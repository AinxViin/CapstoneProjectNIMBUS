package com.example.capstoneproject.utils

import com.example.capstoneproject.data.pref.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CustomCookieJar(private val userPreference: UserPreference) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookieString = cookies.joinToString("; ") { it.toString() }
        CoroutineScope(Dispatchers.IO).launch {
            userPreference.saveCookies(cookieString)
        }
        println("Cookies saved for ${url.host}: $cookies")
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookieString = userPreference.getCookies() ?: return emptyList()
        val cookies = cookieString.split("; ").mapNotNull { Cookie.parse(url, it) }
        println("Cookies loaded for ${url.host}: $cookies")
        return cookies
    }
}