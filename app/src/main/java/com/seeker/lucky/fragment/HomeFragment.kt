package com.seeker.lucky.fragment

import android.view.View
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import com.seeker.lucky.log.LuckyLogger
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 *@author  Seeker
 *@date    2019/1/14/014  10:52
 *@describe 首页
 */
class HomeFragment : LuckyFragment(){

    private val TAG = HomeFragment::class.java.simpleName

    override fun layoutResId(): Int = R.layout.fragment_tab

    override fun onUIVisible() {
        fragment_text.text = "首页"
        LuckyLogger.v(TAG,"onUIVisible() called...")
    }

    override fun onUIInVisible() {
        LuckyLogger.v(TAG,"onUIInVisible() called...")
    }

    override fun onViewCreated(contentView: View) {
        LuckyLogger.v(TAG,"onViewCreated() called...")
    }
}