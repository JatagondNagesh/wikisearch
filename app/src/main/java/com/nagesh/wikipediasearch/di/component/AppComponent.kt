package com.nagesh.wikipediasearch.di.component

import android.app.Application
import com.nagesh.wikipediasearch.WikipediaSearchApplication
import com.nagesh.wikipediasearch.di.module.ActivityBuildersModule
import com.nagesh.wikipediasearch.di.module.AppModule
import com.nagesh.wikipediasearch.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuildersModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(wikipediaSearchApplication: WikipediaSearchApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
