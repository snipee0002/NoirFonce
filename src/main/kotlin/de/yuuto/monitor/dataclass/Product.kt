package de.yuuto.monitor.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long,
    val title: String,
    val handle: String,
    val vendor: String,
    val variants: List<Variants>,
    val images: List<Images>
)

@Serializable
data class Variants(
    val id: Long,
    val title: String,
    val price: String,
    val available: Boolean,
)


@Serializable
data class Images(
    val id: Long,
    val src: String,
)