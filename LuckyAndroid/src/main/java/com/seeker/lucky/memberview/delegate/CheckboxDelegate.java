package com.seeker.lucky.memberview.delegate;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;
import com.seeker.lucky.memberview.drawable.DrawableFactory;

/**
 * @author Seeker
 * @date 2018/12/25/025  14:29
 * @describe 带有checkbox
 */
public class CheckboxDelegate extends CompoundButtonDelegate<CheckBox>{

    private static final int DEFAULT_SIZE = 24;

    private int defaultSize;

    public CheckboxDelegate(MemberView container) {
        super(container);
        defaultSize = applyDimension(DEFAULT_SIZE);
    }

    @Override
    public void setTypeAttrs(TypedArray typeAttrs) {
        int width = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeWidth,defaultSize);
        int height = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeHeight,defaultSize);
        boolean isChecked = typeAttrs.getBoolean(R.styleable.MemberView_isChecked,false);
        Drawable bg = typeAttrs.getDrawable(R.styleable.MemberView_checkboxRes);
        if (bg == null){
            bg = DrawableFactory.getCheckStateDrawable();
        }
        compoundButton = new CheckBox(mContext);
        compoundButton.setButtonDrawable(null);
        compoundButton.setBackground(bg);
        compoundButton.setChecked(isChecked);
        container.addView(compoundButton,new LinearLayout.LayoutParams(width,height));
    }
}
