package dev.adrian.goral.connect_four_jb

import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        WebApp()
    }
}

