package com.github.sfpprxy.tacointellijplugin.settings

import com.github.sfpprxy.tacointellijplugin.TacoBundle.message
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JCheckBox
import javax.swing.JPanel

/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class SettingsComponent {
    val panel: JPanel
    private val debugModeCheckBox = JCheckBox(message("action.settings.debug.label"))
    private val endpointTextField = JBTextField()

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(
                debugModeCheckBox,
                1,
            )
            .addLabeledComponent(
                JBLabel(message("action.settings.endpoint.label")),
                endpointTextField,
                1,
                false
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    var debugMode: Boolean
        get() {
            return debugModeCheckBox.isSelected
        }
        set(newDebugMode) {
            debugModeCheckBox.isSelected = newDebugMode
        }

    var endpointUrl: String
        get() {
            return endpointTextField.getText()
        }
        set(newText) {
            endpointTextField.setText(newText)
        }
}
