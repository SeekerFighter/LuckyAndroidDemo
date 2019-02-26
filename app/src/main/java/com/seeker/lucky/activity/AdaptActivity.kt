package com.seeker.lucky.activity

import android.view.View
import com.seeker.lucky.R
import com.seeker.lucky.app.adapt.AdaptFragmentActivity
import com.seeker.lucky.app.adapt.AdaptStrategy

/**
 *@author  Seeker
 *@date    2019/2/25/025  16:36
 *@describe TODO
 */
class AdaptActivity : AdaptFragmentActivity(){

    override fun layoutResId(): Int  = R.layout.activity_adapt

    override fun onViewCreated(contentView: View) {
    }

//    override fun isFollowSystemFontScale(): Boolean {
//        return false
//    }

//    override fun isCancelAdapt(): Boolean {
//        return true
//    }

//    override fun initAdaptStrategy(): AdaptStrategy {
//        return object :AdaptStrategy(){
//
//            override fun getScaledDensity(): Float {
//                return super.getScaledDensity() * 2
//            }
//        }
//    }
}