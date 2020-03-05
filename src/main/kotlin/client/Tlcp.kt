package client

import kotlinx.coroutines.Deferred
import platform.AssociativeArray
import platform.AsyncPostFromString
import platform.UrlEvent

/**
 * Send a generic request to a LS server
 */
fun AsyncTlcpPost(server: String, action: String, request: AssociativeArray): Deferred<UrlEvent?> {
    val url = "https://" + server + "/lightstreamer/" + action + ".txt?LS_protocol=TLCP-2.1.0"
    val payload = TlcpEncodeRequest(request)
    return AsyncPostFromString(url, payload)
}

/**
 * Encode a TLCP request using the percent encoding
 */
fun TlcpEncodeRequest(request: AssociativeArray): String {
    var encoded = ""
    for ((key, value) in request) {
        encoded = encoded + key + "=" + TlcpEncodeString(value) + "&"
    }
    return encoded
}

/*
 * Encode the text using the TLCP percent encoding
 */
fun TlcpEncodeString(text: String): String {
    var res = ""
    for (char in text) {
        val encoded = when (char) {
            // line delimiters
            '\r' -> "%0D"
            '\n' -> "%0A"
            // used for percent-encoding
            ' ' -> "%20"
            '%' -> "%25"
            '+' -> "%2B"
            // parameter delimiters
            '&' -> "%26"
            '=' -> "%3D"
            // otherwise encoding not required
            // includes all non-ascii characters
            else -> char.toString()
        }
        res = res + encoded
    }
    return res
}
