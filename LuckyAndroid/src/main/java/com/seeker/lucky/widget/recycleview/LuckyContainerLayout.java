package com.seeker.lucky.widget.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Seeker
 * @date 2018/7/28/028  14:06
 * @describe 动态Item布局，当添加了overflow布局之后用得到
 */
class LuckyContainerLayout extends FrameLayout{

    private int overflowLayoutWidth = 0;

    private View overflowView;

    private View itemView;

    private LuckyItemContentLayout itemContentLayout;

    public LuckyContainerLayout(@NonNull Context context) {
        super(context);
    }

    public LuckyContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LuckyContainerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public LuckyContainerLayout addContentItemLayout(View itemView){
        this.itemView = itemView;
        return this;
    }

    public LuckyContainerLayout addOverflowLayout(View overflowView){
        this.overflowView = overflowView;
        return this;
    }

    public void finishAdd(){

        if (itemView == null){
            throw new NullPointerException("have you add content itemView?");
        }
        if (overflowView == null){
            throw new NullPointerException("have you add overflowView?");
        }

        int itemHeight = itemView.getLayoutParams().height;

        setOverflowHeightFollowItem(itemHeight);

        itemContentLayout = new LuckyItemContentLayout(getContext());
        itemContentLayout.addView(itemView);

        addView(itemContentLayout,new LayoutParams(itemView.getLayoutParams().width,itemHeight));
    }

    private void setOverflowHeightFollowItem(int height){
        overflowView.measure(0,0);
        overflowLayoutWidth = overflowView.getMeasuredWidth();
        if (overflowView instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) overflowView;
            for (int i = 0,count = viewGroup.getChildCount();i<count;i++){
                View child = viewGroup.getChildAt(i);
                child.getLayoutParams().height = height;
            }
        }
        LayoutParams p = new LayoutParams(overflowLayoutWidth,height);
        p.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
        addView(overflowView,p);
    }

    public int getOverflowLayoutWidth() {
        return overflowLayoutWidth;
    }

    public View getOverflowView() {
        return overflowView;
    }

    public LuckyItemContentLayout getItemContentLayout() {
        return itemContentLayout;
    }
}
