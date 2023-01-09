package de.yuuto.monitor.util

import mu.KotlinLogging

object Logger {
    private val logger = KotlinLogging.logger {}

    fun info(text: String) {
        logger.info { text }
    }
    fun error(text: String) {
        logger.error { text }
    }
}