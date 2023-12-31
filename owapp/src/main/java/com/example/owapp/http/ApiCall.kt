package com.example.owapp.http

import android.annotation.SuppressLint
import android.os.PersistableBundle
import com.example.owapp.HamApp
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mm.hamcompose.data.http.interceptor.CacheCookieInterceptor
import com.mm.hamcompose.data.http.interceptor.LogInterceptor
import com.mm.hamcompose.data.http.interceptor.SetCookieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * Created by Superman. 19/5/27
 */

val apiService=ApiCall.apiService
object ApiCall {
    /**
     * 请求超时时间
     */
    private const val DEFAULT_TIMEOUT = 30000
    private lateinit var SERVICE: ApiService

    //手动创建一个OkHttpClient并设置超时时间
    val apiService: ApiService
        get() {
            if (!ApiCall::SERVICE.isInitialized) {
                SERVICE = Retrofit.Builder()
                    .client(okHttp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                    .baseUrl(ApiService.url)
                    .build()
                    .create(ApiService::class.java)
            }
            return SERVICE
        }
    //Cookie持久化
    private val cookieJar:PersistentCookieJar by lazy{
        PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(HamApp.mContext))
    }
    //手动创建一个OkHttpClient并设置超时时间
    val okHttp: OkHttpClient
        get() {
            return OkHttpClient.Builder().run {
               // cookieJar(cookieJar)
                connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                addInterceptor(SetCookieInterceptor())
                addInterceptor(CacheCookieInterceptor())
                addInterceptor(LogInterceptor())
                //不验证证书，可以使用Charles监听数据
                .sslSocketFactory(createSSLSocketFactory(),TrustAllCerts())
                hostnameVerifier(TrustAllNameVerifier())
                build()
            }
        }

    private fun createSSLSocketFactory(): SSLSocketFactory {
        lateinit var ssfFactory: SSLSocketFactory
        try {
            val sslFactory = SSLContext.getInstance("TLS")
            sslFactory.init(null,  arrayOf(TrustAllCerts()), SecureRandom());
            ssfFactory = sslFactory.socketFactory
        } catch (e: Exception) {
            print("SSL错误：${e.message}")
        }
        return ssfFactory
    }

}

class TrustAllNameVerifier: HostnameVerifier {
    @SuppressLint("BadHostnameVerifier")
    override fun verify(hostname: String?, session: SSLSession?): Boolean = true
}

@SuppressLint("CustomX509TrustManager")
class TrustAllCerts : X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}
