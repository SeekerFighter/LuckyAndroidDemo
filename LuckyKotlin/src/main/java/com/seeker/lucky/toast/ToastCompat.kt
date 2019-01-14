package com.seeker.lucky.toast

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Message
import android.widget.Toast

/**
 *@author  Seeker
 *@date    2018/12/12/012  16:40
 *@describe 发送安全的Toast提示
 */
object ToastCompat {

    @JvmOverloads//添加此注释方法参数会有默认值，否则三个参数在java中都要传
    fun makeToast(context: Context,msg:CharSequence?,duration:Int = Toast.LENGTH_SHORT){
        val toast = Toast.makeText(context.applicationContext, msg, duration)
        hookHandler(toast)
        toast.show()
    }

    private fun hookHandler(toast: Toast){
        if (Build.VERSION.SDK_INT >= 26)
            return
        try {
            val mTNFiled = Toast::class.java.getDeclaredField("mTN")
            mTNFiled.isAccessible = true
            val tnHandlerFiled = mTNFiled.type.getDeclaredField("mHandler");
            tnHandlerFiled.isAccessible = true
            val tn = mTNFiled.get(toast)
            val handler = tnHandlerFiled.get(tn) as Handler
            tnHandlerFiled.set(tn,SafelyHandlerWrapper(handler))
        }catch (ignore:Exception){}
    }

    private class SafelyHandlerWrapper(val impl: Handler) : Handler() {

        override fun dispatchMessage(msg: Message) {
            try {
                impl.dispatchMessage(msg)
            } catch (ignore: Exception) {
            }
        }

        override fun handleMessage(msg: Message) {
            impl.handleMessage(msg)//需要委托给原Handler执行
        }
    }
}