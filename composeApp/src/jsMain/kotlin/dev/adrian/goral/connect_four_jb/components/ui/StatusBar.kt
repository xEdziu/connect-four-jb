package dev.adrian.goral.connect_four_jb.components.ui

import androidx.compose.runtime.Composable
import dev.adrian.goral.connect_four_jb.domain.GameState
import dev.adrian.goral.connect_four_jb.domain.GameStatus
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun StatusBar(state: GameState, message: String? = null) {
    P(attrs = {
        classes("status")
        if (message != null) classes("status-error")
    }) {
        Text(
            message ?: when (state.status) {
                GameStatus.IN_PROGRESS -> "Current player: ${state.currentPlayer}"
                GameStatus.WON -> "Winner: ${state.winner}"
                GameStatus.DRAW -> "Draw"
            }
        )
    }
}
