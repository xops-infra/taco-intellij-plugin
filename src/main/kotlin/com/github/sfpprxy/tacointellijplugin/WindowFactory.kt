package com.github.sfpprxy.tacointellijplugin

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class WindowFactory : ToolWindowFactory {

    init {
        thisLogger().info(
            "Init TacoWindowFactory, Happy developing!"
        )
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {

    }

    override fun shouldBeAvailable(project: Project) = false
}
