package com.nagesh.wikipediasearch.data.remote.response.wikipedia

import com.google.gson.annotations.SerializedName

data class Terms(
    @SerializedName("description")
    val description: List<String>?
)