package jp.bizen.vrcfriends.android.presentation.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.bizen.vrcfriends.android.model.manager.VRChatManager

class LoginViewModel(private val vrChatManager: VRChatManager) : ViewModel() {
    private val _navigateToHomeScreen = MutableLiveData<Unit>()
    val navigateToHomeScreen: LiveData<Unit> = _navigateToHomeScreen

    private val _presentErrorMessage = MutableLiveData<String>()
    val presentErrorMessage: LiveData<String> = _presentErrorMessage

    private val _presentWelcomeMessage = MutableLiveData<String>()
    val presentWelcomeMessage: LiveData<String> = _presentWelcomeMessage

    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val twoFactorAuthValue = MutableLiveData<String>()

    private val inputIdValue: String
        get() = id.value ?: ""

    private val inputPasswordValue: String
        get() = password.value ?: ""

    private val inputTwoFactorAuthValue: String
        get() = twoFactorAuthValue.value ?: ""

    fun onLoginClick(@Suppress("UNUSED_PARAMETER") view: View?) {
        if (inputIdValue.length < 3 && inputPasswordValue.length < 5) {
            _presentErrorMessage.postValue("正しいIDとパスワードを入れてください")
            return
        }

        vrChatManager.login(
            inputIdValue,
            inputPasswordValue,
            inputTwoFactorAuthValue,
            object : VRChatManager.LoginCallback {
                override fun available(loggedInUserName: String) {
                    _presentWelcomeMessage.postValue(loggedInUserName)
                    _navigateToHomeScreen.postValue(Unit)
                }

                override fun unavailable(error: Throwable) {
                    error.printStackTrace()
                    _presentErrorMessage.postValue("ログインに失敗しました ${error.localizedMessage}")
                }
            })
    }
}