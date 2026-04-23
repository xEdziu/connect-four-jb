package dev.adrian.goral.connect_four_jb.domain

import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BoardTest {

    @Test
    fun `new board should be empty`() {
        val board = Board()
        assertTrue(board.isEmpty())
    }

    @Test
    fun `new board should have correct dimensions`() {
        val config = GameConfig(rows = 6, columns = 7)
        val board = Board(config)

        assertEquals(6, board.grid.size, "Board should have 6 rows")
        assertEquals(7, board.grid[0].size, "Board should have 7 columns")
    }

    @Test
    fun `all board cells should be initialized to Player NONE on start`() {
        val config = GameConfig(rows = 6, columns = 7)
        val board = Board(config)

        val isBoardEmpty = board.grid.all {row ->
            row.all { cell -> cell == Player.NONE }
        }
        assertTrue(isBoardEmpty, "All cells should be initialized to Player.NONE on start")
    }

    @Test
    fun `dropping token in empty column should place it at the bottom`() {
        val config = GameConfig(rows = 6, columns = 7)
        val board = Board(config)

        val newBoard = board.dropToken(column = 3, player = Player.RED)
        assertEquals(Player.RED, newBoard.grid[5][3], "Token should be placed at the bottom of the column")
        assertEquals(Player.NONE, newBoard.grid[4][3], "Cell above the token should be empty")
        assertEquals(Player.NONE, board.grid[5][3], "Original board should remain unchanged")
    }
}