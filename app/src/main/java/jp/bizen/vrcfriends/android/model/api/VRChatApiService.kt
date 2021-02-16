package jp.bizen.vrcfriends.android.model.api

import jp.bizen.vrcfriends.android.model.entity.Config
import jp.bizen.vrcfriends.android.model.entity.Friend
import jp.bizen.vrcfriends.android.model.entity.User
import jp.bizen.vrcfriends.android.model.entity.VerifyResult
import retrofit2.http.*

interface VRChatApiService {
    @GET("config")
    suspend fun fetchConfig(): Config

    @GET("auth/user")
    suspend fun fetchUser(
        @Query("apiKey") apiKey: String,
        @Header("Authorization") authToken: String
    ): User

    @GET("auth/user/friends")
    suspend fun fetchFriends(
        @Query("apiKey") apiKey: String,
        @Query("authToken") authToken: String
    ): List<Friend>

    @FormUrlEncoded
    @POST("auth/twofactorauth/totp/verify")
    suspend fun verify(
        @Field("code") code: String,
        @Header("Authorization") authToken: String
    ): VerifyResult
}