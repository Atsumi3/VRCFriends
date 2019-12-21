package jp.bizen.vrcfriends.android.model.api

import jp.bizen.vrcfriends.android.model.manager.VRChatManager
import okhttp3.Interceptor
import okhttp3.Response

class ResponseHeaderInterceptor : Interceptor {
    // 集中力が切れたのでいつかRefactoringする
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isNotEmpty()) {
            for (header in response.headers("Set-Cookie")) {
                if (header.contains("auth=")) {
                    val values = header.split(";")
                    values.forEach {
                        val kv = it.split("=")
                        if (kv[0] == "auth") {
                            VRChatManager.instance.updateAuthToken(kv[1])
                        }
                    }
                }
            }
        }
        return response
    }
}