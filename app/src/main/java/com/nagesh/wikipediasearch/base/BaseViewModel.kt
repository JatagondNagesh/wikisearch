package com.nagesh.wikipediasearch.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val compositeDisposable: CompositeDisposable
) : ViewModel() {
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}