package com.github.sfpprxy.tacointellijplugin.improvement

import com.github.sfpprxy.tacointellijplugin.action.ActionUtil
import com.github.sfpprxy.tacointellijplugin.exception.TacoException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PopupAction
import com.intellij.openapi.command.WriteCommandAction

@Suppress("MissingActionUpdateThread")
class ImproveSnippetAction : AnAction(), PopupAction, ImproveAction {

    override fun update(e: AnActionEvent) {
        e.presentation.setEnabledAndVisible(ActionUtil.hasSelection(e))
    }

    override fun actionPerformed(e: AnActionEvent) {
        asyncImprove(e)
    }

    override fun buildImprovementContext(e: AnActionEvent): ImprovementContext {
        return ImprovementContext(
            ImprovementScope.SNIPPET,
            listOf(
                ProjectObject(
                    ActionUtil.getFilePath(e),
                    false,
                    ActionUtil.getFileSize(e),
                    getSnippet(e)
                )
            ),
            Rule.defaultSet()
        )
    }

    private fun getSnippet(e: AnActionEvent): Code {
        val text = ActionUtil.getEditor(e).selectionModel.getSelectedText(true)
            ?: throw TacoException("Snippet not found")
        return Code(text, Language.from(ActionUtil.getPsiFile(e).language.displayName))
    }

    // https://github.com/JetBrains/intellij-sdk-code-samples/tree/main/editor_basics/
    override fun useImprovement(e: AnActionEvent, improvement: Improvement) {
        val editor = ActionUtil.getEditor(e)
        WriteCommandAction.runWriteCommandAction(e.project) {
            editor.document.replaceString(
                editor.selectionModel.selectionStart,
                editor.selectionModel.selectionEnd,
                improvement.improvedProjectObjects.first().code!!.content,
            )
        }
    }
}
