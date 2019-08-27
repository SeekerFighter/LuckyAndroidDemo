package com.seeker.lucky.widget.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;

import com.seeker.luckya.R;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/8/1/001  17:38
 * @describe 带有item动画的RecycleView
 */
public class LuckyAnimRecycleView extends LuckyRecycleView{

    public LuckyAnimRecycleView(Context context) {
        super(context);
    }

    public LuckyAnimRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LuckyAnimRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {

        final RecyclerView.LayoutManager layoutManager = getLayoutManager();

        if (getAdapter() != null && layoutManager instanceof GridLayoutManager){

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

            if (animationParams == null) {
                // If there are no animation parameters, create new once and attach them to
                // the LayoutParams.
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }

            // Next we are updating the parameters

            // Set the number of items in the RecyclerView and the index of this item
            animationParams.count = count;
            animationParams.index = index;

            // Calculate the number of columns and rows in the grid
            final int columns = ((GridLayoutManager) layoutManager).getSpanCount();
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            // Calculate the column/row position in the grid
            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;

        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout == null) return;
        if (layout instanceof  GridLayoutManager){
            setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.lucky_grid_layout_anim));
        }else {
            setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.lucky_list_layout_anim));
        }
    }
}
