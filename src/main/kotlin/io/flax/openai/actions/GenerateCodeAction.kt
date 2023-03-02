package io.flax.openai.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import io.flax.openai.ui.toolwindow.OpenAiToolWindow

class GenerateCodeAction:AnAction("Generate Code") {
    override fun actionPerformed(e: AnActionEvent) {
        val openAiToolWindow = OpenAiToolWindow.getInstance(
                e.project ?: error { "No project found in action event" },
        )
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val code = editor.selectionModel.selectedText ?: return
        openAiToolWindow.explainCode(code)
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val caretModel = editor.caretModel
        e.presentation.isEnabledAndVisible = caretModel.currentCaret.hasSelection()
    }
}