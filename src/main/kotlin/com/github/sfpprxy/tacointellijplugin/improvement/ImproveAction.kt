package com.github.sfpprxy.tacointellijplugin.improvement

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator
import java.io.IOException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

interface ImproveAction {
    fun asyncImprove(e: AnActionEvent) {
        val indicator = BackgroundableProcessIndicator(e.project, ImproveTask())
        indicator.start()

        val improvementContext = buildImprovementContext(e)

        // https://flylib.com/books/en/2.558.1/why_are_guis_single_threaded_.html

        val blockingQueue: BlockingQueue<Improvement> = LinkedBlockingQueue(1)

        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val improvement = ImproveService.getInstance().improve(improvementContext)
                blockingQueue.put(improvement)
            } catch (e: IOException) {
                thisLogger().error(e)
                indicator.processFinish()
            }
        }

        ApplicationManager.getApplication().executeOnPooledThread {
            val improvement = blockingQueue.take()
            useImprovement(e, improvement)
            indicator.processFinish()
        }
    }

    fun buildImprovementContext(e: AnActionEvent): ImprovementContext

    fun useImprovement(e: AnActionEvent, improvement: Improvement)
}