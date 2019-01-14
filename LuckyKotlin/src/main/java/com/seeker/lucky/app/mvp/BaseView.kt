package com.seeker.lucky.app.mvp

/**
 *@author  Seeker
 *@date    2018/11/20/020  9:10
 *@describe mvp的V层
 */
interface BaseView {

    fun onStarted(funCode:Int = -1){

    }

    fun onCompleted(funCode: Int = -1){

    }

    fun onError(errMsg:String? = null,errThrowable: Throwable? = null){

    }
}