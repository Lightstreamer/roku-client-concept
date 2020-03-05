import client.AsyncTlcpPost
import platform.AssociativeArrayOf
import platform.UrlEvent
import platform.wait

suspend fun main() {
    val request = AssociativeArrayOf(
            "LS_polling" to "true",
            "LS_polling_millis" to "0",
            "LS_cid" to "mgQkwtwdysogQz2BJ4Ji kOj2Bg",
            "LS_adapter_set" to "WELCOME_HOME"
    )
    // send HTTP post
    val asyncPost = AsyncTlcpPost(
            server = "push.lightstreamer.com",
            action = "create_session",
            request = request
    )
    val urlEvent = wait(0, asyncPost)

    if (urlEvent is UrlEvent) {
        println("Response code " + urlEvent.responseCode)
        println(urlEvent.string)
    }
}
