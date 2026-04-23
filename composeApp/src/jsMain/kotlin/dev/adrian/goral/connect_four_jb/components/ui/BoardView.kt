package dev.adrian.goral.connect_four_jb.components.ui

import androidx.compose.runtime.Composable
import dev.adrian.goral.connect_four_jb.domain.GameState
import dev.adrian.goral.connect_four_jb.domain.GameStatus
import dev.adrian.goral.connect_four_jb.domain.Player
import org.jetbrains.compose.web.dom.Div

@Composable
fun BoardView(state: GameState, onColumnClick: (Int) -> Unit) {
    Div(attrs = {
        classes("board")
        attr("style", "grid-template-columns: repeat(${state.config.columns}, minmax(0, 1fr));")
    }) {
        for (rowIndex in 0 until state.config.rows) {
            for (colIndex in 0 until state.config.columns) {
                val player = state.board.grid[rowIndex][colIndex]

                val tokenClass = when (player) {
                    Player.RED -> "token-red"
                    Player.BLUE -> "token-blue"
                    Player.NONE -> "token-empty"
                }

                val isColumnFull = state.board.grid.first()[colIndex] != Player.NONE
                val canPlay = state.status == GameStatus.IN_PROGRESS && !isColumnFull

                Div(attrs = {
                    classes("slot")
                    if (!canPlay) classes("slot-disabled")
                    if (canPlay) {
                        onClick { onColumnClick(colIndex) }
                    }
                }) {
                    Div(attrs = {
                        classes("token", tokenClass)
                    })
                }
            }
        }
    }
}