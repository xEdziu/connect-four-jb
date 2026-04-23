package dev.adrian.goral.connect_four_jb.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class BoardTest {

    @Test
    fun `new board should be empty`() {
        val board = Board()
        assert(board.isEmpty())
    }

    @Test
    fun `new board should have correct dimensions`() {
        val config = GameConfig(rows = 6, columns = 7)
        val board = Board(config)

        assertEquals(6, board.grid.size, "Board should have 6 rows")
        assertEquals(7, board.grid[0].size, "Board should have 7 columns")
    }

    @Test
    fun `all board cells should be initialized to Player.NONE on start`() {
        val config = GameConfig(rows = 6, columns = 7)
        val board = Board(config)

        val isBoardEmpty = board.grid.all {row ->
            row.all { cell -> cell == Player.NONE }
        }
        assertTrue(isBoardEmpty, "All cells should be initialized to Player.NONE on start")
    }
}