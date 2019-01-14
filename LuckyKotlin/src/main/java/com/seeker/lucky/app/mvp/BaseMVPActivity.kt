package com.seeker.lucky.app.mvp

import android.os.Bundle
import com.seeker.lucky.app.LuckyFragmentActivity

/**
 *@author  Seeker
 *@date    2018/11/20/020  9:37
 *@describe mvpActivity
 */
abstract class BaseMVPActivity<V:BaseView,P:BasePresenter<V>> : LuckyFragmentActivity(){

    lateinit var mPresenter:P

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter.attachView(this as V)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    abstract fun initPresenter():P

}