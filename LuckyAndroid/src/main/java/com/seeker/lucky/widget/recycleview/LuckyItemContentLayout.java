package com.seeker.lucky.widget.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * @author Seeker
 * @date 2018/7/28/028  15:22
 */
class LuckyItemContentLayout extends LinearLayout{

    private Scroller mScroller;

    private LuckyItemToucListener.OnOverflowChangedAnimator mOverflowChangedListener;

    private LuckyContainerLayout parent;

    public LuckyItemContentLayout(Context context) {
        this(context,null);
    }

    public LuckyItemContentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckyItemContentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()){
            int oldX = getScrollX();
            int x = mScroller.getCurrX();
            if (oldX != x){
                scrollTo(x,0);
                if (mOverflowChangedListener != null){
                    float percent = 0f;
                    int width = parent.getOverflowLayoutWidth();
                    if (getScrollX() <= 0){
                        percent = 0f;
                    }else if (getScrollX() >= width){
                        percent = 1f;
                    }else {
                        percent = 1f * getScrollX() / width;
                    }
                    mOverflowChangedListener.onOverflowChanged(parent.getOverflowView(),width,percent);
                }
            }
            invalidate();
        }
    }

    public void startScroll(int startX,int startY,int delatX,int delatY){
        mScroller.startScroll(startX, startY, delatX, delatY);
        invalidate();
    }

    public void setParent(LuckyContainerLayout parent) {
        this.parent = parent;
    }

    protected void setOnOverflowChangedAnimator(LuckyItemToucListener.OnOverflowChangedAnimator listener){
        this.mOverflowChangedListener = listener;
    }

}
