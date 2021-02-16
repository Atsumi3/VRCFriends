package jp.bizen.vrcfriends.android.model.manager

import jp.bizen.vrcfriends.android.model.CredentialStore
import jp.bizen.vrcfriends.android.model.api.VRChatApiService
import jp.bizen.vrcfriends.android.model.entity.Friend
import jp.bizen.vrcfriends.android.model.error.InvalidTwoFactorStatusError
import jp.bizen.vrcfriends.android.model.error.MissingAuthToken
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class VRChatManager(
    private val vrChatApiService: VRChatApiService,
    private val credentialStore: CredentialStore): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun login(userId: String, password: String, code: String, callback: LoginCallback) {
        credentialStore.credentials = okhttp3.Credentials.basic(userId, password)
        login(code, callback)
    }

    fun login(code: String = "", callback: LoginCallback) {
        val credentials = credentialStore.credentials
        if (credentials.isEmpty()) {
            callback.unavailable(RuntimeException("credentials is null."))
            return
        }
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            callback.unavailable(exception)
        }
        launch(errorHandler) {
            var apiKey = credentialStore.apiKey
            if (apiKey.isNullOrEmpty()) {
                apiKey = fetchConfig().apiKey
                if (apiKey.isNullOrEmpty()) {
                    callback.unavailable(RuntimeException("failed to fetch apiKey."))
                }
                credentialStore.apiKey = apiKey
            }
            var user = fetchMe(apiKey, credentials)
            if (user.requiresTwoFactorAuth.isNotEmpty()) {
                if (code.isNotEmpty() && verify(code, credentials).verified) {
                    user = fetchMe(apiKey, credentials)
                } else {
                    throw InvalidTwoFactorStatusError()
                }
            }
            callback.available(user.username)
        }
    }

    fun fetchFriends(callback: FriendCallback) {
        val apiKey = credentialStore.apiKey
        val authToken = credentialStore.authToken
        if (apiKey.isNullOrEmpty() || authToken.isNullOrEmpty()) {
            callback.failure(MissingAuthToken())
            return
        }
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            callback.failure(exception)
        }
        launch(errorHandler) {
            val friends = fetchFriends(apiKey, authToken)
            callback.success(friends)
        }
    }

    private suspend fun fetchConfig() = withContext(Dispatchers.Default) {
        vrChatApiService.fetchConfig()
    }

    private suspend fun fetchMe(apiKey: String, credentials: String) = withContext(Dispatchers.Default) {
        vrChatApiService.fetchUser(apiKey, credentials)
    }

    private suspend fun fetchFriends(apiKey: String, authToken: String) = withContext(Dispatchers.Default) {
        vrChatApiService.fetchFriends(apiKey, authToken)
    }

    private suspend fun verify(code: String, credentials: String) = withContext(Dispatchers.Default) {
        vrChatApiService.verify(code, credentials)
    }

    interface LoginCallback {
        fun available(loggedInUserName: String)

        fun unavailable(error: Throwable)
    }

    interface FriendCallback {
        fun success(friends: List<Friend>)

        fun failure(error: Throwable)
    }
}