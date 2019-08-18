package com.nagesh.wikipediasearch.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.nagesh.wikipediasearch.BuildConfig
import com.nagesh.wikipediasearch.data.remote.NetworkService
import com.nagesh.wikipediasearch.data.repository.WikipediaRepository
import com.nagesh.wikipediasearch.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
object NetworkModule {

    private const val OFFLINE = "offline"
    private const val ONLINE = "online"
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val HTTP_CACHE = "http-cache"
    private const val HEADER_PRAGMA = "Pragma"
    private const val MAX_TALE = 7
    private const val MAX_AGE = 0
    private const val NETWORK_CALL_TIMEOUT = 60

    @JvmStatic
    @Provides
    @Singleton
    @Named(OFFLINE)
    fun provideOfflineInterceptor(networkHelper: NetworkHelper) = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            if (!networkHelper.isNetworkConnected()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(MAX_TALE, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }

            return chain.proceed(request)
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    @Named(ONLINE)
    fun provideOnlineInterceptor(networkHelper: NetworkHelper) = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl
            if (networkHelper.isNetworkConnected()) {
                cacheControl = CacheControl.Builder()
                    .maxAge(MAX_AGE, TimeUnit.SECONDS)
                    .build()
            } else {
                cacheControl = CacheControl.Builder()
                    .maxStale(MAX_TALE, TimeUnit.DAYS)
                    .build()
            }
            return response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named(OFFLINE) offlineInterceptor: Interceptor,
        @Named(ONLINE) onlineInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .addInterceptor(offlineInterceptor)
            .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cacheDir = context.cacheDir
        var cache: Cache? = null
        try {
            cache = Cache(
                File(cacheDir, HTTP_CACHE), cacheSize.toLong()
            )
        } catch (e: Exception) {
        }
        return cache!!
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): NetworkService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)

    @JvmStatic
    @Provides
    @Singleton
    fun provideWikipediaRepository(networkService: NetworkService): WikipediaRepository =
        WikipediaRepository(networkService)
}
