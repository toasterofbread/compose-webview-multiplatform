package com.kevinnzou.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import com.multiplatform.webview.cookie.Cookie
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.flow.filter

/**
 * Created By Kevin Zou On 2023/9/8
 *
 * Basic Sample of WebView
 *
 * Note: Developers targeting the Desktop platform should refer to
 * [README.desktop.md](https://github.com/KevinnZou/compose-webview-multiplatform/blob/main/README.desktop.md)
 * for setup instructions first.
 */
@Composable
internal fun BasicWebViewSample(navHostController: NavHostController? = null) {
    val initialUrl = "https://github.com/KevinnZou/compose-webview-multiplatform"
    val state =
        rememberWebViewState(url = initialUrl) {
            logSeverity = KLogSeverity.Debug
            customUserAgentString =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1) AppleWebKit/625.20 (KHTML, like Gecko) Version/14.3.43 Safari/625.20"
            iOSWebSettings.isInspectable = true
        }

        onDispose { }
    }
    val navigator =
        rememberWebViewNavigator(
            urlRequestInterceptor = // resourceRequestInterceptor to intercept resource requests
                object : RequestInterceptor {
                    override fun onInterceptRequest(
                        request: WebRequest,
                        navigator: WebViewNavigator,
                    ): WebRequestInterceptResult {
                        request.let {
                            Logger.i { "Sample onInterceptRequest: $it" }
                        }
                        return if (request.url.contains("github")) {
                            WebRequestInterceptResult.Modify(
                                WebRequest(
                                    url = "https://kotlinlang.org/docs/multiplatform.html",
                                    headers = mutableMapOf("info" to "test"),
                                ),
                            )
                        } else {
                            WebRequestInterceptResult.Allow
                        }
                    }
                },
        )
    var textFieldValue by remember(state.lastLoadedUrl) {
        mutableStateOf(state.lastLoadedUrl)
    }
    MaterialTheme {
        Scaffold { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
            ) {
                TopAppBar(
                    modifier =
                        Modifier
                            .background(
                                color = MaterialTheme.colors.primary,
                            ).padding(
                                top =
                                    WindowInsets.statusBars
                                        .asPaddingValues()
                                        .calculateTopPadding(),
                            ),
                    title = { Text(text = "WebView Sample") },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (navigator.canGoBack) {
                                navigator.navigateBack()
                            } else {
                                navHostController?.popBackStack()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    },
                )

                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.errorsForCurrentRequest.isNotEmpty()) {
                            Image(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Error",
                                colorFilter = ColorFilter.tint(Color.Red),
                                modifier =
                                    Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(8.dp),
                            )
                        }

                        OutlinedTextField(
                            value = textFieldValue ?: "",
                            onValueChange = { textFieldValue = it },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    Button(
                        onClick = {
                            textFieldValue?.let {
                                navigator.loadUrl(it)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Text("Go")
                    }
                }

                val loadingState = state.loadingState
                if (loadingState is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = loadingState.progress,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                WebView(
                    state = state,
                    modifier =
                        Modifier
                            .fillMaxSize(),
                    navigator = navigator,
                )
            }
        }
    }
}

@Composable
internal fun CookieSample(state: WebViewState) {
    LaunchedEffect(state) {
        snapshotFlow { state.loadingState }
            .filter { it is LoadingState.Finished }
            .collect {
                state.cookieManager.setCookie(
                    "https://github.com",
                    Cookie(
                        name = "test",
                        value = "value",
                        domain = "github.com",
                        expiresDate = 1896863778,
                    ),
                )
                Logger.i {
                    "cookie: ${state.cookieManager.getCookies("https://github.com")}"
                }
                state.cookieManager.removeAllCookies()
                Logger.i {
                    "cookie: ${state.cookieManager.getCookies("https://github.com")}"
                }
            }
    }
}
