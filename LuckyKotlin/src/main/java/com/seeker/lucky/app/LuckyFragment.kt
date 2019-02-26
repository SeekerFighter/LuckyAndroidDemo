package com.seeker.lucky.app

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *@author  Seeker
 *@date    2018/11/12/012  15:57
 *@describe 基于 kotlin的 LuckyFragment,懒加载以及界面可见不可见回调
 */
abstract class LuckyFragment : Fragment(), LuckyUIHelper {

    var mHostActivity: LuckyFragmentActivity? = null

    var mWho: Long = 0

    companion object {
        const val INVISIBLE_STATE_NONE = -1
        const val VISIBLE_STATE_RESUMED = 0
        const val VISIBLE_STATE_HIDE = 1
        const val VISIBLE_STATE_REPLACE = 2
        const val INVISIBLE_STATE_PAUSED = 3
        const val INVISIBLE_STATE_HIDE = 4
        const val INVISIBLE_STATE_REPLACE = 5
    }

    private var currentState: Int = INVISIBLE_STATE_NONE

    private val recordState = { state:Int -> currentState = state}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWho = System.nanoTime()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutResId(), container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mHostActivity = context as LuckyFragmentActivity
    }

    override fun onDetach() {
        super.onDetach()
        mHostActivity = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(view)
    }

    /**
     * replace方法会走当前函数
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) visible(VISIBLE_STATE_REPLACE) else inVisible(INVISIBLE_STATE_REPLACE)
    }

    /**
     * 针对hide/show处理方法是使用onHiddenChanged
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) inVisible(INVISIBLE_STATE_HIDE) else visible(VISIBLE_STATE_HIDE)
    }

    override fun onResume() {
        super.onResume()
        when (currentState) {
            INVISIBLE_STATE_NONE, INVISIBLE_STATE_PAUSED -> visible(VISIBLE_STATE_RESUMED)
        }
    }

    override fun onPause() {
        super.onPause()
        when (currentState) {
            VISIBLE_STATE_RESUMED, VISIBLE_STATE_HIDE, VISIBLE_STATE_REPLACE -> inVisible(INVISIBLE_STATE_PAUSED)
        }
    }

    private fun visible(state: Int) {
        recordState(state)
        onUIVisible()
    }

    private fun inVisible(state: Int) {
        recordState(state)
        onUIInVisible()
    }

    /**
     * 界面可见
     */
    abstract fun onUIVisible()

    /**
     * 界面不可见
     */
    abstract fun onUIInVisible()

    /**
     * 处理handler回调信息
     */
    open fun handleMessage(message: Message?) :Boolean = true

    /**
     * 如果当前fragment显示，当再次点击显示意图时，走如下代码
     */
    open fun echoShowTodoWork() {
        //todo 2018//11/16
    }
}