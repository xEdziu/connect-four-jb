package dev.adrian.goral.connect_four_jb.presentation

import dev.adrian.goral.connect_four_jb.domain.GameConfig

data class PersistedGameSnapshot(
    val schemaVersion: Int,
    val config: GameConfig,
    val moves: List<Int>
) {
    companion object {
        const val SCHEMA_VERSION: Int = 1
    }
}

