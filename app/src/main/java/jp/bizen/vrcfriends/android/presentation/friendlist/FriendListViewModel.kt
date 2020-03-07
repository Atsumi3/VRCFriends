package jp.bizen.vrcfriends.android.presentation.friendlist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.bizen.vrcfriends.android.model.api.RequestStatus
import jp.bizen.vrcfriends.android.model.entity.Friend
import jp.bizen.vrcfriends.android.model.error.MissingAuthToken
import jp.bizen.vrcfriends.android.model.manager.VRChatManager

class FriendListViewModel(private val vrChatManager: VRChatManager) : ViewModel() {
    private val _navigateToStartupScreen = MutableLiveData<Unit>()
    val navigateToStartupScreen: LiveData<Unit> = _navigateToStartupScreen

    private val _presentErrorMessage = MutableLiveData<String>()
    val presentErrorMessage: LiveData<String> = _presentErrorMessage

    private val _progressViewVisibility = MutableLiveData<Int>()
    val progressViewVisibility: LiveData<Int> = _progressViewVisibility

    private val _data = MutableLiveData<List<Friend>>()
    val data: LiveData<List<Friend>> = _data

    private var requestStatus: RequestStatus<List<Friend>> = RequestStatus.Idle()
        set(value) {
            field = value
            when (value) {
                is RequestStatus.Idle -> {
                    _progressViewVisibility.postValue(View.GONE)
                }

                is RequestStatus.Requesting -> {
                    _progressViewVisibility.postValue(View.VISIBLE)
                }
                is RequestStatus.Success -> {
                    _progressViewVisibility.postValue(View.GONE)
                    _data.postValue(value.value.sortedBy { it.displayName })
                }

                is RequestStatus.Failure -> {
                    _progressViewVisibility.postValue(View.GONE)
                    when (value.error) {
                        is MissingAuthToken -> {
                            _presentErrorMessage.postValue("認証が切れました")
                            _navigateToStartupScreen.postValue(Unit)
                        }
                        else -> {
                            _presentErrorMessage.postValue(value.error.localizedMessage)
                        }
                    }
                }
            }
        }

    init {
        requestStatus = RequestStatus.Idle()
    }

    fun onResume() {
        fetchFriends()
    }

    fun onRefreshClick(@Suppress("UNUSED_PARAMETER") view: View?) {
        fetchFriends()
    }

    private fun fetchFriends() {
        if (requestStatus is RequestStatus.Requesting) {
            _presentErrorMessage.postValue("更新中です")
            return
        }
        requestStatus = RequestStatus.Requesting()
        vrChatManager.fetchFriends(object : VRChatManager.FriendCallback {
            override fun success(friends: List<Friend>) {
                requestStatus = RequestStatus.Success(friends)
            }

            override fun failure(error: Throwable) {
                requestStatus = RequestStatus.Failure(error)
            }
        })
    }
}