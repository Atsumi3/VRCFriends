package jp.bizen.vrcfriends.android.model.api

import io.reactivex.Single
import jp.bizen.vrcfriends.android.model.entity.Config
import jp.bizen.vrcfriends.android.model.entity.Friend
import jp.bizen.vrcfriends.android.model.entity.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface VRChatApiService {
    @GET("config")
    fun fetchConfig(): Single<Config>

    @GET("auth/user")
    fun fetchUser(@Query("apiKey") apiKey: String, @Header("Authorization") auth: String): Single<User>

    @GET("auth/user/friends")
    fun fetchFriends(@Query("apiKey") apiKey: String, @Query("authToken") authToken: String): Single<List<Friend>>
}