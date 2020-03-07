package jp.bizen.vrcfriends.android.di.modules

import jp.bizen.vrcfriends.android.model.CredentialStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModules = module {
    single { CredentialStore(androidContext()) }
}