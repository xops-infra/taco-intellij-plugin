package com.github.sfpprxy.tacointellijplugin.action

import com.github.sfpprxy.tacointellijplugin.exception.TacoException
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiFile

class ActionUtil {
    companion object {
        fun getEditor(e: AnActionEvent) = (CommonDataKeys.EDITOR.getData(e.dataContext)
            ?: throw TacoException("Editor not found"))

        fun getFilePath(e: AnActionEvent): String {
            val project = e.project ?: throw TacoException("Project not found")
            val projectRoot = project.basePath ?: throw TacoException("Project root not found")
            val filePath = getPsiFile(e).virtualFile.path
            return filePath.substring(projectRoot.length + 1)
        }

        fun getFileSize(e: AnActionEvent): Int {
            return getPsiFile(e).virtualFile.length.toInt()
        }

        fun getPsiFile(e: AnActionEvent): PsiFile {
            return e.getData(CommonDataKeys.PSI_FILE) ?: throw TacoException("PsiFile not found")
        }

        fun hasSelection(e: AnActionEvent): Boolean {
            return e.getRequiredData(CommonDataKeys.EDITOR).caretModel.currentCaret.hasSelection()
        }
    }
}