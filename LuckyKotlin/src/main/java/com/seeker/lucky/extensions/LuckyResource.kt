@file:JvmMultifileClass
@file:JvmName("LuckyResource")

package com.seeker.lucky.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 *@author  Seeker
 *@date    2019/1/14/014  13:02
 *@describe 资源文件获取
 */

/**
 * 根据图片id获取drawable
 */
fun Context.findDrawableById(@DrawableRes id:Int):Drawable? = ContextCompat.getDrawable(this,id)

/**
 * 根据id获取颜色值
 */
fun Context.findColorById(@ColorRes id:Int):Int = ContextCompat.getColor(this,id)

/**
 * 根据id获取字符串
 */
fun Context.findStringById(@StringRes id:Int):String = getString(id)