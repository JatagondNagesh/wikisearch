package com.nagesh.wikipediasearch.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nagesh.wikipediasearch.di.ViewModelKey
import com.nagesh.wikipediasearch.di.WikipediaSearchViewModelFactory
import com.nagesh.wikipediasearch.ui.search.WikipediaSearchViewModel
import com.nagesh.wikipediasearch.ui.webview.WebViewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WikipediaSearchViewModel::class)
    abstract fun bindWikipediaViewModel(wikipediaViewModel: WikipediaSearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebViewViewModel::class)
    abstract fun bindWebViewViewModel(webViewViewModel: WebViewViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WikipediaSearchViewModelFactory): ViewModelProvider.Factory
}
