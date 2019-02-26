package com.seeker.lucky.app.adapt

import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.CallSuper
import com.seeker.lucky.app.LuckyFragmentActivity

/**
 *@author  Seeker
 *@date    2019/2/25/025  13:58
 *@describe 自适应大小的activity，在activity节点或者application节点下，添加设计图定义的大小
 * {<meta-data android:name="design_width" android:value="480"/>
    <meta-data android:name="design_height" android:value="640"/>}
 * 优先activity节点下定义的大小
 */
abstract class AdaptFragmentActivity: LuckyFragmentActivity(),Adapt {

    var adaptStrategy: AdaptStrategy ?= null

    private var adaptController = AdaptController()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        adaptController.onCreate(this)
        super.onCreate(savedInstanceState)
    }

    override fun getResources(): Resources{
        if (adaptStrategy == null){
            adaptStrategy = initAdaptStrategy()
        }
       return adaptController.applyResource(super.getResources(),
               adaptStrategy!!,
               isCancelAdapt(),
               isFollowSystemFontScale())
    }
}