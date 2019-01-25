package com.seeker.lucky.widget.group;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * @author Seeker
 * @date 2019/1/25/025  9:09
 */
public abstract class GroupItem<T> implements Action{

    public T mSource;

    public GroupItem(T source){
        this.mSource = source;
    }

    void add(@NonNull LinearLayout container){
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View itemView = inflater.inflate(layoutResId(),container,false);
        container.addView(itemView);
        onViewAdded(itemView);
    }

    /**
     * 布局id
     * @return
     */
    @LayoutRes
    public abstract int layoutResId();

    /**
     * 当itemView添加到布局之后调用
     * @param itemView
     */
    public abstract void onViewAdded(View itemView);

}
