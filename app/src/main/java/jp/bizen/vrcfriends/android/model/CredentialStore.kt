package jp.bizen.vrcfriends.android.model

import android.content.Context
import androidx.core.content.edit

class CredentialStore(context: Context) {
    private val preferences = context.getSharedPreferences("ss", Context.MODE_PRIVATE)

    var credentials: String
        get() = preferences.getString("credentials_value", null) ?: ""
        set(value) = preferences.edit {
            putString("credentials_value", value)
        }

    // memory cache
    var apiKey: String? = null
    // memory cache
    var authToken: String? = null
    set(value) {
        field = value
        println("updated authToken -> $value")
    }
}