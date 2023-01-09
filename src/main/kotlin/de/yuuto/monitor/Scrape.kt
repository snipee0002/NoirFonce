import de.yuuto.monitor.api.Backend
import de.yuuto.monitor.api.WebhookManager
import de.yuuto.monitor.dataclass.EmbedList
import de.yuuto.monitor.dataclass.Product
import de.yuuto.monitor.dataclass.Webhook
import de.yuuto.monitor.util.AlreadySent
import de.yuuto.monitor.util.Config
import de.yuuto.monitor.util.Logger
import de.yuuto.monitor.util.filter
import dev.kord.rest.builder.message.EmbedBuilder
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Scraper {
    private val scope = CoroutineScope(Dispatchers.IO)


    private fun check(
        pid: Long,
        sizes: MutableMap<String, String>
    ): Boolean {
        AlreadySent.all().forEach { (sku, size) ->
            if (sku != pid) return@forEach

            return if (sizes == size) {
                false
            } else {
                AlreadySent.replace(pid, sizes)
                true
            }
        }
        AlreadySent.add(pid, sizes)
        return true
    }

    private fun sizes(product: Product): MutableMap<String, String> {
        val size = mutableMapOf<String, String>()
        product.variants.forEach {
            if (it.available) {
                size[it.title] = it.id.toString()
            }
        }
        return size
    }

    private suspend fun sendWebhook(product: Product, start: Boolean) {
        val embed = EmbedBuilder()


        if (start) {
            WebhookManager.send(
                Webhook(
                    webhooks = Config.getWebhooks(),
                    embeds = EmbedList(
                        listOf(
                            embed.toRequest()
                        )
                    )
                )
            )
        }
    }

    private suspend fun manage(product: Product, start: Boolean) {
        if (filter(product.id, product.title)) {
            scope.launch {
                val sizes = sizes(product)
                val check = check(product.id, sizes)
                if (check) {
                    sendWebhook(
                        product = product,
                        start = start
                    )
                }
            }
        }

    }

    suspend fun start(start: Boolean, client: HttpClient) {
        val response = Backend.scrapeProducts(client)

        response.products.forEach {
            manage(it, start)
        }
        Logger.info("Scraped Monitor")
    }
}