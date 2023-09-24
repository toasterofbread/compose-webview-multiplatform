package com.multiplatform.webview.cookie


data class Cookie(
    /**
     * The cookie name.
     * */
    val name: String,

    /**
     * The cookie value.
     * */
    val value: String,

    /**
     * The cookie domain.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val domain: String?,

    /**
     * The cookie expiration date in milliseconds.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val path: String?,

    /**
     * The cookie expiration date in milliseconds.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val expiresDate: Long?,

    /**
     * Whether the cookie is only valid for the current session.
     * */
    val isSessionOnly: Boolean = false,

    /**
     * The cookie same site policy.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val sameSite: HTTPCookieSameSitePolicy?,

    /**
     * Whether the cookie is secure.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val isSecure: Boolean?,

    /**
     * Whether the cookie is HTTP only.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val isHttpOnly: Boolean?,

    /**
     * The cookie maximum age in seconds.
     * available on `Android` only if [androidx.webkit.WebViewFeature.GET_COOKIE_INFO] feature is supported.
     * */
    val maxAge: Long?
) {

    enum class HTTPCookieSameSitePolicy {
        /**
         * The default value; allows cookies to be sent normally.
         */
        NONE,

        /**
         * Cookies are not sent with cross-site requests.
         */
        LAX,

        /**
         * Cookies are only sent with requests originating from the same website.
         */
        STRICT
    }

    override fun toString(): String {
        var cookieValue = "$name=$value; Path=$path"

        if (domain != null) cookieValue += "; Domain=$domain"

        if (expiresDate != null) cookieValue += "; Expires=" + getCookieExpirationDate(expiresDate)

        if (maxAge != null) cookieValue += "; Max-Age=$maxAge"

        if (isSecure != null && isSecure) cookieValue += "; Secure"

        if (isHttpOnly != null && isHttpOnly) cookieValue += "; HttpOnly"

        if (sameSite != null) cookieValue += "; SameSite=$sameSite"

        cookieValue += ";"

        return cookieValue
    }
}

expect fun getCookieExpirationDate(expiresDate: Long): String