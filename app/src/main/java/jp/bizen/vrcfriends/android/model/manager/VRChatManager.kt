package jp.bizen.vrcfriends.android.model.manager

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.core.content.edit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import jp.bizen.vrcfriends.android.model.api.ApiClient
import jp.bizen.vrcfriends.android.model.entity.Friend
import jp.bizen.vrcfriends.android.model.error.MissingAuthToken


class VRChatManager private constructor(private val context: Context) {
    private val compositeDisposable = CompositeDisposable()
    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    // memory cache
    private var apiKey: String? = null
    private var authToken: String? = null
    // file cache
    private var credentials: String?
        get() = preference.getString(PREFERENCE_KEY_CREDENTIALS_VALUE, null)
        set(value) {
            preference.edit {
                putString(PREFERENCE_KEY_CREDENTIALS_VALUE, value)
            }
        }

    fun updateAuthToken(value: String?) {
        authToken = value
    }

    fun login(userId: String, password: String, callback: LoginCallback) {
        credentials = okhttp3.Credentials.basic(userId, password)
        login(callback)
    }

    fun login(callback: LoginCallback) {
        if (credentials.isNullOrEmpty()) {
            callback.unavailable()
            return
        }
        val subscriber = if (apiKey.isNullOrEmpty()) {
            fetchApiKey()
                .flatMap {
                    apiKey = it.apiKey
                    fetchMe(it.apiKey, credentials!!)
                }
        } else {
            fetchMe(apiKey!!, credentials!!)
        }
        val disposable = subscriber.subscribe({
            Handler(Looper.getMainLooper()).postDelayed({
                callback.available(it.username)
            }, 3000)
        }, {
            callback.unavailable()
        })
        compositeDisposable.add(disposable)
    }

    fun fetchFriends(callback: FriendCallback) {
        if (apiKey.isNullOrEmpty() || authToken.isNullOrEmpty()) {
            callback.failure(MissingAuthToken())
            return
        }
        val disposable = fetchFriends(apiKey!!, authToken!!).subscribe({
            callback.success(it)
        }, {
            callback.failure(it)
        })
        compositeDisposable.add(disposable)
    }

    private fun fetchApiKey() = ApiClient.vrChatApiService
        .fetchConfig()
        .observeOn(AndroidSchedulers.mainThread())

    private fun fetchMe(apiKey: String, credentials: String) = ApiClient.vrChatApiService
        .fetchUser(apiKey, credentials)
        .observeOn(AndroidSchedulers.mainThread())

    private fun fetchFriends(apiKey: String, authToken: String) = ApiClient.vrChatApiService
        .fetchFriends(apiKey, authToken)
        .observeOn(AndroidSchedulers.mainThread())

    companion object {
        private const val PREFERENCE_NAME = "session"
        private const val PREFERENCE_KEY_CREDENTIALS_VALUE = "credentials_value"

        lateinit var instance: VRChatManager

        fun setup(context: Context) {
            instance = VRChatManager(context)
        }
    }

    interface LoginCallback {
        fun available(loggedInUserName: String)

        fun unavailable()
    }

    interface FriendCallback {
        fun success(friends: List<Friend>)

        fun failure(error: Throwable)
    }
}