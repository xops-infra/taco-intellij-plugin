package com.github.sfpprxy.tacointellijplugin.improvement

import com.github.sfpprxy.tacointellijplugin.action.ActionUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PopupAction
import com.intellij.openapi.command.WriteCommandAction

@Suppress("MissingActionUpdateThread")
class ImproveFileAction : AnAction(), PopupAction, ImproveAction {

    override fun update(e: AnActionEvent) {
        e.presentation.setEnabledAndVisible(!ActionUtil.hasSelection(e))
    }

    override fun actionPerformed(e: AnActionEvent) {
        asyncImprove(e)
    }

    override fun buildImprovementContext(e: AnActionEvent): ImprovementContext {
        return ImprovementContext(
            ImprovementScope.FILE,
            listOf(
                ProjectObject(
                    ActionUtil.getFilePath(e),
                    false,
                    ActionUtil.getFileSize(e),
                    getFileContent(e)
                )
            ),
            Rule.defaultSet()
        )
    }

    private fun getFileContent(e: AnActionEvent): Code {
        val psiFile = ActionUtil.getPsiFile(e)
        return Code(psiFile.text, Language.from(psiFile.language.displayName))
    }

    override fun useImprovement(e: AnActionEvent, improvement: Improvement) {
        val psiFile = ActionUtil.getPsiFile(e)
        WriteCommandAction.runWriteCommandAction(psiFile.project) {
            val content = improvement.improvedProjectObjects.first().code!!.content
            ActionUtil.getEditor(e).document.setText(content)
        }
    }
}
