package com.nagesh.wikipediasearch.ui.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nagesh.wikipediasearch.BuildConfig
import com.nagesh.wikipediasearch.R
import com.nagesh.wikipediasearch.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*


class WikipediaWebViewActivity : BaseActivity() {
    private lateinit var webViewViewModel: WebViewViewModel
    private lateinit var searchTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webViewViewModel = ViewModelProviders.of(this, viewModelFactory).get(WebViewViewModel::class.java)
        getIntentParams()
        setupProgressBar(progress)
        setupToolbar(toolbar, searchTitle, true)
        setupWebView()
        setupObservers()
        webViewViewModel.processSearchQuery(BuildConfig.WIKIPEDIA_BASE_URL, searchTitle)
    }

    private fun getIntentParams() {
        searchTitle = intent.getStringExtra(EXTRAS_SEARCH_TITLE)
    }

    private fun setupObservers() {
        webViewViewModel.getWebViewViewState().observe(this, Observer { setViewState(it) })
    }

    private fun setViewState(webViewViewState: WebViewViewState) = when (webViewViewState) {
        is Loading -> setProgress(true)
        is WebUrl -> loadUrl(webViewViewState.url)

    }

    private fun loadUrl(url: String) {
        webView.loadUrl(url)
    }

    private fun setupWebView() {
        webView.apply {
            webViewClient = WebViewClient()
        }
    }

    companion object {
        private const val EXTRAS_SEARCH_TITLE = "extras_search_title"

        fun start(context: Context, searchTitle: String) {
            val intent = Intent(context, WikipediaWebViewActivity::class.java)
            intent.putExtra(EXTRAS_SEARCH_TITLE, searchTitle)
            context.startActivity(intent)
        }
    }
}
