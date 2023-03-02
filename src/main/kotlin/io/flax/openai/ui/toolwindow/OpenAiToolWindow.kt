package io.flax.openai.ui.toolwindow

import com.detectlanguage.DetectLanguage
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.util.ui.AsyncProcessIcon
import io.flax.openai.client.OpenAiClient
import io.flax.openai.ui.icons.OpenAiIcons
import java.util.function.Supplier
import javax.swing.JPanel
import javax.swing.JTextArea

class OpenAiToolWindow(private val toolWindow: ToolWindow) {
    companion object {
        const val ID = "Open AI"
        const val codeName = "Code"
        const val explanationName = "Explanation"
        private var theInstance: OpenAiToolWindow? = null
        fun getInstance(project: Project) = theInstance ?: ToolWindowManager.getInstance(project).let {
            it.registerToolWindow("Open Ai") {
                anchor = ToolWindowAnchor.RIGHT
                stripeTitle = Supplier { "Open Ai" }
                icon = OpenAiIcons.Logo
                shouldBeAvailable = true
                hideOnEmptyContent = false
            }.let { toolWindow -> OpenAiToolWindow(toolWindow).also { theInstance = it } }
        }
    }

    private lateinit var inputTextArea: JTextArea
    private lateinit var outputTextArea: JTextArea
    private lateinit var explainingProcess: AsyncProcessIcon
    internal var content: JPanel = buildUi().also { this.toolWindow.component.add(it) }

    fun explainCode(code: String) {
        toolWindow.show()
        explainingProcess.isVisible = true
        val results = DetectLanguage.detect(code)
        val explanation = if (results.isNotEmpty()) {
            OpenAiClient.explainCode(code, results.maxBy { it.confidence }.language)
        } else {
            emptyList()
        }
        this.inputTextArea.text = code
        this.outputTextArea.text = explanation.joinToString("\n")
        explainingProcess.isVisible = false
    }

    fun generateCode(description: String) {
        toolWindow.show()
//        explainingProcess.isVisible = true
        val code = OpenAiClient.generateCode(description)
        this.inputTextArea.text = description
        this.outputTextArea.text = code
//        explainingProcess.isVisible = false
    }

    private fun buildUi() = panel {
        indent {
            row {
                label(codeName)
            }
            row {
                textArea().also {
                    it.component.name = codeName
                    it.component.isEditable = true
                    inputTextArea = it.component
                }.horizontalAlign(HorizontalAlign.FILL)
            }
            row {
                button("Explain") {
                    explainCode(inputTextArea.text)
                }
                cell(AsyncProcessIcon("Explaining").also { explainingProcess = it })
            }
            separator()
            row { label(explanationName) }
            row {
                textArea().also {
                    it.component.name = explanationName
                    it.component.isEditable = false
                    outputTextArea = it.component
                }.horizontalAlign(HorizontalAlign.FILL)
            }
        }
    }
}
