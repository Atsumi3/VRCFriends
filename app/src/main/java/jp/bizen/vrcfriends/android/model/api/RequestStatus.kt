package jp.bizen.vrcfriends.android.model.api

sealed class RequestStatus<T> {
    class Idle<T> : RequestStatus<T>()
    class Requesting<T> : RequestStatus<T>()
    data class Success<T>(val value: T) : RequestStatus<T>()
    data class Failure<T>(val error: Throwable) : RequestStatus<T>()
}