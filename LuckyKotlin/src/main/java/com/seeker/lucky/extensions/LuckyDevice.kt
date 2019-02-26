@file:JvmMultifileClass
@file:JvmName("LuckyDevice")

package com.seeker.lucky.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager


/**
 *@author  Seeker
 *@date    2018/11/20/020  16:29
 *@describe 获取和设备有关的信息
 */

/**
 * 获取当前的屏幕尺寸
 */

fun Context.getDisplaySize(): IntArray {
    val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val dm = DisplayMetrics()
    display.getMetrics(dm)
    return intArrayOf(dm.widthPixels, dm.heightPixels)
}

/**
 * 获取状态栏高度
 */
fun getStatusBarHeight(): Int {
    var result = 0
    try {
        val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
    } catch (e: Resources.NotFoundException) {
        e.printStackTrace()
    }
    return result
}

/**
 * 获取设备原生大小
 */
fun Context.getDeviceRawSize(): IntArray {
    val display = (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val dm = DisplayMetrics()
    display.getMetrics(dm)
    var widthPixels = dm.widthPixels
    var heightPixels = dm.heightPixels
    if (Build.VERSION.SDK_INT in 14..16) {
        try {
            widthPixels = Display::class.java.getMethod("getRawWidth").invoke(dm) as Int
            heightPixels = Display::class.java.getMethod("getRawHeight").invoke(dm) as Int
        } catch (ignored: Exception) {
        }
    }else if (Build.VERSION.SDK_INT >= 17)
        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java).invoke(dm, realSize)
            widthPixels = realSize.x
            heightPixels = realSize.y
        } catch (ignored: Exception) {}
    return intArrayOf(widthPixels, heightPixels)
}