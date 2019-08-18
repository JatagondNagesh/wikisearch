package com.nagesh.wikipediasearch.data.repository

import com.nagesh.wikipediasearch.data.remote.NetworkService
import com.nagesh.wikipediasearch.data.remote.response.wikipedia.WikipediaSearchApiResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WikipediaRepository @Inject constructor(
    private val networkService: NetworkService
) {
    fun searchForQuery(query: String): Single<WikipediaSearchApiResponse> =
        networkService.searchForQuery(query)
}