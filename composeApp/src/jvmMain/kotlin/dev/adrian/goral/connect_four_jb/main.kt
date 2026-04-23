package dev.adrian.goral.connect_four_jb

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "connect_four_jb",
    ) {
        App()
    }
}