package dev.adrian.goral.connect_four_jb.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class GameEngineTest {
    @Test
    fun `playing a move should alternate turns`() {
        val engine = GameEngine()
        val initialState = engine.gameState

        assertEquals(Player.RED, initialState.currentPlayer)

        engine.play(column = 0)
        assertEquals(Player.BLUE, engine.gameState.currentPlayer)

        engine.play(column = 1)
        assertEquals(Player.RED, engine.gameState.currentPlayer)

        engine.play(column = 2)
        val nextState = engine.gameState
        assertEquals(Player.BLUE, nextState.currentPlayer)
        assertEquals(GameStatus.IN_PROGRESS, nextState.status)
    }

    @Test
    fun `playing a winning move should update status and not change turn`() {
        // Arrange
        val config = GameConfig(rows = 6, columns = 7, winCondition = 3)
        val engine = GameEngine(GameState(config = config, board = Board(config)))

        engine.play(column = 0) // RED
        engine.play(column = 1) // BLUE
        engine.play(column = 0) // RED
        engine.play(column = 1) // BLUE

        assertEquals(GameStatus.IN_PROGRESS, engine.gameState.status)
        assertEquals(Player.RED, engine.gameState.currentPlayer)

        engine.play(column = 0)

        val finalState = engine.gameState
        assertEquals(GameStatus.WON, finalState.status, "Game should be WON")
        assertEquals(Player.RED, finalState.winner, "RED should be the winner")
        assertEquals(Player.RED, finalState.currentPlayer)
    }

    @Test
    fun `playing on full board without possible win should end in draw`() {
        val config = GameConfig(rows = 1, columns = 2, winCondition = 3)
        val engine = GameEngine(GameState(config = config, board = Board(config)))

        engine.play(column = 0)
        engine.play(column = 1)

        val finalState = engine.gameState
        assertEquals(GameStatus.DRAW, finalState.status, "Game should be DRAW")
        assertEquals(Player.NONE, finalState.winner, "There should be no winner")
    }
}