package com.seeker.lucky.app

/**
 *@author  Seeker
 *@date    2018/11/12/012  14:33
 *@describe UI界面辅助接口
 */
interface UIHelper {
    /**
     * 设置布局资源id
     */
    fun layoutResId(): Int = -1

    /**
     * 开始进行操作
     */
    fun todoWork()
}