package io.flax.openai.ui.icons

import com.intellij.ui.IconManager
import com.intellij.util.IconUtil

object OpenAiIcons {
    @JvmField
    val Logo = IconUtil.resizeSquared(
        IconManager.getInstance().loadRasterizedIcon("open-ai-icon-pink.png", javaClass.classLoader, -1, 1),
        13,
    )
}
