package com.seeker.lucky.net

import com.seeker.lucky.app.mvp.BaseMVPObserver
import com.seeker.lucky.app.mvp.BaseView
import io.reactivex.internal.disposables.ListCompositeDisposable

/**
 *@author  Seeker
 *@date    2018/11/17/017  12:36
 *@describe retrofit 网络请求结果
 */
abstract class HttpRequestCallback<T>(
    private val mView: BaseView? = null,
    private val requestCode: Int = -1,
    resources: ListCompositeDisposable? = null
) : BaseMVPObserver<T>(mView,requestCode,resources) {

    final override fun onNext(t: T) {
        onSuccess(t, requestCode)
    }

    final override fun onError(e: Throwable) {
        if (!handerError(requestCode)) {
            mView?.onError()
        }
    }

    open fun handerError(requestCode: Int): Boolean = false

    abstract fun onSuccess(result: T, requestCode: Int)

}