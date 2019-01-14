@file:JvmMultifileClass
@file:JvmName("OtherKt")

package com.seeker.lucky.extensions

import android.os.Message
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 *@author  Seeker
 *@date    2018/11/15/015  14:59
 *@describe 各种扩展函数
 */

/**
 * 检查message中what的有效性
 */
fun Message.checkForValidMessageWhat() {
    if (what and -0x10000 != 0) {
        throw IllegalArgumentException("Can only use lower 16 bits for messageWhat")
    }
}

/**
 * 把一个javebean对象转成json字符串
 */
fun <T> T.toJsonStr(): String = GsonBuilder().setPrettyPrinting().create().toJson(this)

/**
 * 把一个json字符串转成javebean对象
 */
fun <T> String.toJavaBean(clazz: Class<T>): T? = try {
    Gson().fromJson(this, clazz)
} catch (e: Exception) {
    e.printStackTrace()
    null
}