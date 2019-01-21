package com.seeker.lucky.widget.memberview.drawable;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @author Seeker
 * @date 2019/1/8/008  16:51
 */
public final class DrawableFactory {

    private DrawableFactory() {
        throw new IllegalStateException("no need instance.");
    }

    public static StateListDrawable getCheckStateDrawable() {
        return CheckBoxDrawable.createCheckStateDrawable();
    }

    public static Drawable getArrowDrawable() {
        return ArrowDrawable.newInstance();
    }

    public static Drawable getSwitchThumbDrawable(int size, int stokeW) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, createDrawable(GradientDrawable.OVAL, Color.WHITE, size, size, 0xFF4CD964, stokeW, size / 2));
        stateListDrawable.addState(new int[]{}, createDrawable(GradientDrawable.OVAL, Color.WHITE, size, size, 0xFFDCDCDC, stokeW, size / 2));
        return stateListDrawable;
    }

    public static Drawable getSwitchTrackDrawable(int w, int h, int radius) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, createDrawable(GradientDrawable.RECTANGLE, 0xFF4CD964, w, h, 0xFF4CD964, 0, radius));
        stateListDrawable.addState(new int[]{}, createDrawable(GradientDrawable.RECTANGLE, 0xFFDCDCDC, w, h, 0xFFDCDCDC, 0, radius));
        return stateListDrawable;
    }

    private static Drawable createDrawable(int shape, int solidColor, int w, int h, int strokeColor, int strokeW, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(shape);
        drawable.setColor(solidColor);
        drawable.setSize(w, h);
        drawable.setStroke(strokeW, strokeColor);
        drawable.setCornerRadius(radius);
        return drawable;
    }

}
