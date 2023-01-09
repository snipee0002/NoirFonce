
import de.yuuto.monitor.util.Config
import de.yuuto.monitor.util.Logger
import de.yuuto.monitor.util.ProxyManager
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import kotlin.time.Duration.Companion.seconds


suspend fun main() {
    Config()

    Logger.info("Monitor Started")

    var start = false

    val clients = clients()

    try {
        coroutineScope {
            launch {
                while (isActive) {
                    Scraper().start(start, client = clients.random())

                    delay(Config.getDelay().seconds)
                    if (!start) {
                        start = true
                    }
                }
            }
        }
    } catch (e: Exception) {
        Logger.error("Some loop failed ${e.message.toString()}")
    }
}

fun clients(): List<HttpClient> {
    Logger.info("Loading ${Config.getClients()} Clients")
    val client = arrayListOf<HttpClient>()

    for (i in 1..Config.getClients()) {
        val myProxy = ProxyManager.get()
        client.add(
            HttpClient(Apache) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
                engine {
                    followRedirects = false
                    customizeClient {
                        setProxy(HttpHost(myProxy[0], myProxy[1].toInt()))

                        val credentialsProvider = BasicCredentialsProvider()
                        credentialsProvider.setCredentials(
                            AuthScope(myProxy[0], myProxy[1].toInt()),
                            UsernamePasswordCredentials(
                                myProxy[2],
                                myProxy[3]
                            )
                        )
                        setDefaultCredentialsProvider(credentialsProvider)
                    }

                }
            }
        )

    }
    return client

}