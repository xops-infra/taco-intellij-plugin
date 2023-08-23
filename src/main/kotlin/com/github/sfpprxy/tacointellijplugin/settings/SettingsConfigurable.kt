package com.github.sfpprxy.tacointellijplugin.settings

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class SettingsConfigurable : Configurable {
    private var settingsComponent: SettingsComponent? = null

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String {
        return "Taco Settings"
    }

    override fun createComponent(): JComponent {
        settingsComponent = SettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = SettingsState.getInstance()
        return settingsComponent!!.endpointUrl != settings.endpointUrl
                || settingsComponent!!.debugMode != settings.debugMode
    }

    override fun apply() {
        val settings = SettingsState.getInstance()
        settings.endpointUrl = settingsComponent!!.endpointUrl
        settings.debugMode = settingsComponent!!.debugMode
        if (settings.debugMode) {
            thisLogger().info("taco debug mode enabled")
        }
    }

    override fun reset() {
        val settings = SettingsState.getInstance()
        settingsComponent!!.endpointUrl = settings.endpointUrl
        settingsComponent!!.debugMode = settings.debugMode
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
