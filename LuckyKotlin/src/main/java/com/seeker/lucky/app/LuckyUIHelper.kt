package com.seeker.lucky.app

import android.view.View

/**
 *@author  Seeker
 *@date    2018/11/12/012  14:33
 *@describe UI界面辅助接口
 */
interface LuckyUIHelper {
    /**
     * 设置布局资源id
     */
    fun layoutResId(): Int

    /**
     *用于填充展示fragment的
     */

    fun fragmentContainerId(): Int = -1
    /**
     * 开始进行操作
     */
    fun onViewCreated(contentView: View)
}