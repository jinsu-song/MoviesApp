package com.songjinsu.moviesapp.net

import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.songjinsu.moviesapp.BuildConfig
import java.io.UnsupportedEncodingException

class GsonRequest<T>(
    url: String,
    method: Int,
    private val clazz: Class<T>,
    listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : JsonRequest<T>(method, url, null, listener, errorListener) {

    private val gson = Gson()

    override fun getHeaders(): MutableMap<String, String> {
        val headers = mapOf(
            "Authorization" to BuildConfig.API_KEY,
            "accept" to "application/json"
        )
        return headers.toMutableMap()
    }

    override fun parseNetworkResponse(response: com.android.volley.NetworkResponse): Response<T> {
        return try {
            val json = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers, "utf-8")))
            Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(com.android.volley.ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(com.android.volley.ParseError(e))
        }
    }
}