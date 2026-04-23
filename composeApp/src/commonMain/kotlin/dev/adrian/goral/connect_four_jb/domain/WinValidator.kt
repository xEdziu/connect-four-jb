package dev.adrian.goral.connect_four_jb.domain

object WinValidator {

    fun hasWon(board: Board, player: Player, config: GameConfig): Boolean {
        val winCondition = config.winCondition

        // Check horizontal
        for (row in board.grid) {
            if (row.windowed(winCondition).any { window -> window.all { it == player } }) {
                return true
            }
        }

        // Check vertical
        for (col in 0 until config.columns) {
            val columnTokens = board.grid.map { it[col] }
            if (columnTokens.windowed(winCondition).any { window -> window.all { it == player } }) {
                return true
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (row in 0..(config.rows - winCondition)) {
            for (col in 0..(config.columns - winCondition)) {
                if ((0 until winCondition).all { offset ->
                        board.grid[row + offset][col + offset] == player
                    }) {
                    return true
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (row in 0..(config.rows - winCondition)) {
            for (col in (winCondition - 1) until config.columns) {
                if ((0 until winCondition).all { offset ->
                        board.grid[row + offset][col - offset] == player
                    }) {
                    return true
                }
            }
        }

        return false
    }
}