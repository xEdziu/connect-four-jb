package dev.adrian.goral.connect_four_jb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform