package jp.bizen.vrcfriends.android.model.api

import jp.bizen.vrcfriends.android.model.CredentialStore
import okhttp3.Interceptor
import okhttp3.Response

class ResponseHeaderInterceptor(private val credentialStore: CredentialStore) : Interceptor {
    private val regex = "^auth=(.*);".toRegex()

    // 集中力が切れたのでいつかRefactoringする
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var cookie: String = request.header("cookie") ?: ""
        val newRequestBuilder = request.newBuilder()
        credentialStore.authToken?.let { credential ->
            if (credential.isNotEmpty()) {
                if (cookie.isEmpty()) {
                    cookie += "auth=$credential"
                } else {
                    if (!cookie.contains("auth=")) {
                        cookie += ";auth=$credential"
                    }
                }
                newRequestBuilder.addHeader("Cookie", cookie)
            }
        }

        val response = chain.proceed(newRequestBuilder.build())
        response.headers("Set-Cookie").forEach { header ->
            regex.find(header)?.let {
                credentialStore.authToken = it.groupValues[1]
                return@forEach
            }
        }
        return response
    }
}