package com.seeker.lucky.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2019/1/9/009  16:17
 * @describe TODO
 */
public class LuckyGridDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;
    private int mSpanCount;

    public LuckyGridDecoration(Context context, int spanCount) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mSpanCount = spanCount;
    }

    public void setDrawable( Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(@NonNull Canvas c,@NonNull RecyclerView parent,@NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            int column = (position + 1) % 3;
            column  = column == 0 ? mSpanCount : column;
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(child.getTranslationY());
            final int bottom = top + mDivider.getIntrinsicHeight();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(child.getTranslationX());
            final int right = left + mDivider.getIntrinsicHeight();

            mDivider.setBounds(child.getLeft(), top, right, bottom);
            mDivider.draw(c);

            if(column < mSpanCount) {
                mDivider.setBounds(left, child.getTop(), right, bottom);
                mDivider.draw(c);
            }

        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect,@NonNull View view,@NonNull RecyclerView parent,@NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if((position+1) % mSpanCount > 0) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }

}
