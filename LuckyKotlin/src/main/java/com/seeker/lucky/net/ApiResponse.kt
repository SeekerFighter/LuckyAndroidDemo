package com.seeker.lucky.net

/**
 *@author  Seeker
 *@date    2018/11/17/017  13:34
 *@describe 网络请求返回结果
 */
data class ApiResponse<T>(
    private val code: Int = 0,
    private val data: T? = null,
    private val message: String? = null
)