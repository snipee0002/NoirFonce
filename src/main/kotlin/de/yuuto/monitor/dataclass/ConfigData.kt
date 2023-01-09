package de.yuuto.monitor.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class ConfigData(
    val Backend: Backend,
    val delay: Int,
    val webhooks: List<String>,
    val filter: Filter,
    val color: String,
    val footer: String,
    val clients: Int
)


@Serializable
data class Backend(
    val products: String
)

@Serializable
data class Filter(
    val skus: List<Long>,
    val keywords: List<String>
)