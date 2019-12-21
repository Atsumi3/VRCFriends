package jp.bizen.vrcfriends.android.model.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    // DI用
    private val rxScheduler: Scheduler by lazy {
        Schedulers.newThread()
    }

    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        OkHttpClient.Builder()
            .addInterceptor(ResponseHeaderInterceptor())
            .addInterceptor(logging)
            .build()
    }

    val vrChatApiService: VRChatApiService by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://api.vrchat.cloud/api/1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(rxScheduler))
            .build()
            .create(VRChatApiService::class.java)
    }
}