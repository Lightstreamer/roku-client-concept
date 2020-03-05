package platform

import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeoutOrNull
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

data class UrlEvent(val responseCode: Int, val string: String?)

fun AsyncPostFromString(url: String, payload: String): CompletableFuture<UrlEvent?> =
        HttpClient.newHttpClient()
                .sendAsync(
                        HttpRequest.newBuilder(URI(url)).POST(HttpRequest.BodyPublishers.ofString(payload)).build(),
                        HttpResponse.BodyHandlers.ofString()
                ).handle { response, exception ->
                    if (exception == null) UrlEvent(responseCode = response.statusCode(), string = response.body())
                    else null
                }

suspend fun <T> wait(millis: Int, future: CompletableFuture<T>): T? =
        if (millis == 0) future.await()
        else withTimeoutOrNull(millis.toLong()) { future.await() }
