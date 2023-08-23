package com.github.sfpprxy.tacointellijplugin.settings

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Supports storing the application settings in a persistent way.
 * The [State] and [Storage] annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
    name = "com.github.sfpprxy.tacointellijplugin.settings.AppSettingsState",
    storages = [Storage("TacoSettings.xml")]
)
@Service
class SettingsState : PersistentStateComponent<SettingsState> {
    var endpointUrl = "http://replace-this-example-url"

    override fun getState(): SettingsState {
        return this
    }

    override fun loadState(state: SettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): SettingsState = service()
    }
}
