package com.github.sfpprxy.tacointellijplugin

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.extensions.PluginId
import org.jdom.Element

/**
 * This plugin attempts to emulate the key binding and general functionality of Vim and gVim. See the supplied
 * documentation for a complete list of supported and unsupported Vim emulation. The code base contains some debugging
 * output that can be enabled in necessary.
 *
 *
 * This is an application level plugin meaning that all open projects will share a common instance of the plugin.
 * Registers and marks are shared across open projects so you can copy and paste between files of different projects.
 */
class TacoPlugin internal constructor() : PersistentStateComponent<Element>, Disposable {


    override fun dispose() {
        thisLogger().debug("disposeComponent")
        thisLogger().debug("disposeComponent done")
    }

    override fun getState(): Element? {
        TODO("Not yet implemented")
    }

    override fun loadState(state: Element) {
        TODO("Not yet implemented")
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
