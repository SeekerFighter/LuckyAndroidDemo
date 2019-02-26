package com.seeker.lucky.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import com.seeker.lucky.extensions.findDrawableById
import com.seeker.lucky.log.LuckyLogger
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 *@author  Seeker
 *@date    2019/1/14/014  10:52
 *@describe 首页
 */
class DataFragment : LuckyFragment(){

    private val TAG = DataFragment::class.java.simpleName

    override fun layoutResId(): Int = R.layout.fragment_tab

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onUIVisible() {
        fragment_text.text = "数据"
        fragment_text.background = mHostActivity?.findDrawableById(android.R.color.holo_blue_bright)
        LuckyLogger.v(TAG,"onUIVisible() called...")
    }

    override fun onUIInVisible() {
        LuckyLogger.v(TAG,"onUIInVisible() called...")
    }

    override fun onViewCreated(contentView: View) {
        LuckyLogger.v(TAG,"onViewCreated() called...")
    }
}