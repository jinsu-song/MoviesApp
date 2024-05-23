package com.songjinsu.moviesapp.common

object StaticParameter {

    // 유튜브 패키지명
    const val YOUTUBE_PACKAGE_NAME : String = "com.google.android.youtube"

    fun YOUTUBE_APP_URI(videoId: String) : String = "vnd.youtube:${videoId}"

    // 유튜브 사이트 url
    fun YOUTUBE_SITE_URL(videoId: String) : String = "https://www.youtube.com/watch?v=${videoId}"

}