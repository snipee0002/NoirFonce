package de.yuuto.monitor.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Products(
    val products: List<Product>
)