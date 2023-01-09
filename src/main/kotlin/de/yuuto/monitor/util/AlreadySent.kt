package de.yuuto.monitor.util

import co.touchlab.stately.isolate.IsolateState

object AlreadySent {
    private val cacheMap = IsolateState { mutableMapOf<Long, MutableMap<String, String>>() }

    fun add(sku: Long, sizes: MutableMap<String, String>) {
        cacheMap.access { map ->
            map.put(sku, sizes)
        }
    }

    fun replace(sku: Long, sizes: MutableMap<String, String>) {
        cacheMap.access {map ->
            map.replace(sku, sizes)
        }
    }

    fun all(): MutableMap<Long, MutableMap<String, String>> {
        return cacheMap.access { it }
    }

}