package com.nagesh.wikipediasearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nagesh.wikipediasearch.base.BaseViewModel
import com.nagesh.wikipediasearch.data.repository.WikipediaRepository
import com.nagesh.wikipediasearch.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class WikipediaSearchViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    private val wikipediaRepository: WikipediaRepository
) : BaseViewModel(compositeDisposable) {

    private val wikipediaViewStateLiveData: MutableLiveData<WikipediaViewState> = MutableLiveData()
    private val publishSubject = PublishSubject.create<String>()
    private val TIMEOUT = 300

    fun getWikipediaState(): LiveData<WikipediaViewState> {
        return wikipediaViewStateLiveData
    }

    fun searchForQuery(query: String) {
        publishSubject.onNext(query)
        compositeDisposable.add(
            publishSubject
                .debounce(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .filter { s -> s.isNotEmpty() }
                .distinctUntilChanged()
                .switchMapSingle { s -> wikipediaRepository.searchForQuery(s).subscribeOn(schedulerProvider.io()) }
                .subscribe(
                    {
                        wikipediaViewStateLiveData.postValue(Success(it.query?.pages))
                    },
                    {
                        wikipediaViewStateLiveData.postValue(NetworkError(it.message))
                    }
                )

        )
    }
}