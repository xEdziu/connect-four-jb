package dev.adrian.goral.connect_four_jb.presentation

import dev.adrian.goral.connect_four_jb.domain.GameConfig
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

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage.asStateFlow()

    fun onColumnClicked(column: Int) {
        try {
            engine.play(column)
            _state.value = engine.gameState
            _uiMessage.value = null
        } catch (_: IllegalArgumentException) {
            _uiMessage.value = "Column is full"
        }
    }

    fun startNewGame(config: GameConfig) {
        engine = GameEngine(GameState(config = config))
        _state.value = engine.gameState
        _uiMessage.value = null
    }

    fun resetGame() {
        val config = _state.value.config
        engine = GameEngine(GameState(config = config))
        _state.value = engine.gameState
        _uiMessage.value = null
    }

    fun clearUiMessage() {
        _uiMessage.value = null
    }
}