package com.seeker.lucky.widget.recycleview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author Seeker
 * @date 2018/8/20/020  15:18
 * @describe 点击触摸辅助
 */
final class TouchedHelper {

    private TouchedHelper(){

    }

    /**
     * 判断是否点击在设置了onclicklistener监听的view上
     * @return
     */
    static boolean isTouchedInClickableView(View parent, int x, int y){
        if (parent == null) return false;
        View child = findViewByXY(parent, x, y);
        return child != null && child.hasOnClickListeners();
    }

    private static View findViewByXY(View parent,int x, int y){
        if (parent == null) return null;
        View target = parent;
        if (parent instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) parent;
            for (int i = 0,count = viewGroup.getChildCount(); i < count;++i){
                View child = viewGroup.getChildAt(i);
                target = findViewByXY(child,x,y);
                if (target != null){
                    break;
                }
            }
        }

        if (target != null && isInTargetView(target,x,y)){
            return target;
        }

        return null;
    }

    private static boolean isInTargetView(@NonNull View target, int x, int y){
        int[] location = new int[2];
        target.getLocationOnScreen(location);
        int left = location[0];
        int right = left + target.getMeasuredWidth();
        int top = location[1];
        int bottom = top + target.getMeasuredHeight();
        return x >= left && x <= right && y >= top && y <= bottom;
    }
}
