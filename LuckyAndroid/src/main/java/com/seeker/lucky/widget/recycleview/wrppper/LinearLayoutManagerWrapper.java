package com.seeker.lucky.widget.recycleview.wrppper;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author Seeker
 * @date 2018/8/6/006  9:08
 * @describe LayoutManager包装
 */
public class LinearLayoutManagerWrapper extends LinearLayoutManager {

    private boolean isScrollVerticallyEnable = true;

    public LinearLayoutManagerWrapper(Context context) {
        super(context);
    }

    public LinearLayoutManagerWrapper(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollVerticallyEnable && super.canScrollVertically();
    }

    public void setScrollVerticallyEnable(boolean scrollVerticallyEnable) {
        isScrollVerticallyEnable = scrollVerticallyEnable;
    }
}
