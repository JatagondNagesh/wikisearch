package com.nagesh.wikipediasearch.data.remote

object Endpoints {
    const val SEARCH_QUERY = "w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1" +
                "&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpslimit=10"
}