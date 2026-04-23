package dev.adrian.goral.connect_four_jb.domain

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WinValidatorTest {

    @Test
    fun `should detect vertical win`() {
        val config = GameConfig(rows = 6, columns = 7, winCondition = 4)
        var board = Board(config)

        // simulate dropping 3 red tokens to 0 column
        board = board.dropToken(column = 0, player = Player.RED)
        board = board.dropToken(column = 0, player = Player.RED)
        board = board.dropToken(column = 0, player = Player.RED)

        assertFalse(
            WinValidator.hasWon(board, Player.RED),
            "Player should not win with only 3 vertical tokens (4 to win)"
        )

        board = board.dropToken(column = 0, player = Player.RED)

        assertTrue(
            WinValidator.hasWon(board, Player.RED),
            "Player should win with 4 tokens in a column"
        )
    }

    @Test
    fun `should detect horizontal win`() {
        val config = GameConfig(rows = 6, columns = 7, winCondition = 4)
        var board = Board(config)

        board = board.dropToken(column = 0, player = Player.RED)
        board = board.dropToken(column = 1, player = Player.RED)
        board = board.dropToken(column = 2, player = Player.RED)

        assertFalse(
            WinValidator.hasWon(board, Player.RED),
            "Player should not win with only 3 horizontal tokens (4 to win)"
        )

        board = board.dropToken(column = 3, player = Player.RED)

        assertTrue(
            WinValidator.hasWon(board, Player.RED),
            "Player should win with 4 tokens in a row"
        )
    }

    @Test
    fun `should detect diagonal win from left to right`() {
        val config = GameConfig(rows = 6, columns = 7, winCondition = 4)
        var board = Board(config)

        // Build diagonal: (5,0), (4,1), (3,2), (2,3)
        board = board.dropToken(column = 0, player = Player.RED)

        board = board.dropToken(column = 1, player = Player.BLUE)
        board = board.dropToken(column = 1, player = Player.RED)

        board = board.dropToken(column = 2, player = Player.BLUE)
        board = board.dropToken(column = 2, player = Player.BLUE)
        board = board.dropToken(column = 2, player = Player.RED)

        assertFalse(
            WinValidator.hasWon(board, Player.RED),
            "Player should not win with only 3 diagonal tokens (4 to win)"
        )

        board = board.dropToken(column = 3, player = Player.BLUE)
        board = board.dropToken(column = 3, player = Player.BLUE)
        board = board.dropToken(column = 3, player = Player.BLUE)
        board = board.dropToken(column = 3, player = Player.RED)

        assertTrue(
            WinValidator.hasWon(board, Player.RED),
            "Player should win with 4 tokens on a left-to-right diagonal"
        )
    }

    @Test
    fun `should detect diagonal win from right to left`() {
        val config = GameConfig(rows = 6, columns = 7, winCondition = 4)
        var board = Board(config)

        // Build diagonal: (5,3), (4,2), (3,1), (2,0)
        board = board.dropToken(column = 3, player = Player.RED)

        board = board.dropToken(column = 2, player = Player.BLUE)
        board = board.dropToken(column = 2, player = Player.RED)

        board = board.dropToken(column = 1, player = Player.BLUE)
        board = board.dropToken(column = 1, player = Player.BLUE)
        board = board.dropToken(column = 1, player = Player.RED)

        assertFalse(
            WinValidator.hasWon(board, Player.RED),
            "Player should not win with only 3 diagonal tokens (4 to win)"
        )

        board = board.dropToken(column = 0, player = Player.BLUE)
        board = board.dropToken(column = 0, player = Player.BLUE)
        board = board.dropToken(column = 0, player = Player.BLUE)
        board = board.dropToken(column = 0, player = Player.RED)

        assertTrue(
            WinValidator.hasWon(board, Player.RED),
            "Player should win with 4 tokens on a right-to-left diagonal"
        )
    }
}