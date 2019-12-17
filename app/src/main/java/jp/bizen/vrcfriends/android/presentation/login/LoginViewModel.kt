package jp.bizen.vrcfriends.android.presentation.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginButtonEnabled = MutableLiveData<String>()
    val loginButtonEnabled = _loginButtonEnabled

    fun onLoginClick(@Suppress("UNUSED_PARAMETER") view: View?) {

    }
}