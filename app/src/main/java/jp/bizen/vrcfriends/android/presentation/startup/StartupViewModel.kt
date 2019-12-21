package jp.bizen.vrcfriends.android.presentation.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.bizen.vrcfriends.android.model.manager.VRChatManager

class StartupViewModel : ViewModel() {
    private val _navigateToLoginScreen = MutableLiveData<Unit>()
    val navigateToLoginScreen: LiveData<Unit> = _navigateToLoginScreen

    private val _navigateToHomeScreen = MutableLiveData<Unit>()
    val navigateToHomeScreen: LiveData<Unit> = _navigateToHomeScreen

    private val _presentWelcomeMessage = MutableLiveData<String>()
    val presentWelcomeMessage: LiveData<String> = _presentWelcomeMessage

    fun onResume() {
        VRChatManager.instance.login(object : VRChatManager.LoginCallback {
            override fun available(loggedInUserName: String) {
                _presentWelcomeMessage.postValue(loggedInUserName)
                _navigateToHomeScreen.postValue(Unit)
            }

            override fun unavailable() {
                _navigateToLoginScreen.postValue(Unit)
            }
        })
    }
}