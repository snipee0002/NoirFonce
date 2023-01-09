package de.yuuto.monitor.api

import de.yuuto.monitor.dataclass.Webhook
import de.yuuto.monitor.util.Logger
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object WebhookManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val WebhookSender = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }


    suspend fun send(data: Webhook) {
        data.webhooks.forEach {
            scope.launch {
                val send = WebhookSender.post(it) {
                    contentType(ContentType.Application.Json)
                    setBody(data.embeds)
                }
                if (!send.status.isSuccess()) {
                    Logger.error("Couldn't send webhook ${send.status.hashCode()}")
                }
            }
        }
        return
    }


}