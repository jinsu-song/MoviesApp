package com.songjinsu.moviesapp.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.songjinsu.moviesapp.R
import com.songjinsu.moviesapp.common.StaticParameter
import com.songjinsu.moviesapp.data.network.ApiPaths
import com.songjinsu.moviesapp.data.network.HttpRequest
import com.songjinsu.moviesapp.ui.datamodel.ConfigurationResponse

class MainViewModel(val fm: FragmentManager) : ViewModel() {

    val call = HttpRequest
    lateinit var configuration: ConfigurationResponse

    fun addFragment(fragment: Fragment, slide: Int = 1) {
        val tag = fragment::class.java.simpleName
        val ft = fm.beginTransaction()

        when(slide) {
            1 -> ft.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            2 -> ft.setCustomAnimations(
                R.anim.bottom_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.bottom_out
            )
        }


        ft.add(R.id.fragment_container_view, fragment)
        .addToBackStack(tag)
        .commit()
    }

    fun popFragment() {
        fm.popBackStack()
    }

    fun openYouTube(videoId: String, context: Context) {
        val packageManager : PackageManager = context.packageManager
        val appInstalledCheckIntent = packageManager.getLaunchIntentForPackage(StaticParameter.YOUTUBE_PACKAGE_NAME)

        if (appInstalledCheckIntent == null) {
            val appOpenIntent : Intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                StaticParameter.getYouTubeUri(
                    videoId
                )
            ))
            context.startActivity(appOpenIntent)
        } else {

            val browserIntent : Intent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                StaticParameter.getYouTubeSiteUrl(
                    videoId
                )
            ))
            context.startActivity(browserIntent)
        }
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
}