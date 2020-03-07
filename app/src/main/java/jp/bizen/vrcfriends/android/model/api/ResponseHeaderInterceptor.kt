package jp.bizen.vrcfriends.android.model.api

import jp.bizen.vrcfriends.android.model.CredentialStore
import okhttp3.Interceptor
import okhttp3.Response

class ResponseHeaderInterceptor(private val credentialStore: CredentialStore) : Interceptor {
    // 集中力が切れたのでいつかRefactoringする
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isNotEmpty()) {
            for (header in response.headers("Set-Cookie")) {
                println("HEADER -> $header")
                if (header.contains("auth=")) {
                    val values = header.split(";")
                    values.forEach {
                        val kv = it.split("=")
                        if (kv[0] == "auth") {
                            credentialStore.authToken = kv[1]
                        }
                    }
                }
            }
        }
        return response
    }
}