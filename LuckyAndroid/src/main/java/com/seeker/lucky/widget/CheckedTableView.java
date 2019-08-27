package com.seeker.lucky.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.seeker.luckya.R;
import com.seeker.lucky.utils.ColorHelper;
import com.seeker.lucky.utils.DisplayHelper;

import androidx.annotation.Nullable;

/**
 * @author Seeker
 * @date 2017/9/21/021  16:46
 */

public class CheckedTableView extends LinearLayout {

    private static final int DEFAULT_SIZE = -2;

    private static final int POSITION_LEFT = 0;//左边
    private static final int POSITION_MID = 1;//中间
    private static final int POSITION_RIGHT = 2;//右边
    private static final int POSITION_SINGLE = 3;

    private int strokeWith;//边线宽度

    private int strokeColor;//边线颜色

    private int cornerRadius;//圆角大小

    private int checkedColor;//选中颜色

    private int pressedColor;//按下时的颜色

    private int normalColor;//正常颜色

    private float textSize;//文字大小

    private int textCheckedColor;//文字选中的颜色

    private int textNormalColor;//文字正常颜色

    private int itemWidth;//每个单元的宽度

    private int itemHeight;//每个单元的高度

    private CharSequence[] chars;

    private OnTabCheckedListener mListener;

    private CheckedTextView preCheckedView;

    public CheckedTableView(Context context) {
        this(context, null);
    }

    public CheckedTableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckedTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckedTableView);

        if (ta != null) {
            try {
                strokeWith = ta.getDimensionPixelSize(R.styleable.CheckedTableView_tabStrokeWidth, DisplayHelper.dp2px(context, 0.5f));
                strokeColor = ta.getColor(R.styleable.CheckedTableView_tabStrokeColor, Color.parseColor("#3e7bff"));
                cornerRadius = ta.getDimensionPixelSize(R.styleable.CheckedTableView_tabCornerRadius, DisplayHelper.dp2px(context, 3.5f));
                checkedColor = ta.getColor(R.styleable.CheckedTableView_tabCheckedColor, Color.parseColor("#3e7bff"));
                pressedColor = ta.getColor(R.styleable.CheckedTableView_tabPressedColor, ColorHelper.lightAlpha(checkedColor,200));
                normalColor = ta.getColor(R.styleable.CheckedTableView_tabNormalColor, Color.TRANSPARENT);
                textSize = ta.getDimensionPixelSize(R.styleable.CheckedTableView_tabTextSize, DisplayHelper.dp2px(context, 13));
                chars = ta.getTextArray(R.styleable.CheckedTableView_tabTextArray);
                textCheckedColor = ta.getColor(R.styleable.CheckedTableView_tabTextCheckedColor, Color.WHITE);
                textNormalColor = ta.getColor(R.styleable.CheckedTableView_tabTextNormalColor,checkedColor);
                itemWidth = ta.getDimensionPixelSize(R.styleable.CheckedTableView_tabItemWidth, DEFAULT_SIZE);
                itemHeight = ta.getDimensionPixelSize(R.styleable.CheckedTableView_tabItemHeight,DEFAULT_SIZE);
            } finally {
                ta.recycle();
            }
        }

        addView();
    }

    private void addView() {
        if (chars == null || chars.length == 0) {
            throw new NullPointerException("text array can't be null.");
        }
        final int length = chars.length;
        if (length == 1) {
            addChild(POSITION_SINGLE, chars[0], 0);
        } else {

            for (int i = 0; i < length; ++i) {
                if (i == 0) {
                    addChild(POSITION_LEFT, chars[i], i);
                } else if (i == length - 1) {
                    addChild(POSITION_RIGHT, chars[i], i);
                } else {
                    addChild(POSITION_MID, chars[i], i);
                }
            }
        }

        post(new Runnable() {
            @Override
            public void run() {
                resizeChildWidth();
            }
        });

    }

    private void resizeChildWidth() {
        final int count = getChildCount();
        int maxWidth = 0;
        int maxHeight = 0;

        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            measureChild(child, 0, 0);
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight,child.getMeasuredHeight());
        }

        if (itemHeight != DEFAULT_SIZE){
            maxHeight = itemHeight;
        }
        if (itemWidth != DEFAULT_SIZE) {
            maxWidth = itemWidth;
        }

        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams p = child.getLayoutParams();
            p.width = maxWidth;
            p.height = maxHeight;
            child.setLayoutParams(p);
        }

        invalidate();
    }

    private void addChild(int position, CharSequence text, int index) {
        final CheckedTextView ctv = createCheckedTextView();
        LayerDrawable layer = createBackground(position);
        ctv.setBackground(layer);
        ctv.setChecked(index == 0);
        ctv.setText(text);
        ctv.setTag(index);
        addView(ctv, index);
        if (index == 0) {
            preCheckedView = ctv;
        }
        ctv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                preCheckedView.setChecked(false);
                ctv.setChecked(true);
                if (mListener != null) {
                    mListener.onChecked(ctv, (int) ctv.getTag());
                }
                preCheckedView = ctv;
            }
        });
    }

    private CheckedTextView createCheckedTextView() {
        CheckedTextView ctv = new CheckedTextView(getContext());
        ctv.setMaxLines(1);
        ctv.getPaint().setTextSize(textSize);
        ctv.setGravity(Gravity.CENTER);
        ctv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ctv.setTextColor(createColorDrawable());
        ctv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        int padding = DisplayHelper.dp2px(getContext(), 5);
        ctv.setPadding(padding, padding, padding, padding);
        return ctv;
    }

    private LayerDrawable createBackground(int position) {

        Drawable[] layers = new Drawable[1];
        layers[0] = createSelector(position);

        LayerDrawable layer = new LayerDrawable(layers);

        if (position == POSITION_LEFT) {
            layer.setLayerInset(0, 0, 0, -strokeWith, 0);
        } else if (position == POSITION_RIGHT) {
            layer.setLayerInset(0, 0, 0, 0, 0);
        } else if (position == POSITION_MID) {
            layer.setLayerInset(0, 0, 0, -strokeWith, 0);
        } else if (position == POSITION_SINGLE) {
            layer.setLayerInset(0, 0, 0, 0, 0);
        }

        return layer;
    }

    private StateListDrawable createSelector(int position) {

        StateListDrawable stateDrawable = new StateListDrawable();
        GradientDrawable checked = createShapeDrawable(position, checkedColor);
        GradientDrawable pressed = createShapeDrawable(position, pressedColor);
        GradientDrawable normal = createShapeDrawable(position, normalColor);

        stateDrawable.addState(new int[]{android.R.attr.state_checked}, checked);
        stateDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        stateDrawable.addState(new int[]{}, normal);

        return stateDrawable;
    }

    private GradientDrawable createShapeDrawable(int position, int soldColor) {

        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(strokeWith, strokeColor);
        drawable.setColor(soldColor);
        float[] radii;

        if (position == POSITION_LEFT) {
            radii = new float[]{
                    cornerRadius, cornerRadius,
                    0, 0,
                    0, 0,
                    cornerRadius, cornerRadius};
        } else if (position == POSITION_RIGHT) {
            radii = new float[]{
                    0, 0,
                    cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius,
                    0, 0};
        } else if (position == POSITION_MID) {
            radii = new float[]{
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0};
        } else {
            radii = new float[]{
                    cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius};
        }

        drawable.setCornerRadii(radii);
        return drawable;
    }

    private ColorStateList createColorDrawable() {

        int[] colors = new int[]{textCheckedColor, textNormalColor};

        int[][] states = new int[][]{
                {android.R.attr.state_checked},
                {}
        };

        return new ColorStateList(states, colors);
    }

    public void setCheckedIndex(int index) {
        preCheckedView.setChecked(false);
        preCheckedView = (CheckedTextView) getChildAt(index);
        if (preCheckedView == null) {
            throw new IndexOutOfBoundsException("Can't find child at " + index + ",child count is " + getChildCount());
        }
        preCheckedView.setChecked(true);
        if (mListener != null) {
            mListener.onChecked(preCheckedView, (int) preCheckedView.getTag());
        }
    }

    public void setTableTextArray(CharSequence... array) {
        this.chars = array;
        removeAllViews();
        addView();
    }

    public CharSequence[] getTableTextArray(){
        return chars;
    }

    public CharSequence getCheckedText(int position){
        return chars[position];
    }

    public void setOnTabCheckedListener(OnTabCheckedListener listener) {
        this.mListener = listener;
    }

    public interface OnTabCheckedListener {
        void onChecked(CheckedTextView checkedView, int position);
    }

}
