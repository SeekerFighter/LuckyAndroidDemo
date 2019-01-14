package com.seeker.lucky.app.mvp

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.disposables.ListCompositeDisposable
import io.reactivex.internal.util.EndConsumerHelper
import java.util.concurrent.atomic.AtomicReference

/**
 *@author  Seeker
 *@date    2018/11/20/020  9:59
 *@describe mvp 模式下的监听
 */
abstract class BaseMVPObserver<T>(
    private val mView: BaseView? = null,
    private val funCode:Int = -1,
    private val resources: ListCompositeDisposable? = ListCompositeDisposable()
) : Observer<T>, Disposable {

    private val upstream = AtomicReference<Disposable>()

    override fun isDisposed(): Boolean = DisposableHelper.isDisposed(upstream.get())

    override fun dispose() {
        if (DisposableHelper.dispose(upstream)) {
            resources?.delete(upstream.get())
        }
    }

    override fun onSubscribe(d: Disposable) {
        if (EndConsumerHelper.setOnce(upstream, d, javaClass)) {
            resources?.add(d)
            mView?.onStarted()
        }
    }

    override fun onComplete() {
        mView?.onCompleted(funCode)
    }

}