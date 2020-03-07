package jp.bizen.vrcfriends.android.di.modules

import jp.bizen.vrcfriends.android.presentation.friendlist.FriendListViewModel
import jp.bizen.vrcfriends.android.presentation.home.HomeViewModel
import jp.bizen.vrcfriends.android.presentation.login.LoginViewModel
import jp.bizen.vrcfriends.android.presentation.startup.StartupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { StartupViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { FriendListViewModel(get()) }
}