package com.github.sfpprxy.tacointellijplugin.settings

import com.intellij.openapi.options.Configurable
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

/**
 * Provides controller functionality for application settings.
 */
class SettingsConfigurable : Configurable {
    private var mySettingsComponent: SettingsComponent? = null

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String {
        return "Taco Settings"
    }

    override fun createComponent(): JComponent {
        mySettingsComponent = SettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = SettingsState.getInstance()
        return mySettingsComponent!!.endpointUrl != settings.endpointUrl
    }

    override fun apply() {
        val settings = SettingsState.getInstance()
        settings.endpointUrl = mySettingsComponent!!.endpointUrl
    }

    override fun reset() {
        val settings = SettingsState.getInstance()
        mySettingsComponent!!.endpointUrl = settings.endpointUrl
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}
