package de.yuuto.monitor.util

fun filter(sku: Long, title: String): Boolean {
    Config.getKeywords().forEach {
        if (title.lowercase().contains(it.lowercase())) {
            return true
        }
    }

    Config.getSkus().forEach {
        if (sku == it) {
            return true
        }
    }
    return false
}