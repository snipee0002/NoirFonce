package de.yuuto.monitor.util

import kotlin.io.path.Path
import kotlin.io.path.readLines

object ProxyManager {
    private val proxyFile = Path("./proxies.txt")
    private val proxies: List<String> = proxyFile.readLines()

    fun get(): List<String> = proxies.random().split(":")
}
