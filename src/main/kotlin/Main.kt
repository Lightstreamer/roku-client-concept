import platform.AsyncPostFromString
import platform.UrlEvent
import platform.wait

suspend fun main() {
    // Lightstreamer public demo server URL
    val url = "https://push.lightstreamer.com/lightstreamer/create_session.txt?LS_protocol=TLCP-2.1.0"
    // "create_session" parameters
    val payload = "LS_polling=true&LS_polling_millis=0&LS_cid=mgQkwtwdysogQz2BJ4Ji%20kOj2Bg&LS_adapter_set=WELCOME_HOME"
    // send HTTP post
    val urlEvent = wait(0, AsyncPostFromString(url, payload))

    if (urlEvent is UrlEvent) {
        /*
           print the server response, ie:
            CONOK,Scb4b098a6bfd8345Mf16T8462942,50000,0,*
            SERVNAME,Lightstreamer HTTPS Server
            CLIENTIP,1.2.3.4
            LOOP,0
         */
        println("Response code " + urlEvent.responseCode)
        println(urlEvent.string)
    }
}
