package dev.adrian.goral.connect_four_jb.domain

class Board(config: GameConfig = GameConfig()) {
    val grid: List<List<Player>> = List(config.rows) {
        List(config.columns) { Player.NONE }
    }

    fun isEmpty(): Boolean = grid.all { row ->
        row.all { cell -> cell == Player.NONE }
    }
}