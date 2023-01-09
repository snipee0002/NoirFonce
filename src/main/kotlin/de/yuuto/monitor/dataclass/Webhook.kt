package de.yuuto.monitor.dataclass

import dev.kord.rest.json.request.EmbedRequest
import kotlinx.serialization.Serializable

@Serializable
data class EmbedList(
    val embeds: List<EmbedRequest>
)

@Serializable
data class Webhook(
    val webhooks: List<String>,
    val embeds: EmbedList
)