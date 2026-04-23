package dev.adrian.goral.connect_four_jb.domain

class GameEngine(initialState: GameState = GameState()) {
    var gameState: GameState = initialState
        private set

    fun play(column: Int) {
        val current = gameState
        if (current.status != GameStatus.IN_PROGRESS) return

        val player = current.currentPlayer
        val updatedBoard = current.board.dropToken(column, player)

        if (WinValidator.hasWon(updatedBoard, player, current.config)) {
            gameState = current.copy(
                board = updatedBoard,
                status = GameStatus.WON,
                winner = player,
                currentPlayer = player
            )
            return
        }

        if (isBoardFull(updatedBoard)) {
            gameState = current.copy(
                board = updatedBoard,
                status = GameStatus.DRAW,
                winner = Player.NONE
            )
            return
        }

        gameState = current.copy(
            board = updatedBoard,
            currentPlayer = player.next()
        )
    }

    private fun isBoardFull(board: Board): Boolean =
        // to consider: checking if only the top row is full - if yes, end the game
        board.grid.all { row -> row.none { cell -> cell == Player.NONE } }

    private fun Player.next(): Player = when (this) {
        Player.RED -> Player.BLUE
        Player.BLUE -> Player.RED
        Player.NONE -> Player.NONE
    }
}