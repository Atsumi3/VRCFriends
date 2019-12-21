package jp.bizen.vrcfriends.android

import android.app.Application
import jp.bizen.vrcfriends.android.model.manager.VRChatManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VRChatManager.setup(this)
    }
}