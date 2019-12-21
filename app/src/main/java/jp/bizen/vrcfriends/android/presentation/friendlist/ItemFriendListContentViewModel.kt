package jp.bizen.vrcfriends.android.presentation.friendlist

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import jp.bizen.vrcfriends.android.model.entity.Friend

class ItemFriendListContentViewModel(friend: Friend) : BaseObservable() {
    var data: Friend = friend
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val userName: String
        get() = data.displayName
}