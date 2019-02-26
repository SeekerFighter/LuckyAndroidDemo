@file:JvmMultifileClass
@file:JvmName("LuckyMeteData")

package com.seeker.lucky.extensions

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/**
 *@author  Seeker
 *@date    2018/11/20/020  16:29
 *@describe 获取manifest中定义的meteData数据
 */

/**
 * 获取定义在activity中的meteData数据
 */
fun Activity.getActivityMeteData()
        = packageManager.getActivityInfo(componentName,PackageManager.GET_META_DATA)?.metaData

/**
 * 获取定义在application应用中的meteData数据
 */
fun Context.getApplicationMeteData()
        = packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA)?.metaData

/**
 * 获取定义在service中的meteData数据
 */
fun Service.getServiceMeteData()
        = packageManager.getServiceInfo(ComponentName(this,javaClass),PackageManager.GET_META_DATA)?.metaData

/**
 * 获取定义在BroadcastReceiver中的meteData数据
 */
fun BroadcastReceiver.getReceiverMeteData(context: Context)
        = context.packageManager.getReceiverInfo(ComponentName(context,javaClass),PackageManager.GET_META_DATA)?.metaData
