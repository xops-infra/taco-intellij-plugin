package com.github.sfpprxy.tacointellijplugin.settings

import com.github.sfpprxy.tacointellijplugin.TacoBundle.message
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class SettingsComponent {
    val panel: JPanel
    private val endpointTextField = JBTextField()

    init {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(
                JBLabel(message("action.settings.endpoint.label")),
                endpointTextField,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    var endpointUrl: String
        get() {
            return endpointTextField.getText()
        }
        set(newText) {
            endpointTextField.setText(newText)
        }
}
