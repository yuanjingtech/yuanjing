package com.yuanjingtech

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform