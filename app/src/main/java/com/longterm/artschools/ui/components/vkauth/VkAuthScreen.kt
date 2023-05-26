package com.longterm.artschools.ui.components.vkauth

import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.viewinterop.AndroidView
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.vkauth.components.AuthStatus
import com.longterm.artschools.ui.components.vkauth.components.AuthWebViewClient
import com.longterm.artschools.ui.components.vkauth.components.OnVkAuthResult
import com.longterm.artschools.ui.components.vkauth.components.VkScope
import java.net.URLEncoder
import java.util.regex.Pattern

private fun getUrlArg(name: String, value: String) = String.format(
    "%s=%s",
    URLEncoder.encode(name, "UTF-8"),
    URLEncoder.encode(value, "UTF-8")
) + "&"

@Composable
fun VkAuthScreen(
    callback: OnVkAuthResult,
    onComplete: () -> Unit
) {
    val authParams = buildString {
        append("https://oauth.vk.com/authorize?")
        append(getUrlArg("client_id", integerResource(id = R.integer.com_vk_sdk_AppId).toString()))
        append(getUrlArg("client_secret", "bRUQMWv798QurrntWakO"))
        append(getUrlArg("redirect_uri", "https://oauth.vk.com/blank.html"))
        append(getUrlArg("display", "mobile"))
        append(getUrlArg("scope", VkScope.SCOPE))
        append(getUrlArg("response_type", "code"))
        append(getUrlArg("v", "5.131"))
        append(getUrlArg("state", "12345"))
        append(getUrlArg("revoke", "1"))
    }

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = AuthWebViewClient(it) {
                when (it) {
                    AuthStatus.ERROR -> {
                        Toast.makeText(context, "Не верный логин или пароль", Toast.LENGTH_LONG).show()
                    }

                    AuthStatus.BLOCKED -> {
                        CookieManager.getInstance().removeAllCookies(null)
                        loadUrl(authParams)
                        Toast.makeText(context, "Аккаунт заблокирован", Toast.LENGTH_LONG).show()
                    }

                    AuthStatus.SUCCESS -> {
                        val url = url!!
                        val tokenMather = Pattern.compile("code=\\w+").matcher(url)
                        // Если есть совпадение с патерном.
                        if (tokenMather.find()) {
                            val token = tokenMather.group().replace("code=".toRegex(), "")
                            // Если токен и id получен.
                            if (token.isNotEmpty()) {
                                callback.onResult(token, "0")
                                onComplete()
                            }
                        }
                    }

                    AuthStatus.AUTH,
                    AuthStatus.CONFIRM -> Unit
                }
            }
            loadUrl(authParams)
        }
    }, update = {
        it.loadUrl(authParams)
    })
}