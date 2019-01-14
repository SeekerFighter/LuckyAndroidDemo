package com.seeker.lucky.app.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seeker.lucky.app.LuckyFragment

/**
 *@author  Seeker
 *@date    2018/11/20/020  9:37
 *@describe MVPFragment
 */
abstract class BaseMVPFragment<V:BaseView,P:BasePresenter<V>> : LuckyFragment(){

    lateinit var mPresenter:P

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = initPresenter()
        mPresenter.attachView(this as V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    abstract fun initPresenter():P

}