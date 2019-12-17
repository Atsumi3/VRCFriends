package jp.bizen.vrcfriends.android

import android.app.Application
import jp.bizen.vrcfriends.android.model.manager.SessionManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.setup(this)
    }
}