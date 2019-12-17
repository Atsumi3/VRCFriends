package jp.bizen.vrcfriends.android.model.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager private constructor(private val context: Context) {
    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var session: String?
        get() = preference.getString(PREFERENCE_KEY_SESSION_VALUE, null)
        set(value) {
            preference.edit {
                putString(PREFERENCE_KEY_SESSION_VALUE, value)
            }
        }

    companion object {
        private const val PREFERENCE_NAME = "session"
        private const val PREFERENCE_KEY_SESSION_VALUE = "session_value"

        lateinit var instance: SessionManager

        fun setup(context: Context) {
            instance = SessionManager(context)
        }
    }
}