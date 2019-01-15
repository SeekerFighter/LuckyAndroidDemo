@file:JvmMultifileClass
@file:JvmName("LuckyPermission")

package com.seeker.lucky.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.seeker.lucky.app.LuckyFragmentActivity
import com.seeker.lucky.app.LuckyFragment

/**
 *@author  Seeker
 *@date    2018/12/17/017  16:33
 *@describe 权限申请扩展函数
 */

val needRequestPermission:()->Boolean = {Build.VERSION.SDK_INT >= Build.VERSION_CODES.M}

val checkPermissionGranted :(Context,String)->Boolean = {context, permission ->
    ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED}

fun Context.checkPermissionsGranted (permissions: Array<String>):Boolean{
    if (!needRequestPermission()){
        return true
    }
    var isGranted = true
    for (permission in permissions){
        if (!checkPermissionGranted(this,permission)){
            isGranted = false
            break
        }
    }
    return isGranted
}

inline fun LuckyFragmentActivity.invokeMethodWithPermission(permissions: Array<String>,
                                                            requestCode: Int = 0,
                                                            method: () -> Unit){
    if (checkPermissionsGranted(permissions)) method()
    else ActivityCompat.requestPermissions(this, permissions, requestCode)
}

inline fun LuckyFragment.invokeMethodWithPermission(permissions: Array<String>,
                                                    requestCode: Int = 0,
                                                    method: () -> Unit){
    if (activity!!.checkPermissionsGranted(permissions)) method()
    else requestPermissions(permissions, requestCode)
}

inline fun handlerRequestPermissionResult(permissions: Array<String>,
                                          grantResults: IntArray,
                                          granted:()->Unit,
                                          noGranted:()->Unit){

}