package jp.bizen.vrcfriends.android.extensions

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.lazyWithArgs(key: String): Lazy<T> {
    return lazy { arguments!!.get(key) as T }
}