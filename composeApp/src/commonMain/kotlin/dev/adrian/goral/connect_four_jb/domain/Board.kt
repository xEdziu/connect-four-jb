package dev.adrian.goral.connect_four_jb.domain

class Board private constructor(
    val grid: List<List<Player>>
) {
    constructor(config: GameConfig = GameConfig()) : this(
        List(config.rows) { List(config.columns) { Player.NONE } }
    )

    fun isEmpty(): Boolean = grid.all { row ->
        row.all { cell -> cell == Player.NONE }
    }

    fun dropToken(column: Int, player: Player): Board {
        if (player == Player.NONE) {
            throw IllegalArgumentException("Cannot drop NONE token")
        }

        val columnCount = grid.firstOrNull()?.size ?: 0
        if (column !in 0 until columnCount) {
            throw IllegalArgumentException("Column out of bounds: $column")
        }

        val targetRow = (grid.lastIndex downTo 0).firstOrNull { row ->
            grid[row][column] == Player.NONE
        } ?: throw IllegalArgumentException("Column is full: $column")

        val newGrid = grid.mapIndexed { rowIndex, row ->
            if (rowIndex != targetRow) row
            else row.mapIndexed { colIndex, cell ->
                if (colIndex == column) player else cell
            }
        }

        return Board(newGrid)
    }
}