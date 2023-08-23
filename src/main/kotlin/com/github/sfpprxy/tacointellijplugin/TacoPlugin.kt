package com.github.sfpprxy.tacointellijplugin

import com.github.sfpprxy.tacointellijplugin.settings.SettingsState
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.Disposable
import com.intellij.openapi.extensions.PluginId

class TacoPlugin internal constructor() : Disposable {

    override fun dispose() {
    }

    companion object {
        private const val TACO_PLUGIN_ID = "Taco"

        fun getVersion(): String {
            val plugin = PluginManagerCore.getPlugin(getPluginId())
            return plugin!!.version
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun getPluginId(): PluginId {
            return PluginId.getId(TACO_PLUGIN_ID)
        }
    }
}

inline fun <reified T : Any> T.peek(message: Any?) {
    if (!SettingsState.getInstance().debugMode) {
        return
    }

    val stackTrace = Thread.currentThread().stackTrace
    if (stackTrace.size > 2) {
        val callerFunction = stackTrace[1]
        val fileName = callerFunction.fileName
        val functionName = callerFunction.methodName
        val lineNumber = callerFunction.lineNumber
        println("Taco peek:$fileName#$functionName:$lineNumber#$message:$this")
    }
}
