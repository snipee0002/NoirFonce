package de.yuuto.monitor.api

import de.yuuto.monitor.dataclass.Products
import de.yuuto.monitor.util.Config
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

object Backend {
    suspend fun scrapeProducts(client: HttpClient): Products = client.get(Config.getProducts()).body()

}