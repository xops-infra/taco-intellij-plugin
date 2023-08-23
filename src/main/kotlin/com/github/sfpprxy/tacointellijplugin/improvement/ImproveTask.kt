package com.github.sfpprxy.tacointellijplugin.improvement

import com.github.sfpprxy.tacointellijplugin.TacoBundle.message
import com.intellij.openapi.progress.TaskInfo

class ImproveTask : TaskInfo {
    override fun getTitle(): String {
        return message("action.improve.process.tittle")
    }

    override fun getCancelText(): String {
        return message("action.improve.process.cancel.text")
    }

    override fun getCancelTooltipText(): String {
        return message("action.improve.process.cancel.tooltip.text")
    }

    override fun isCancellable(): Boolean {
        return true
    }
}