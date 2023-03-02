package io.flax.openai.ui.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class OpenAiToolWindowFactory : ToolWindowFactory {

    companion object {
        private var openAiToolWindow: OpenAiToolWindow? = null
        fun getInitialized() = openAiToolWindow ?: error("Open AI tool window not initialized")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val openAiToolWindow = OpenAiToolWindow(toolWindow).also { openAiToolWindow = it }
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(openAiToolWindow.content, "Open AI", false)
        toolWindow.contentManager.addContent(content)
    }
}
