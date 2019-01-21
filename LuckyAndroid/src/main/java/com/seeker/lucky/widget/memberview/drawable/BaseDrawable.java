package com.seeker.lucky.widget.memberview.drawable;

import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

/**
 * @author Seeker
 * @date 2019/1/8/008  16:20
 */
public abstract class BaseDrawable extends Drawable {

    private Rect mTarget = new Rect();

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();
        int half = Math.min(bounds.width(),bounds.height())/2;
        mTarget.set(centerX-half,centerY-half,centerX+half,centerY+half);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    public Rect getTarget() {
        return mTarget;
    }
}
