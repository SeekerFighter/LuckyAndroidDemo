package com.seeker.lucky.utils;

import android.content.Context;

/**
 * @author Seeker
 * @date 2019/1/17/017  16:18
 */
public class DisplayHelper {

    /**
     * 单位转换: dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

}
