package dev.adrian.goral.connect_four_jb.presentation

import dev.adrian.goral.connect_four_jb.domain.GameEngine
import dev.adrian.goral.connect_four_jb.domain.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel {
    private var engine = GameEngine()
    //private, mutable state that only viewmodel can modify
    private val _state = MutableStateFlow(engine.gameState)
    //external immutable state for UI read
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun onColumnClicked(column: Int) {
        engine.play(column)
        _state.value = engine.gameState
    }

    fun resetGame() {
        engine = GameEngine()
        _state.value = engine.gameState
    }
}