package com.songjinsu.moviesapp.net

import android.content.Context
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.android.volley.Response

object HttpRequest {
    fun <T> request(url: String, clazz: Class<T>, context: Context, success: CallbackSuccess<T>, failure: CallbackFailure) {
        val queue = Volley.newRequestQueue(context)

        val request = GsonRequest(
            url,
            clazz,
            Response.Listener {response ->
                response
                val response = response as? T
                success.onSuccess(response)
            },
            Response.ErrorListener {error ->
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