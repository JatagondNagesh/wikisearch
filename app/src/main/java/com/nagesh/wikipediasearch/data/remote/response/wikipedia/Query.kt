package com.nagesh.wikipediasearch.data.remote.response.wikipedia

import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("pages")
    val pages: List<Page>?,
    @SerializedName("redirects")
    val redirects: List<Redirect>
)