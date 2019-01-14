package com.seeker.lucky.memberview.delegate;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;
import com.seeker.lucky.memberview.drawable.DrawableFactory;

/**
 * @author Seeker
 * @date 2018/12/25/025  14:29
 */
public class ArrowDelegate extends BaseDelegate{

    private static final int DEFAULT_SIZE = 18;

    private int defaultSize;

    public ArrowDelegate(MemberView container) {
        super(container);
        this.defaultSize = applyDimension(DEFAULT_SIZE);
    }

    @Override
    public void setTypeAttrs(TypedArray typeAttrs) {
        int width = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeWidth,defaultSize);
        int height = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeHeight,defaultSize);
        ImageView arrowImg = new ImageView(mContext);
        Drawable arrow = typeAttrs.getDrawable(R.styleable.MemberView_arrowRes);
        if(arrow == null){
            arrow = DrawableFactory.getArrowDrawable();
        }
        arrowImg.setImageDrawable(arrow);
        container.addView(arrowImg,new LinearLayout.LayoutParams(width,height));
    }
}
