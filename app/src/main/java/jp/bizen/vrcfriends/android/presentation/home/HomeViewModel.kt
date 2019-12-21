package jp.bizen.vrcfriends.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _navigateToStartupScreen = MutableLiveData<Unit>()
    val navigateToStartupScreen: LiveData<Unit> = _navigateToStartupScreen

    private val _presentLogoutConfirmationDialog = MutableLiveData<Unit>()
    val presentLogoutConfirmationDialog: LiveData<Unit> = _presentLogoutConfirmationDialog

    fun onMenuLogoutClick() {
        _presentLogoutConfirmationDialog.postValue(Unit)
    }

    fun onLogoutConfirmationOk() {

    }
}