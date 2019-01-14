package com.seeker.lucky.memberview.delegate;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seeker.lucky.R;
import com.seeker.lucky.memberview.MemberView;

/**
 * @author Seeker
 * @date 2018/12/25/025  14:29
 * @describe TODO
 */
public class ImageDelegate extends BaseDelegate{

    public ImageDelegate(MemberView container) {
        super(container);
    }

    @Override
    public void setTypeAttrs(TypedArray typeAttrs) {
        ImageView imageView = new ImageView(mContext);
        Drawable image = typeAttrs.getDrawable(R.styleable.MemberView_imageRes);
        int width = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeWidth,WRAP_CONTENT);
        int height = typeAttrs.getDimensionPixelSize(R.styleable.MemberView_coreTypeHeight,WRAP_CONTENT);
        imageView.setImageDrawable(image);
        container.addView(imageView,new LinearLayout.LayoutParams(width,height));
    }
}
