package dev.adrian.goral.connect_four_jb.components.ui

import androidx.compose.runtime.Composable
import dev.adrian.goral.connect_four_jb.domain.GameState
import dev.adrian.goral.connect_four_jb.domain.GameStatus
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun GameResultBanner(state: GameState, onPlayAgain: () -> Unit) {
    if (state.status == GameStatus.IN_PROGRESS) return

    val (title, subtitle, modifierClass) = when (state.status) {
        GameStatus.WON -> Triple(
            "Victory!",
            "${state.winner} wins the game",
            if (state.winner.name.lowercase() == "red") "result-win-red" else "result-win-blue"
        )
        GameStatus.DRAW -> Triple("Draw", "No more valid moves", "result-draw")
        GameStatus.IN_PROGRESS -> Triple("", "", "")
    }

    Div(attrs = { classes("result-banner", modifierClass) }) {
        P(attrs = { classes("result-title") }) { Text(title) }
        P(attrs = { classes("result-subtitle") }) { Text(subtitle) }
        Button(attrs = {
            classes("btn", "btn-primary")
            onClick { onPlayAgain() }
        }) {
            Text("Play again")
        }
    }
}

