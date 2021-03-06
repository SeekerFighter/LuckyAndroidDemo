package com.seeker.lucky.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import com.seeker.lucky.extensions.findDrawableById
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 *@author  Seeker
 *@date    2019/1/14/014  10:52
 *@describe 首页
 */
class SelfFragment : LuckyFragment(){

    override fun layoutResId(): Int = R.layout.fragment_tab

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onUIVisible() {
        fragment_text.text = "我的"
        fragment_text.background = mHostActivity?.findDrawableById(android.R.color.holo_red_dark)
    }

    override fun onUIInVisible() {

    }

    override fun onViewCreated(contentView: View) {
        
    }
}