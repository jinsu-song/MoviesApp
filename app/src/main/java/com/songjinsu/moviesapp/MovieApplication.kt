package com.songjinsu.moviesapp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.songjinsu.moviesapp.common.StaticParameter
import com.songjinsu.moviesapp.ui.datamodel.ConfigurationResponse
import com.songjinsu.moviesapp.data.network.ApiPaths
import com.songjinsu.moviesapp.data.network.HttpRequest

object MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}