package com.nagesh.wikipediasearch.di.module

import android.app.Application
import android.content.Context
import com.nagesh.wikipediasearch.utils.network.NetworkHelper
import com.nagesh.wikipediasearch.utils.rx.RxSchedulerProvider
import com.nagesh.wikipediasearch.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @JvmStatic
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @JvmStatic
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @JvmStatic
    @Provides
    fun provideNetworkHelper(context: Context): NetworkHelper = NetworkHelper(context)
}
