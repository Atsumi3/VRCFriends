package jp.bizen.vrcfriends.android.presentation.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.bizen.vrcfriends.android.model.error.InvalidTwoFactorStatusError
import jp.bizen.vrcfriends.android.model.manager.VRChatManager

class StartupViewModel(private val vrChatManager: VRChatManager) : ViewModel() {
    private val _navigateToLoginScreen = MutableLiveData<Unit>()
    val navigateToLoginScreen: LiveData<Unit> = _navigateToLoginScreen

    private val _navigateToHomeScreen = MutableLiveData<Unit>()
    val navigateToHomeScreen: LiveData<Unit> = _navigateToHomeScreen

    private val _presentWelcomeMessage = MutableLiveData<String>()
    val presentWelcomeMessage: LiveData<String> = _presentWelcomeMessage

    private val _presentErrorAlert = MutableLiveData<String>()
    val presentErrorAlert: LiveData<String> = _presentErrorAlert

    private val _presentInputTwoFactorTokenScreen = MutableLiveData<Unit>()
    val presentInputTwoFactorTokenScreen: LiveData<Unit> = _presentInputTwoFactorTokenScreen

    fun onResume() {
        login()
    }

    fun onInputTwoFactorTokenDialogOk(code: String) {
        login(code)
    }

    private fun login(code: String = "") {
        vrChatManager.login(code, object : VRChatManager.LoginCallback {
            override fun available(loggedInUserName: String) {
                _presentWelcomeMessage.postValue(loggedInUserName)
                _navigateToHomeScreen.postValue(Unit)
            }

            override fun unavailable(error: Throwable) {
                if (error is InvalidTwoFactorStatusError) {
                    _presentInputTwoFactorTokenScreen.postValue(Unit)
                } else {
                    _navigateToLoginScreen.postValue(Unit)
                }
            }
        })
    }

    fun onInputTwoFactorTokenDialogBackToLogin() {
        _navigateToLoginScreen.postValue(Unit)
    }
}