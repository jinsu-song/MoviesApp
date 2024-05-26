package com.songjinsu.moviesapp.common

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.songjinsu.moviesapp.ui.datamodel.ConfigurationResponse
import com.songjinsu.moviesapp.net.ApiPaths
import com.songjinsu.moviesapp.net.HttpRequest

object App : Application() {

    lateinit var configuration: ConfigurationResponse
    val call = HttpRequest
    override fun onCreate() {
        super.onCreate()
    }

    fun getConfiguration(context: Context, success: () -> Unit) {
        val methodName : String = "getConfiguration()"
        call.requestGet(
            ApiPaths.makeFullUrl(ApiPaths.CONFIGURATION),
            ConfigurationResponse::class.java,
            context,
            { response ->
                if (response == null) {
                    Toast.makeText(context, "Response is Null at $methodName", Toast.LENGTH_SHORT).show()
                } else {
                    configuration = response
                    success()
                }

            }, { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun openYoutube(videoId: String, context: Context) {
        val packageManager : PackageManager = context.packageManager
        val appInstalledCheckIntent = packageManager.getLaunchIntentForPackage(StaticParameter.YOUTUBE_PACKAGE_NAME)

        if (appInstalledCheckIntent == null) {
            val appOpenIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(StaticParameter.getYouTubeUri(videoId)))
            context.startActivity(appOpenIntent)
        } else {

            val browserIntent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(StaticParameter.getYouTubeSiteUrl(videoId)))
            context.startActivity(browserIntent)
        }
    }
}