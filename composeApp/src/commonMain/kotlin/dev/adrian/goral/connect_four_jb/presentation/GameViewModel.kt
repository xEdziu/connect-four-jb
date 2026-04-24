package dev.adrian.goral.connect_four_jb.presentation

import dev.adrian.goral.connect_four_jb.domain.GameConfig
import dev.adrian.goral.connect_four_jb.domain.GameEngine
import dev.adrian.goral.connect_four_jb.domain.GameStatus
import dev.adrian.goral.connect_four_jb.domain.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    initialSnapshot: PersistedGameSnapshot? = null,
    private val onPersistenceChanged: (PersistedGameSnapshot?) -> Unit = {}
) {
    private var engine = GameEngine()
    private val moveHistory = mutableListOf<Int>()
    //private, mutable state that only viewmodel can modify
    private val _state = MutableStateFlow(engine.gameState)
    //external immutable state for UI read
    val state: StateFlow<GameState> = _state.asStateFlow()

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage.asStateFlow()

    init {
        val restored = initialSnapshot?.let { restoreFromSnapshot(it) } ?: false
        if (!restored) {
            notifyPersistence()
        }
    }

    fun onColumnClicked(column: Int) {
        val previousState = engine.gameState
        try {
            engine.play(column)
            val nextState = engine.gameState
            if (previousState.board != nextState.board) {
                moveHistory.add(column)
            }
            _state.value = nextState
            _uiMessage.value = null
            notifyPersistence()
        } catch (_: IllegalArgumentException) {
            _uiMessage.value = "Column is full"
        }
    }

    fun startNewGame(config: GameConfig) {
        engine = GameEngine(GameState(config = config))
        moveHistory.clear()
        _state.value = engine.gameState
        _uiMessage.value = null
        notifyPersistence()
    }

    fun resetGame() {
        val config = _state.value.config
        engine = GameEngine(GameState(config = config))
        moveHistory.clear()
        _state.value = engine.gameState
        _uiMessage.value = null
        notifyPersistence()
    }

    fun clearUiMessage() {
        _uiMessage.value = null
    }

    private fun restoreFromSnapshot(snapshot: PersistedGameSnapshot): Boolean {
        if (!snapshot.config.isValidForPersistence()) return false

        val restoredEngine = GameEngine(GameState(config = snapshot.config))
        val restoredMoves = mutableListOf<Int>()

        for (column in snapshot.moves) {
            val before = restoredEngine.gameState
            if (column !in 0 until before.config.columns || before.status != GameStatus.IN_PROGRESS) {
                return false
            }

            try {
                restoredEngine.play(column)
            } catch (_: IllegalArgumentException) {
                return false
            }

            val after = restoredEngine.gameState
            if (before.board == after.board) {
                return false
            }
            restoredMoves.add(column)
        }

        engine = restoredEngine
        moveHistory.clear()
        moveHistory.addAll(restoredMoves)
        _state.value = restoredEngine.gameState
        _uiMessage.value = null
        notifyPersistence()
        return true
    }

    private fun notifyPersistence() {
        val currentState = _state.value
        if (currentState.status == GameStatus.IN_PROGRESS) {
            onPersistenceChanged(
                PersistedGameSnapshot(
                    schemaVersion = PersistedGameSnapshot.SCHEMA_VERSION,
                    config = currentState.config,
                    moves = moveHistory.toList()
                )
            )
            return
        }

        onPersistenceChanged(null)
    }

    private fun GameConfig.isValidForPersistence(): Boolean {
        if (rows !in 4..20 || columns !in 4..20) return false
        val maxWin = minOf(10, rows, columns)
        return winCondition in 3..maxWin
    }
}