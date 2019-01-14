package com.seeker.lucky.memberview.delegate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;

/**
 * @author Seeker
 * @date 2018/12/25/025  14:18
 */
public abstract class BaseDelegate implements MemberDelegate {

    public static final int WRAP_CONTENT = -2;

    protected Context mContext;

    private DisplayMetrics displayMetrics;

    protected MemberView container;

    private TextView coreTextView;//head

    private TextView subTextView;//tail

    public BaseDelegate(MemberView container){
        this.container = container;
        this.mContext = container.getContext();
        this.displayMetrics = mContext.getResources().getDisplayMetrics();
    }

    @Override
    public void setUpAttrs(TypedArray typedArray) {
        addIcon(typedArray);
        addCoreText(typedArray);
        addSubText(typedArray);
        setTypeAttrs(typedArray);
    }

    private void addIcon(TypedArray typedArray){
        Drawable icon = typedArray.getDrawable(R.styleable.MemberView_coreIconRes);
        if (icon != null){
            int iconWidth = typedArray.getDimensionPixelSize(R.styleable.MemberView_coreIconWidth,WRAP_CONTENT);
            int iconHeight = typedArray.getDimensionPixelSize(R.styleable.MemberView_coreIconHeight,WRAP_CONTENT);
            //head
            ImageView coreImageView = new ImageView(mContext);
            coreImageView.setImageDrawable(icon);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(iconWidth,iconHeight);
            container.addView(coreImageView,params);
        }
    }

    private void addCoreText(TypedArray typedArray){
        String text = typedArray.getString(R.styleable.MemberView_coreTextRes);
        int color = typedArray.getColor(R.styleable.MemberView_coreTextColor,0xFF5A5A5A);
        int size = typedArray.getDimensionPixelSize(R.styleable.MemberView_coreTextSize,applyDimension(16));
        int marginStart = typedArray.getDimensionPixelSize(R.styleable.MemberView_coreTextMarginStart,0);
        int marginEnd = typedArray.getDimensionPixelSize(R.styleable.MemberView_coreTextMarginEnd,0);
        coreTextView = new TextView(mContext);
        Paint paint = coreTextView.getPaint();
        paint.setColor(color);
        paint.setTextSize(size);
        coreTextView.setMaxLines(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,-2,1);
        params.setMarginStart(marginStart);
        params.setMarginEnd(marginEnd);
        coreTextView.setText(text);
        container.addView(coreTextView,params);
    }

    private void addSubText(TypedArray typedArray){
        subTextView = new TextView(mContext);
        int color = typedArray.getColor(R.styleable.MemberView_subTextColor,0xFFEAEAEA);
        int size = typedArray.getDimensionPixelSize(R.styleable.MemberView_subTextSize,applyDimension(12));
        String text = typedArray.getString(R.styleable.MemberView_subTextRes);
        int marginEnd = typedArray.getDimensionPixelSize(R.styleable.MemberView_subTextMarginEnd,0);
        Paint paint = subTextView.getPaint();
        paint.setColor(color);
        paint.setTextSize(size);
        subTextView.setMaxLines(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2,-2);
        params.setMarginEnd(marginEnd);
        subTextView.setText(text);
        container.addView(subTextView,params);
    }

    @Override
    public void setCoreText(CharSequence coreText) {
        if (coreTextView != null){
            coreTextView.setText(coreText);
        }
    }

    @Override
    public void appendCoreText(CharSequence appendedCoreText) {
        if (coreTextView != null){
            coreTextView.append(appendedCoreText);
        }
    }

    @Override
    public void setSubText(CharSequence subText) {
        if (subTextView != null){
            subTextView.setText(subText);
        }
    }

    @Override
    public void appendSubText(CharSequence appendedSubText) {
        if (subTextView != null){
            subTextView.append(appendedSubText);
        }
    }

    public int applyDimension(float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,displayMetrics);
    }

    public abstract void setTypeAttrs(TypedArray typeAttrs);

}
