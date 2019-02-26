package com.seeker.lucky.app.adapt

import android.content.res.Resources
import com.seeker.lucky.extensions.getActivityMeteData
import com.seeker.lucky.extensions.getApplicationMeteData

/**
 *@author  Seeker
 *@date    2019/2/26/026  16:15
 *@describe UI自适配控制器
 */
class AdaptController {

    private val DESIGN_WIDTH = "design_width"// UNIT dp

    private val DESIGN_HEIGHT = "design_height"//UNIT dp

    private var designWidth: Int = Int.MIN_VALUE

    private var designHeight: Int = Int.MIN_VALUE

    private var designedGetter = false

    private var adaptStrategy: AdaptStrategy? = null

    fun onCreate(activity: AdaptFragmentActivity) {
        getDesignSize(activity)
    }

    fun applyResource(resources: Resources,adaptStrategy: AdaptStrategy,isCancelAdapt: Boolean, isFollowSystemFontScale: Boolean): Resources {
        val dm = resources.displayMetrics
        DefaultStore.instance.storeDefault(dm)
        if (isCancelAdapt || !designedGetter) {
            DefaultStore.instance.setDisplayMetrics(dm)
            if (!isFollowSystemFontScale) {
                dm.scaledDensity = dm.density
            }
        } else {
            if (this.adaptStrategy == null) {
                this.adaptStrategy = adaptStrategy
                this.adaptStrategy!!.set(DefaultStore.instance, designWidth, designHeight, isFollowSystemFontScale)
            }
            dm.density = this.adaptStrategy!!.getDensity()
            dm.densityDpi = this.adaptStrategy!!.getDensityDpi()
            dm.scaledDensity = this.adaptStrategy!!.getScaledDensity()
        }
        return resources
    }

    private fun getDesignSize(activity: AdaptFragmentActivity): Boolean {
        if (!designedGetter) {
            activity.getActivityMeteData()?.let {
                if (it.containsKey(DESIGN_WIDTH)) {
                    designWidth = it.getInt(DESIGN_WIDTH)
                    designedGetter = true
                }
                if (it.containsKey(DESIGN_HEIGHT)) {
                    designHeight = it.getInt(DESIGN_HEIGHT)
                    designedGetter = true
                }
            }
        }
        if (!designedGetter) {
            activity.getApplicationMeteData()?.let {
                if (it.containsKey(DESIGN_WIDTH)) {
                    designWidth = it.getInt(DESIGN_WIDTH)
                    designedGetter = true
                }
                if (it.containsKey(DESIGN_HEIGHT)) {
                    designHeight = it.getInt(DESIGN_HEIGHT)
                    designedGetter = true
                }
            }
        }
        return designedGetter
    }

}