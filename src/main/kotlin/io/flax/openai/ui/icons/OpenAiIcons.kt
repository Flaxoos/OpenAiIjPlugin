package io.flax.openai.ui.icons

import com.intellij.openapi.util.IconLoader
import com.intellij.util.IconUtil

object OpenAiIcons {
    @JvmField
    val Logo = IconUtil.resizeSquared(
        IconLoader.getIcon("/openai-icon.svg", javaClass.classLoader),
        13,
    )
}
