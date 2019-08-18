package com.nagesh.wikipediasearch.ui.search

import com.nagesh.wikipediasearch.data.remote.response.wikipedia.Page


sealed class WikipediaViewState
data class NetworkError(val message: String?) : WikipediaViewState()
object Loading : WikipediaViewState()
data class Success(val wikipediaPageList: List<Page>?) : WikipediaViewState()