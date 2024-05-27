package com.songjinsu.moviesapp.data.network

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.Request.Method
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley

object HttpRequest {
    fun <T> requestGet(url: String, clazz: Class<T>, context: Context, success: CallbackSuccess<T>, failure: CallbackFailure) {
        val queue = Volley.newRequestQueue(context)

        val request = GsonRequest(
            url,
            Method.GET,
            clazz,
            { response ->
                val response = response as? T
                success.onSuccess(response)
            },
            { error ->
                failure.onFailure(error)
            }
        )
        queue.add(request)
    }

    // 이미지 로드
    fun <T> imageLoad(url: String, clazz: Class<T>, context: Context, success: CallbackSuccess<T>, failure: CallbackFailure) {
        val queue = Volley.newRequestQueue(context)

        val imageRequest = ImageRequest(
            url,
            { response ->
                val res = response as? T
                success.onSuccess(res)
            },
            0,
            0,
            ImageView.ScaleType.FIT_XY,
            Bitmap.Config.RGB_565,
            { error ->
                failure.onFailure(error)
            }
        )
        queue.add(imageRequest)
    }

    fun interface CallbackSuccess <T> {
        fun onSuccess(result : T?)
    }

    fun interface CallbackFailure {
        fun onFailure(error: VolleyError)
    }
}