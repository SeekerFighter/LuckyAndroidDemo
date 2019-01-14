package com.seeker.lucky.net

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@author  Seeker
 *@date    2018/11/19/019  11:23
 *@describe 网络请求相关
 */
abstract class RetrofitEngine<T>{

    val apiService:T

    init {
        val httpBuilder = OkHttpClient.Builder()
            .connectTimeout(10_000,TimeUnit.MILLISECONDS)
            .readTimeout(10_000,TimeUnit.MILLISECONDS)
            .writeTimeout(10_000,TimeUnit.MILLISECONDS)

        this.interceptors()?.let {
            for (interceptor in  it){
                httpBuilder.addInterceptor(interceptor)
            }
        }

        val retrofit:Retrofit = Retrofit.Builder()
            .client(httpBuilder.build())
            .baseUrl(this.baseUrl())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(this.apiService())
    }

    open fun interceptors():Array<Interceptor>? = null

    abstract fun baseUrl():String

    abstract fun apiService(): Class<T>

}