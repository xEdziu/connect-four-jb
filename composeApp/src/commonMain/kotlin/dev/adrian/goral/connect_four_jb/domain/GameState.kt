package dev.adrian.goral.connect_four_jb.domain

enum class GameStatus {
    IN_PROGRESS, WON, DRAW
}

data class GameState(
    val config: GameConfig = GameConfig(),
    val board: Board = Board(config),
    val currentPlayer: Player = Player.RED,
    val status: GameStatus = GameStatus.IN_PROGRESS,
    val winner: Player = Player.NONE
)