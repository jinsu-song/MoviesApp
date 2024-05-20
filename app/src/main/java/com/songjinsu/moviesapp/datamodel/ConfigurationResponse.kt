package com.songjinsu.moviesapp.datamodel


import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("change_keys")
    val changeKeys: List<String?>?,
    @SerializedName("images")
    val images: Images?
)