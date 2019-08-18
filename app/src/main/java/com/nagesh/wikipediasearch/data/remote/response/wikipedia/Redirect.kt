package com.nagesh.wikipediasearch.data.remote.response.wikipedia

import com.google.gson.annotations.SerializedName

data class Redirect(
    @SerializedName("from")
    val from: String,
    @SerializedName("index")
    val index: Int,
    @SerializedName("to")
    val to: String
)