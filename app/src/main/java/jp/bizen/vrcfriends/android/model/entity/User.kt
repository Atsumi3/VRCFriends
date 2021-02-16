package jp.bizen.vrcfriends.android.model.entity

data class User(
    val requiresTwoFactorAuth: List<String> = emptyList(),
    val username: String = ""
)