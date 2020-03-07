package jp.bizen.vrcfriends.android.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.bizen.vrcfriends.android.model.api.ResponseHeaderInterceptor
import jp.bizen.vrcfriends.android.model.api.VRChatApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModules = module {

    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.NONE
        OkHttpClient.Builder()
            .addInterceptor(ResponseHeaderInterceptor(get()))
            .addInterceptor(logging)
            .build()
    }

    single {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://api.vrchat.cloud/api/1/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VRChatApiService::class.java)
    }
}