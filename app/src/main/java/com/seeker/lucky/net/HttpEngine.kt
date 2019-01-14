package com.seeker.lucky.net

import okhttp3.logging.HttpLoggingInterceptor

/**
 *@author  Seeker
 *@date    2018/11/19/019  12:54
 *@describe 网络请求
 */
class HttpEngine : RetrofitEngine<ApiService>() {

    companion object {
        val instance: HttpEngine by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { HttpEngine() }
    }

    override fun baseUrl(): String  = ""

    override fun apiService(): Class<ApiService> = ApiService::class.java

    override fun interceptors(): Array<okhttp3.Interceptor>? {
        return arrayOf(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
}