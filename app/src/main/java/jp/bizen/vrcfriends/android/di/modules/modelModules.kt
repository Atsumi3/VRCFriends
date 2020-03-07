package jp.bizen.vrcfriends.android.di.modules

import jp.bizen.vrcfriends.android.model.manager.VRChatManager
import org.koin.dsl.module

val modelModules = module {
    single { VRChatManager(get(), get()) }
}