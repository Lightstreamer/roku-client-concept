package platform

import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

typealias AssociativeArray = Map<String, String>

data class UrlEvent(val responseCode: Int, val string: String?)

fun AsyncPostFromString(url: String, payload: String): Deferred<UrlEvent?> =
        GlobalScope.async(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.outputStream.write(payload.toByteArray())
                val content = connection.inputStream.bufferedReader().readText()
                connection.disconnect()
                UrlEvent(responseCode = connection.responseCode, string = content)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

/**
 * Ie: { key1: "value", key2: "55", key3: "5+3" }
 */
fun AssociativeArrayOf(vararg pairs: Pair<String, String>): AssociativeArray = mapOf(*pairs)

suspend fun <T> wait(millis: Int, future: Deferred<T>): T? =
        if (millis == 0) future.await()
        else withTimeoutOrNull(millis.toLong()) { future.await() }
