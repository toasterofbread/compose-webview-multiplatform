package com.multiplatform.webview.setting

import androidx.compose.ui.graphics.Color

/**
 * Created By Kevin Zou On 2023/9/20
 */
sealed class PlatformWebSettings {
    /**
     * Android web settings
     */
    data class AndroidWebSettings(
        /**
         * Enables or disables file access within WebView.
         * Note that this enables or disables file system access only. Assets and resources
         * are still accessible using file:///android_asset and file:///android_res.
         * <p class="note">
         * <b>Note:</b> Apps should not open {@code file://} URLs from any external source in
         * WebView, don't enable this if your app accepts arbitrary URLs from external sources.
         * It's recommended to always use
         * <a href="{@docRoot}reference/androidx/webkit/WebViewAssetLoader">
         * androidx.webkit.WebViewAssetLoader</a> to access files including assets and resources over
         * {@code http(s)://} schemes, instead of {@code file://} URLs. To prevent possible security
         * issues targeting {@link android.os.Build.VERSION_CODES#Q} and earlier, you should explicitly
         * set this value to {@code false}.
         * <p>
         * The default value is {@code true} for apps targeting
         * {@link android.os.Build.VERSION_CODES#Q} and below, and {@code false} when targeting
         * {@link android.os.Build.VERSION_CODES#R} and above.
         */
        var allowFileAccess: Boolean = false,
        /**
         * The text zoom of the page in percent. The default is 100.
         *
         * @param textZoom the text zoom in percent
         */
        var textZoom: Int = 100,
        /**
         * Whether the WebView should enable support for the &quot;viewport&quot;
         * HTML meta tag or should use a wide viewport.
         * When the value of the setting is {@code false}, the layout width is always set to the
         * width of the WebView control in device-independent (CSS) pixels.
         * When the value is {@code true} and the page contains the viewport meta tag, the value
         * of the width specified in the tag is used. If the page does not contain the tag or
         * does not provide a width, then a wide viewport will be used.
         *
         */
        var useWideViewPort: Boolean = false,
        /**
         * The standard font family name. The default is "sans-serif".
         *
         * @param font a font family name
         */
        var standardFontFamily: String = "sans-serif",
        /**
         * The default font size. The default is 16.
         *
         * @param size a non-negative integer between 1 and 72. Any number outside
         *             the specified range will be pinned.
         */
        var defaultFontSize: Int = 16,
        /**
         * Sets whether the WebView should load image resources. Note that this method
         * controls loading of all images, including those embedded using the data
         * URI scheme. Use {@link #setBlockNetworkImage} to control loading only
         * of images specified using network URI schemes. Note that if the value of this
         * setting is changed from {@code false} to {@code true}, all images resources referenced
         * by content currently displayed by the WebView are loaded automatically.
         * The default is {@code true}.
         *
         * @param flag whether the WebView should load image resources
         */
        var loadsImagesAutomatically: Boolean = true,
        /**
         * Control whether algorithmic darkening is allowed.
         *
         * <p class="note">
         * <b>Note:</b> This API and the behaviour described only apply to apps with
         * {@code targetSdkVersion} &ge; {@link android.os.Build.VERSION_CODES#TIRAMISU}.
         *
         * <p>
         * WebView always sets the media query {@code prefers-color-scheme} according to the app's
         * theme attribute {@link android.R.styleable#Theme_isLightTheme isLightTheme}, i.e.
         * {@code prefers-color-scheme} is {@code light} if isLightTheme is true or not specified,
         * otherwise it is {@code dark}. This means that the web content's light or dark style will
         * be applied automatically to match the app's theme if the content supports it.
         *
         * <p>
         * Algorithmic darkening is disallowed by default.
         * <p>
         * If the app's theme is dark and it allows algorithmic darkening, WebView will attempt to
         * darken web content using an algorithm, if the content doesn't define its own dark styles
         * and doesn't explicitly disable darkening.
         *
         * <p>
         * If Android is applying Force Dark to WebView then WebView will ignore the value of
         * this setting and behave as if it were set to true.
         *
         * <p>
         * The deprecated {@link #setForceDark} and related API are no-ops in apps with
         * {@code targetSdkVersion} &ge; {@link android.os.Build.VERSION_CODES#TIRAMISU},
         * but they still apply to apps with
         * {@code targetSdkVersion} &lt; {@link android.os.Build.VERSION_CODES#TIRAMISU}.
         *
         * <p>
         * The below table summarizes how APIs work with different apps.
         *
         * <table border="2" width="85%" align="center" cellpadding="5">
         *     <thead>
         *         <tr>
         *             <th>App</th>
         *             <th>Web content which uses {@code prefers-color-scheme}</th>
         *             <th>Web content which does not use {@code prefers-color-scheme}</th>
         *         </tr>
         *     </thead>
         *     <tbody>
         *     <tr>
         *         <td>App with {@code isLightTheme} True or not set</td>
         *         <td>Renders with the light theme defined by the content author.</td>
         *         <td>Renders with the default styling defined by the content author.</td>
         *     </tr>
         *     <tr>
         *         <td>App with Android forceDark in effect</td>
         *         <td>Renders with the dark theme defined by the content author.</td>
         *         <td>Renders with the styling modified to dark colors by an algorithm
         *             if allowed by the content author.</td>
         *     </tr>
         *     <tr>
         *         <td>App with {@code isLightTheme} False,
         *            {@code targetSdkVersion} &lt; {@link android.os.Build.VERSION_CODES#TIRAMISU},
         *             and has {@code FORCE_DARK_AUTO}</td>
         *         <td>Renders with the dark theme defined by the content author.</td>
         *         <td>Renders with the default styling defined by the content author.</td>
         *     </tr>
         *     <tr>
         *         <td>App with {@code isLightTheme} False,
         *            {@code targetSdkVersion} &ge; {@link android.os.Build.VERSION_CODES#TIRAMISU},
         *             and {@code setAlgorithmicDarkening(false)}</td>
         *         <td>Renders with the dark theme defined by the content author.</td>
         *         <td>Renders with the default styling defined by the content author.</td>
         *     </tr>
         *     <tr>
         *         <td>App with {@code isLightTheme} False,
         *            {@code targetSdkVersion} &ge; {@link android.os.Build.VERSION_CODES#TIRAMISU},
         *             and {@code setAlgorithmicDarkening(true)}</td>
         *         <td>Renders with the dark theme defined by the content author.</td>
         *         <td>Renders with the styling modified to dark colors by an algorithm if allowed
         *             by the content author.</td>
         *     </tr>
         *     </tbody>
         * </table>
         * </p>
         *
         */
        var isAlgorithmicDarkeningAllowed: Boolean = false,
        /**
         * whether Safe Browsing is enabled. Safe Browsing allows WebView to
         * protect against malware and phishing attacks by verifying the links.
         */
        var safeBrowsingEnabled: Boolean = true,
        /**
         * Whether the DOM storage API is enabled. The default value is {@code false}.
         */
        var domStorageEnabled: Boolean = false,
        /**
         * Whether the a user gesture is required to play media. The default is {@code true}.
         */
        var mediaPlaybackRequiresUserGesture: Boolean = true,
        /**
         * Controls whether the `RESOURCE_PROTECTED_MEDIA_ID` permission requests should be
         * automatically granted or not. Necessary to be able to play back DRM protected media
         * inside the WebView.
         * The default is {@code false}.
         */
        var allowProtectedMedia: Boolean = false,
        /**
         * Controls whether the `RESOURCE_MIDI_SYSEX` permission requests should be automatically
         * granted or not. The resource will allow sysex messages to be sent to or received from MIDI
         * devices. Available on API level 21 and above.
         */
        var allowMidiSysexMessages: Boolean = false,
        /**
         * Controls whether the default video poster (a gray, pixelated play button) should be hidden.
         */
        var hideDefaultVideoPoster: Boolean = false,
        /**
         * The Layer Type of the WebView.
         * Default is [LayerType.HARDWARE]
         */
        var layerType: Int = LayerType.HARDWARE,
        /**
         * Enables sandboxing of local file access via WebViewAssetLoader.
         *
         * When true, instead of using file:// URLs (which are insecure and restrict modern features),
         * the WebView uses WebViewAssetLoader to serve local files (assets/resources/internal storage)
         * over secure virtual https:// URLs. This improves compatibility with cookies, service workers,
         * and CSP (Content Security Policy), and prevents file access vulnerabilities.
         *
         * This must be used in combination with a proper PathHandler setup in your WebView client
         * (e.g., mapping /app/ to internal files or app assets).
         *
         * For example, if your WebViewAssetLoader maps the path "/app/" to your internal storage,
         * you can load a file by navigating to a virtual URL like:
         * `https://appassets.androidplatform.net/app/index.html`
         * (the standard host used by WebViewAssetLoader)
         * This URL will internally resolve to your app's internal file path. and enable cookies
         * for them as well
         */
        var enableSandbox: Boolean = false,
        /**
         * The virtual subdomain prefix to be used with WebViewAssetLoader for local file access.
         *
         * This is typically set to something like "/app/" or "/assets/" and must match the path
         * used in your PathHandler configuration inside WebViewAssetLoader.
         *
         * When you load a URL such as `https://appassets.androidplatform.net/app/index.html`
         * (the standard host used by WebViewAssetLoader) in your WebView,
         * the WebViewAssetLoader will map it to the correct local file or asset if configured properly.
         * This URL should be used instead of file:// URLs to ensure secure and modern WebView behavior.
         */
        var sandboxSubdomain: String = "/app/",
    ) : PlatformWebSettings() {
        object LayerType {
            const val NONE = 0
            const val SOFTWARE = 1
            const val HARDWARE = 2
        }
    }

    /**
     * Desktop web settings
     */
    data class DesktopWebSettings(
        var offScreenRendering: Boolean = false,
        var transparent: Boolean = true,
        var disablePopupWindows: Boolean = false,
    ) : PlatformWebSettings()

    /**
     * IOS web settings
     */
    data class IOSWebSettings(
        /**
         * The ios default opaque display
         * The default value is {@code false}.
         * When Value is true will turn off these two properties:
         * @param backgroundColor,@param underPageBackgroundColor
         */
        var opaque: Boolean = false,
        /**
         * The background color of the WebView client. The default value is {@code null}.
         * Will use WebSettings backgroundColor when null.
         *
         * @param backgroundColor a color value
         */
        var backgroundColor: Color? = null,
        /**
         * The background color shown when the WebView client scrolls past the bounds of the active page.
         * The default value is {@code null}. Will use WebSettings backgroundColor when null.
         *
         * @param underPageBackgroundColor a color value
         */
        var underPageBackgroundColor: Color? = null,
        /**
         * Whether the WebView bounces when scrolled past content bounds.
         * The default value is {@code true}.
         */
        var bounces: Boolean = true,
        /**
         * Whether horizontal and vertical scrolling is enabled. The default value is {@code true}.
         */
        var scrollEnabled: Boolean = true,
        /**
         * Whether the horizontal scroll indicator is visible. The default value is {@code true}.
         */
        var showHorizontalScrollIndicator: Boolean = true,
        /**
         * Whether the vertical scroll indicator is visible. The default value is {@code true}.
         */
        var showVerticalScrollIndicator: Boolean = true,
        /**
         * Whether a user gesture is required to play media. The default is {@code true}.
         */
        var mediaPlaybackRequiresUserGesture: Boolean = true,
        /**
         * Whether the WebView supports inspection via MacOS Safari. The default value is {@code false}.
         */
        var isInspectable: Boolean = false,
    ) : PlatformWebSettings()

    /**
     * WasmJS web settings
     */
    data class WasmJSWebSettings(
        /**
         * The background color of the iframe WebView. The default value is {@code null}.
         * Will use WebSettings backgroundColor when null.
         *
         * @param backgroundColor a color value
         */
        var backgroundColor: Color? = null,
        /**
         * Whether the iframe should have a border. The default value is {@code false}.
         */
        var showBorder: Boolean = false,
        /**
         * The border style when showBorder is true. The default value is "1px solid #ccc".
         */
        var borderStyle: String = "1px solid #ccc",
        /**
         * Whether the iframe should be sandboxed. The default value is {@code false}.
         * When true, applies sandbox restrictions for security.
         */
        var enableSandbox: Boolean = false,
        /**
         * Sandbox permissions when enableSandbox is true.
         * The default allows scripts, same-origin, and forms.
         */
        var sandboxPermissions: String = "allow-scripts allow-same-origin allow-forms",
        /**
         * Whether to allow fullscreen mode. The default value is {@code true}.
         */
        var allowFullscreen: Boolean = true,
        /**
         * Custom CSS styles to apply to the iframe container.
         * The default value is {@code null}.
         */
        var customContainerStyle: String? = null,
        /**
         * Whether to enable console logging for debugging. The default value is {@code false}.
         */
        var enableConsoleLogging: Boolean = false,
    ) : PlatformWebSettings()
}
