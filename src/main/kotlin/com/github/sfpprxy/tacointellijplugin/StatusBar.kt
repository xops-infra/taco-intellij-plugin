package com.github.sfpprxy.tacointellijplugin

import com.github.sfpprxy.tacointellijplugin.TacoBundle.message
import com.github.sfpprxy.tacointellijplugin.settings.SettingsConfigurable
import com.intellij.ide.BrowserUtil
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.util.NlsActions
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.ui.awt.RelativePoint
import com.intellij.util.Consumer
import icons.TacoIcons
import org.jetbrains.annotations.NonNls
import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.Icon
import javax.swing.SwingConstants

@NonNls
internal const val STATUS_BAR_ICON_ID = "Taco-Icon"
internal const val STATUS_BAR_DISPLAY_NAME = "Taco"

internal class StatusBarIconFactory : StatusBarWidgetFactory/*, LightEditCompatible*/ {

    override fun getId(): String = STATUS_BAR_ICON_ID

    override fun getDisplayName(): String = STATUS_BAR_DISPLAY_NAME

    override fun disposeWidget(widget: StatusBarWidget) {
    }

    override fun isAvailable(project: Project): Boolean {
        return true
    }

    override fun createWidget(project: Project): StatusBarWidget {
        return TacoStatusBar()
    }

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean = true

    override fun isConfigurable(): Boolean = true
}

internal class TacoStatusBar : StatusBarWidget, StatusBarWidget.IconPresentation {

    override fun ID(): String = STATUS_BAR_ICON_ID

    override fun install(statusBar: StatusBar) {
    }

    override fun dispose() {
    }

    override fun getTooltipText() = STATUS_BAR_DISPLAY_NAME

    override fun getIcon(): Icon {
        return TacoIcons.Sdk_default_icon
    }

    override fun getClickConsumer() = Consumer<MouseEvent> { event ->
        val popup =
            TacoActionsPopup.getPopup(DataManager.getInstance().getDataContext(event.component))

        val at = Point(0, -popup.content.preferredSize.height)
        popup.show(RelativePoint(event.component, at))
    }

    override fun getPresentation(): StatusBarWidget.WidgetPresentation = this
}

class TacoActions : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        TacoActionsPopup.getPopup(e.dataContext).showCenteredInCurrentWindow(project)
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null && !project.isDisposed
    }

    override fun getActionUpdateThread() = ActionUpdateThread.BGT
}

private object TacoActionsPopup {
    fun getPopup(dataContext: DataContext): ListPopup {
        val actions = getActions()
        val popup = JBPopupFactory.getInstance()
            .createActionGroupPopup(
                STATUS_BAR_DISPLAY_NAME,
                actions,
                dataContext,
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                false,
                ActionPlaces.POPUP,
            )
        popup.setAdText(message("popup.version", TacoPlugin.getVersion()), SwingConstants.CENTER)

        return popup
    }

    private fun getActions(): DefaultActionGroup {
        val actionGroup = DefaultActionGroup()
        actionGroup.isPopup = true

        actionGroup.add(ShortcutConflictsSettings)
        actionGroup.addSeparator(message("action.contacts.help.text"))
        actionGroup.add(
            HelpLink(
                message("action.contacts.issue.create"),
                message("action.contacts.issue.link"),
                TacoIcons.Sdk_default_icon,
            ),
        )
        return actionGroup
    }
}

private class HelpLink(
    @NlsActions.ActionText name: String,
    val link: String,
    icon: Icon?,
) : DumbAwareAction(name, null, icon)/*, LightEditCompatible*/ {
    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.browse(link)
    }
}

private object ShortcutConflictsSettings :
    DumbAwareAction(message("action.settings.text"))/*, LightEditCompatible*/ {
    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance()
            .showSettingsDialog(e.project, SettingsConfigurable::class.java)
    }
}
