package dev.adrian.goral.connect_four_jb.domain

data class GameConfig(
    val rows: Int = 6,
    val columns: Int = 7,
    val winCondition: Int = 4
)
