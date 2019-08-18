package com.nagesh.wikipediasearch.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nagesh.wikipediasearch.R
import com.nagesh.wikipediasearch.base.BaseActivity
import com.nagesh.wikipediasearch.data.remote.response.wikipedia.Page
import com.nagesh.wikipediasearch.ui.webview.WikipediaWebViewActivity
import kotlinx.android.synthetic.main.activity_wikipedia_search.*


class WikipediaSearchActivity : BaseActivity(), WikiPagesAdapter.Callback {
    private lateinit var wikipediaSearchViewModel: WikipediaSearchViewModel
    private lateinit var wikiPagesAdapter: WikiPagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wikipedia_search)
        wikipediaSearchViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(WikipediaSearchViewModel::class.java)
        setupProgressBar(progress)
        setupToolbar(toolbar, getString(R.string.app_name), false)
        setupWikiPagesRecyclerView()
        setupObservers()
        setupSearchViewQueryTextListener()
    }

    private fun setupWikiPagesRecyclerView() {
        wikiPagesAdapter = WikiPagesAdapter(this)
        rvWikiPages.apply {
            layoutManager = LinearLayoutManager(this@WikipediaSearchActivity)
            setHasFixedSize(true)
            adapter = wikiPagesAdapter
            addItemDecoration(DividerItemDecoration(this@WikipediaSearchActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSearchViewQueryTextListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                wikipediaSearchViewModel.searchForQuery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                wikipediaSearchViewModel.searchForQuery(newText)
                return true
            }

        })
    }

    private fun setupObservers() {
        wikipediaSearchViewModel.getWikipediaState().observe(this, Observer { setViewState(it) })
    }

    private fun setViewState(wikipediaViewState: WikipediaViewState) = when (wikipediaViewState) {
        is Loading -> setProgress(true)
        is NetworkError -> {
            setProgress(false)
            showError(wikipediaViewState.message!!)
        }
        is Success -> {
            setProgress(false)
            showWikipages(wikipediaViewState.wikipediaPageList)
        }
    }


    private fun showWikipages(wikipediaPageList: List<Page>?) {
        wikiPagesAdapter.updateList(wikipediaPageList?.toMutableList())
    }

    override fun onWikiPageClick(page: Page) {
        WikipediaWebViewActivity.start(this, page.title)
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, WikipediaSearchActivity::class.java)
            context.startActivity(starter)
        }
    }
}
