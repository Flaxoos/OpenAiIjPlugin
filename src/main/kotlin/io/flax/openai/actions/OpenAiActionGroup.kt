package io.flax.openai.actions

import com.intellij.openapi.actionSystem.*

object OpenAiActionGroup : ActionGroup() {
    init {
        ActionManager.getInstance()
            .createActionToolbar(
                ActionPlaces.TOOLBAR,
                OpenAiActionGroup,
                true,
            ).apply { }
    }

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        return arrayOf(ExplainCodeAction())
    }
}
