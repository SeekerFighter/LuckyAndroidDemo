@file:JvmMultifileClass
@file:JvmName("LuckyAndroid7")

package com.seeker.lucky.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 *@author  Seeker
 *@date    2018/11/19/019  15:07
 *@describe file uri 适配,android 7
 */

/**
 * 获取文件的uri
 */
fun File.toUri(context: Context): Uri {
    //版本在7.0以上是不能直接通过uri访问的
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        toUriForV24(context)
    } else {
        Uri.fromFile(this)
    }
}

/**
 * android7 sdk >= 24适配
 */
fun File.toUriForV24(context: Context): Uri =
    FileProvider.getUriForFile(context, "${context.applicationId()}.android7.fileProvider", this)

/**
 * 完善intent数据填充
 */
@JvmOverloads
fun Intent.perfect(context: Context, type: String? = null, file: File, writeAble: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setDataAndType(file.toUriForV24(context), type)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeAble) {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    } else {
        setDataAndType(Uri.fromFile(file), type)
    }
}

/**
 * 设置临时权限
 */
fun Context.grantPermissions(intent: Intent, uri: Uri, writeAble: Boolean) {
    var flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
    if (writeAble) {
        flag = flag or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    }
    intent.addFlags(flag)
    packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)?.let {
        for (resInfo in it) {
            grantUriPermission(resInfo.activityInfo.packageName, uri, flag)
        }
    }
}