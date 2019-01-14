package com.seeker.lucky.fragment

import com.seeker.lucky.R
import com.seeker.lucky.app.LuckyFragment
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 *@author  Seeker
 *@date    2019/1/14/014  10:52
 *@describe 首页
 */
class DataFragment : LuckyFragment(){

    override fun layoutResId(): Int = R.layout.fragment_tab

    override fun onUIVisible() {
        fragment_text.text = "数据"
    }

    override fun onUIInVisible() {

    }

    override fun todoWork() {
        
    }
}