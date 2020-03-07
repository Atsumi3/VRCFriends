package jp.bizen.vrcfriends.android

import android.app.Application
import jp.bizen.vrcfriends.android.di.modules.apiModules
import jp.bizen.vrcfriends.android.di.modules.coreModules
import jp.bizen.vrcfriends.android.di.modules.modelModules
import jp.bizen.vrcfriends.android.di.modules.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(coreModules, modelModules, apiModules, viewModelModules)
        }
    }
}