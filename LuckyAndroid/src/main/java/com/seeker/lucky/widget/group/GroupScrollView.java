package com.seeker.lucky.widget.group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * @author Seeker
 * @date 2019/1/24/024  17:53
 * @describe 一大串具有相同布局类型的子项集合
 */
public class GroupScrollView extends NestedScrollView {

    private static final String TAG = "lucky_GroupScrollView_container";

    private LinearLayout container;

    private List<Action> actions;

    public GroupScrollView(@NonNull Context context) {
        this(context,null);
    }

    public GroupScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GroupScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void addContainer(){
        container = new LinearLayout(getContext());
        container.setTag(TAG);
        container.setOrientation(LinearLayout.VERTICAL);
        addView(container,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    }

    public GroupScrollView addGroupItem(@NonNull GroupItem item){
        item.add(container);
        if (actions == null){
            actions = new ArrayList<>();
        }
        actions.add(item);
        return this;
    }

    public Map<String,String> getItemActionResponse(@IntRange(from = 0) int index){
        return actions.get(index).doAction();
    }

    public Map<String,String> getGroupActionResponse(){
        Map<String,String> groupMap = new HashMap<>();
        for (Action action:actions){
            groupMap.putAll(action.doAction());
        }
        return groupMap;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0){
            throw new IllegalStateException("GroupScrollView can't host direct child");
        }
        addContainer();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(child, index, params);
    }

}
