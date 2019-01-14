@file:JvmMultifileClass
@file:JvmName("ContextKt")

package com.seeker.lucky.extensions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.seeker.lucky.toast.ToastCompat
import java.io.File

/**
 *@author  Seeker
 *@date    2018/11/15/015  14:59
 *@describe Context相关扩展函数
 */

/**
 * Toast提示
 */
@JvmOverloads
fun Context.makeToast(message: CharSequence?, duration: Int = Toast.LENGTH_SHORT) =
    ToastCompat.makeToast(this, message, duration)

/**
 * 应用内单进程广播,效率高
 */
fun Context.localBroadcastManager() = LocalBroadcastManager.getInstance(this)

/**
 * 注册应用内广播
 */
fun Context.registerLocalReceiver(receiver: BroadcastReceiver, vararg actions: String) {
    val intentFilter = IntentFilter()
    for (action in actions) {
        intentFilter.addAction(action)
    }
    this.localBroadcastManager().registerReceiver(receiver,intentFilter)
}

/**
 * 注销广播监听
 */
fun Context.unregisterLocalReceiver(receiver: BroadcastReceiver) =
    this.localBroadcastManager().unregisterReceiver(receiver)

/**
 * 发送广播
 */
fun Context.sendLocalBroadcast(intent: Intent) = this.localBroadcastManager().sendBroadcast(intent)

/**
 * 获取applicationId
 */
fun Context.applicationId(): String = applicationInfo.packageName

private fun Context.packageInfo(): PackageInfo? {
    return try {
        packageManager.getPackageInfo(packageName, 0)
    } catch (ignore: PackageManager.NameNotFoundException) {
        ignore.printStackTrace()
        null
    }
}

/**
 * 获取versionCode
 */
fun Context.versionCode(): Long = packageInfo()?.let {
    PackageInfoCompat.getLongVersionCode(it)
} ?: 0

/**
 * 获取versionName
 */
fun Context.versionName(): String = packageInfo()?.versionName ?: ""

/**
 * 获取应用名称
 */
fun Context.appLabel(defName: String = ""):String = try {
    resources.getString(packageInfo()?.applicationInfo?.labelRes?:0)
}catch (e:Exception){
    defName
}

/**
 * 保存信息到 sharedPreferences
 */
@Throws(IllegalArgumentException::class)
fun <T>Context.putToSharedPreferences(
    key: String,
    value: T,
    name: String = applicationId().toUpperCase(),
    mode: Int = Context.MODE_PRIVATE
) = with(getSharedPreferences(name, mode).edit()) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Float -> putFloat(key, value)
        is Long -> putLong(key, value)
        else -> throw IllegalArgumentException("argument value's type was no found.")
    }
}.apply()

/**
 * 获取信息从 sharedPreferences
 */
@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
@Throws(ClassCastException::class, IllegalArgumentException::class)
fun <T>Context.getFromSharedPreferences(
    key: String,
    defaultValue: T,
    name: String = applicationId().toUpperCase(),
    mode: Int = Context.MODE_PRIVATE
): T = with(getSharedPreferences(name, mode)){
    when (defaultValue) {
        is Boolean -> getBoolean(key, defaultValue)
        is String -> getString(key, defaultValue)
        is Int -> getInt(key, defaultValue)
        is Float -> getFloat(key, defaultValue)
        is Long -> getLong(key, defaultValue)
        else -> throw IllegalArgumentException("argument defaultValue's type was no found.")
    } as T
}

/**
 * 安装应用
 */
fun Context.installApk(apkFile:File){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.perfect(this,"application/vnd.android.package-archive", apkFile, true)
    startActivity(intent)
}

/**
 * 启动一个activity
 */
fun <Activity>Context.startActivity(clazz: Class<Activity>,extras: Bundle? = null,flag:Int = 0){
    val intent = Intent(this,clazz)
    intent.addFlags(flag)
    extras?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}

fun Context.getDrawabler(@DrawableRes id:Int) = ContextCompat.getDrawable(this,id);
