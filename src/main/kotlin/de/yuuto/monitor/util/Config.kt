package de.yuuto.monitor.util

import de.yuuto.monitor.dataclass.ConfigData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.readText

object Config {
    private lateinit var configFile: Path
    private lateinit var configData: ConfigData
    operator fun invoke() {
        configFile = Path("./config.json")
        configData = Json.decodeFromString(configFile.readText())
    }

    fun getProducts() = configData.Backend.products
    fun getColor() = configData.color
    fun getFooter() = configData.footer
    fun getDelay() = configData.delay
    fun getWebhooks() = configData.webhooks
    fun getSkus() = configData.filter.skus
    fun getKeywords() = configData.filter.keywords

    fun getClients() = configData.clients

}