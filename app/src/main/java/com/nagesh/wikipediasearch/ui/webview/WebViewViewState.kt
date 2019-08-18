package com.nagesh.wikipediasearch.ui.webview


sealed class WebViewViewState
data class WebUrl(val url: String) : WebViewViewState()
object Loading : WebViewViewState()