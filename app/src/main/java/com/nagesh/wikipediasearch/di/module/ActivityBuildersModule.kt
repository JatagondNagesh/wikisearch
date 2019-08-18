package com.nagesh.wikipediasearch.di.module

import com.nagesh.wikipediasearch.ui.search.WikipediaSearchActivity
import com.nagesh.wikipediasearch.ui.webview.WikipediaWebViewActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun bindWikipediaSearchActivity(): WikipediaSearchActivity

    @ContributesAndroidInjector
    abstract fun bindWebViewActivity(): WikipediaWebViewActivity
}
