package com.seeker.lucky.rxjava

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *@author  Seeker
 *@date    2018/11/17/017  11:30
 *@describe Observable 扩展函数
 */

/**
 * 线程调度
 */
fun <T> Observable<T>.applyScheduler(subscribeScheduler: Scheduler = Schedulers.io()): Observable<T> = also {
    it.subscribeOn(subscribeScheduler).observeOn(AndroidSchedulers.mainThread())
}
