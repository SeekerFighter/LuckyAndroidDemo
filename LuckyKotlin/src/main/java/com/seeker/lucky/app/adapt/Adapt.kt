package com.seeker.lucky.app.adapt

/**
 *@author  Seeker
 *@date    2019/2/26/026  17:14
 *@describe 自适配接口
 */
interface Adapt {

    /**
     * 可重写，是否取消大小适配
     */
    fun isCancelAdapt():Boolean = false

    /**
     * 可重写，是否跟随系统设置的fontScale
     */
    fun isFollowSystemFontScale():Boolean = true

    /**
     * 可重写，返回适配策略，不为null
     */
    fun initAdaptStrategy():AdaptStrategy = AdaptStrategy()

}