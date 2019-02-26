package com.seeker.lucky.app.adapt

import android.util.DisplayMetrics
import com.seeker.lucky.extensions.getStatusBarHeight
import com.seeker.lucky.log.LuckyLogger

/**
 *@author  Seeker
 *@date    2019/2/25/025  14:44
 *@describe 适配前的默认参数
 */
class DefaultStore {

    companion object {
        val instance: DefaultStore by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DefaultStore() }
    }

    private var setStored = false

    var density = 0f

    var densityDpi = 0

    var scaledDensity = 0f

    var displayWidth = 0//实际显示宽 单位px

    var displayHeight = 0

    var statusBarHeight = 0//顶部状态栏高度

    var displayWidthInDp = 0//实际显示宽 单位dp

    var displayHeightInDp = 0

    var statusBarHeightInDp = 0//顶部状态栏高度 dp

    var screenWidth = 0//显示屏幕实际宽 dp

    var screenHeight = 0 //dp，不包含底部虚拟按键高度

    fun storeDefault(dm: DisplayMetrics){
        if (!setStored) {
            this.density = dm.density
            this.densityDpi = dm.densityDpi
            this.scaledDensity = dm.scaledDensity
            this.displayWidth = dm.widthPixels
            this.displayHeight = dm.heightPixels
            this.statusBarHeight = getStatusBarHeight()
            this.displayWidthInDp = (displayWidth / density).toInt()
            this.displayHeightInDp = (displayHeight / density).toInt()
            this.statusBarHeightInDp = (statusBarHeight / density).toInt()
            this.screenWidth = displayWidthInDp
            this.screenHeight = displayHeightInDp + statusBarHeightInDp
            this.setStored = true
            LuckyLogger.v(msg = "density = $density,densityDpi = $densityDpi,scaledDensity = $scaledDensity" +
                    ",displayWidth = $displayWidth,displayHeight = $displayHeight,statusBarHeight = $statusBarHeight" +
                    ",displayWidthInDp = $displayWidthInDp,displayHeightInDp = $displayHeightInDp,statusBarHeightInDp = $statusBarHeightInDp" +
                    ",screenWidth = $screenWidth,screenHeight = $screenHeight")
        }
    }

    fun setDisplayMetrics(dm:DisplayMetrics){
        if (setStored){
            dm.density = this.density
            dm.densityDpi = this.densityDpi
            dm.scaledDensity = this.scaledDensity
        }
    }

    fun setZeroStored(){
        density = 0f
        densityDpi = 0
        scaledDensity = 0f
        displayWidth = 0
        displayHeight = 0
        statusBarHeight = 0
        displayWidthInDp = 0
        displayHeightInDp = 0
        statusBarHeightInDp = 0
        screenWidth = 0
        screenHeight = 0
        setStored = false
    }

}