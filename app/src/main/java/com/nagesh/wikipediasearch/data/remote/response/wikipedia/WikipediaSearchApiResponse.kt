package com.nagesh.wikipediasearch.data.remote.response.wikipedia


import com.google.gson.annotations.SerializedName

data class WikipediaSearchApiResponse(
    @SerializedName("batchcomplete")
    val batchcomplete: Boolean,
    @SerializedName("continue")
    val continueX: Continue,
    @SerializedName("query")
    val query: Query?
)