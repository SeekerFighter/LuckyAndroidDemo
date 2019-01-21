package com.seeker.lucky.widget.recycleview.wrppper;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author Seeker
 * @date 2018/8/6/006  9:30
 */
public class StaggeredGridLayoutManagerWrapper extends StaggeredGridLayoutManager {

    public StaggeredGridLayoutManagerWrapper(int spanCount, int orientation){
        super(spanCount, orientation);
    }

    public StaggeredGridLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
