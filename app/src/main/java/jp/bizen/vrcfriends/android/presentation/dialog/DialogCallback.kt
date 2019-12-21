package jp.bizen.vrcfriends.android.presentation.dialog

interface DialogCallback {
    fun result(requestCode: Int, resultCode: Int)
}