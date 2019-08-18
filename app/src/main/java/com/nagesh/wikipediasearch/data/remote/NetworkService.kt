package com.nagesh.wikipediasearch.data.remote

import com.nagesh.wikipediasearch.data.remote.response.wikipedia.WikipediaSearchApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(Endpoints.SEARCH_QUERY)
    fun searchForQuery(
        @Query("gpssearch") searchQuery: String
    ): Single<WikipediaSearchApiResponse>

}