package com.seeker.lucky.app.mvp

import io.reactivex.internal.disposables.ListCompositeDisposable

/**
 *@author  Seeker
 *@date    2018/11/20/020  9:26
 *@describe mvp中的p层
 */
open class BasePresenter<V:BaseView>{

    var mView:V? = null

    val resources = ListCompositeDisposable()

    fun attachView(v:V){
        this.mView = v
    }

    fun detachView(){
        resources.clear()
        this.mView = null
    }

}