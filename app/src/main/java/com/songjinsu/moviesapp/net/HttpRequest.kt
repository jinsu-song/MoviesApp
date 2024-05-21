package com.songjinsu.moviesapp.net

import android.content.Context
import com.android.volley.Request.Method
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley

object HttpRequest {
    fun <T> requestGet(url: String, clazz: Class<T>, context: Context, success: CallbackSuccess<T>, failure: CallbackFailure) {
        val queue = Volley.newRequestQueue(context)

        val request = GsonRequest(
            url,
            Method.GET,
            clazz,
            { response ->
                response
                val response = response as? T
                success.onSuccess(response)
            },
            { error ->
                failure.onFailure(error)
            }
        )
        queue.add(request)
    }

    fun interface CallbackSuccess <T> {
        fun onSuccess(result : T?)
    }

    fun interface CallbackFailure {
        fun onFailure(error: VolleyError)
    }
}