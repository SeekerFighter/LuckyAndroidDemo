package com.seeker.lucky.app.adapt

import com.seeker.lucky.log.LuckyLogger

/**
 *@author  Seeker
 *@date    2019/2/25/025  16:49
 *@describe 适配策略
 */
open class AdaptStrategy{

    lateinit var defaultStore: DefaultStore//存储设备默认信息

    var designWidth :Int = -1//设计图宽 dp

    var designHeight :Int = -1//设计图高 dp

    var isFollowSystemFontScale:Boolean = true//是否跟随系统文字大小系数

    fun set(defaultStore: DefaultStore,
            designWidth :Int,
            designHeight :Int,
            isFollowSystemFontScale:Boolean){
        this.defaultStore = defaultStore
        this.designWidth = designWidth
        this.designHeight = designHeight
        this.isFollowSystemFontScale = isFollowSystemFontScale
        LuckyLogger.v(msg = "designWidth = $designWidth,designHeight = $designHeight,isFollowSystemFontScale = $isFollowSystemFontScale")
    }

    open fun getDensity():Float{
        if (designWidth != Int.MIN_VALUE) {
            return defaultStore.density * defaultStore.screenWidth / designWidth
        }else if (designHeight != Int.MIN_VALUE){
            return defaultStore.density * defaultStore.screenHeight / designHeight
        }
        return defaultStore.density
    }

    open fun getScaledDensity():Float = if (isFollowSystemFontScale){
        getDensity() * (defaultStore.scaledDensity / defaultStore.density)
    }else{
        getDensity()
    }

    open fun getDensityDpi():Int = (getDensity() * 160).toInt()
}