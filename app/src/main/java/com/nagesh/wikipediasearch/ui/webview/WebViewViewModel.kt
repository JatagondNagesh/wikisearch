package com.nagesh.wikipediasearch.ui.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nagesh.wikipediasearch.base.BaseViewModel
import com.nagesh.wikipediasearch.utils.Constants
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class WebViewViewModel @Inject constructor(
    compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {
    private val webViewViewStateLiveData: MutableLiveData<WebViewViewState> = MutableLiveData()

    fun processSearchQuery(wikipediaBaseUrl: String, searchTitle: String) {
        webViewViewStateLiveData.value = Loading
        val url = wikipediaBaseUrl + getStringDelimiters(searchTitle, Constants.SPACE, Constants.UNDERSCORE)
        webViewViewStateLiveData.value = WebUrl(url)
    }

    private fun getStringDelimiters(string: String, delimiterToBeReplaced: String, delimiterToReplace: String) =
        string.split(delimiterToBeReplaced).joinToString(delimiterToReplace) { it.capitalize() }

    fun getWebViewViewState(): LiveData<WebViewViewState> {
        return webViewViewStateLiveData
    }

}