@file:JvmMultifileClass
@file:JvmName("LuckyNetWorker")

package com.seeker.lucky.extensions

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

/**
 *@author  Seeker
 *@date    2018/11/17/017  15:14
 *@describe 网络相关的扩展函数
 */

private fun Context.activeNetworkInfo(): NetworkInfo? = getSystemService(Context.CONNECTIVITY_SERVICE)?.let {
    (it as ConnectivityManager).activeNetworkInfo
}

@TargetApi(Build.VERSION_CODES.M)
private fun Context.networkCapabilities(): NetworkCapabilities? = getSystemService(Context.CONNECTIVITY_SERVICE)?.let {
    val cm = it as ConnectivityManager
    cm.getNetworkCapabilities(cm.activeNetwork)
}

/**
 * 网络是否连接
 */
fun Context.isNetworkConnected(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return networkCapabilities()?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
    }
    return activeNetworkInfo()?.isConnected ?: false
}

/**
 * 判断是否是wifi连接
 */
@Suppress("DEPRECATION")
fun Context.isWifiConnected(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return networkCapabilities()?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }
    return activeNetworkInfo()?.let {
        it.type == ConnectivityManager.TYPE_WIFI
    } ?: false
}