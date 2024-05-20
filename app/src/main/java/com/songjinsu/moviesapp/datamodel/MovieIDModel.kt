package com.songjinsu.moviesapp.datamodel

import com.google.gson.annotations.SerializedName


data class ResultMovieIdModel(
//    @SerializedName("results")
    val results: List<MovieIDModel>
)
data class MovieIDModel(
//    @SerializedName("id")
    val id: String,

//    @SerializedName("adult")
    val adult: Boolean
)
