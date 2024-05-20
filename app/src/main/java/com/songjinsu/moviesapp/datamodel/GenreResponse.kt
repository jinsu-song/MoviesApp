package com.songjinsu.moviesapp.datamodel


import com.google.gson.annotations.SerializedName

data class GenreResponse(
//    @SerializedName("genres")
    val genres: List<Genre?>?
)

data class Genre(
//    @SerializedName("id")
    val id: Int?,
//    @SerializedName("name")
    val name: String?
)