package com.seeker.lucky.memberview.delegate;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;
import com.seeker.lucky.memberview.drawable.DrawableFactory;

/**
 * @author Seeker
 * @date 2018/12/25/025  14:29
 */
public class SwitchDelegate extends CompoundButtonDelegate<Switch>{

    private static final int WIDTH = 50;

    private static final int HEIGHT = 25;

    private int width;

    private int height;

    public SwitchDelegate(MemberView container) {
        super(container);
        this.width = applyDimension(WIDTH);
        this.height = applyDimension(HEIGHT);
    }

    @Override
    public void setTypeAttrs(TypedArray typeAttrs) {
        int w = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeWidth,width);
        int h = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeHeight,height);
        boolean isChecked = typeAttrs.getBoolean(R.styleable.MemberView_isChecked,false);
        Drawable track = typeAttrs.getDrawable(R.styleable.MemberView_switchTrackRes);
        Drawable thumb = typeAttrs.getDrawable(R.styleable.MemberView_switchThumbRes);
        if (thumb == null || track == null){
            track = DrawableFactory.getSwitchTrackDrawable(w,h,applyDimension(15));
            thumb = DrawableFactory.getSwitchThumbDrawable(Math.min(w,h),applyDimension(1));
        }
        compoundButton = new Switch(mContext);
        compoundButton.setTrackDrawable(track);
        compoundButton.setThumbDrawable(thumb);
        compoundButton.setChecked(isChecked);
        container.addView(compoundButton,new LinearLayout.LayoutParams(w,h));
    }
}
