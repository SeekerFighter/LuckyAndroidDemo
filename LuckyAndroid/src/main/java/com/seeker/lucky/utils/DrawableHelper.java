package com.seeker.lucky.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;

/**
 * @author Seeker
 * @date 2019/1/11/011  14:16
 * @describe 创建各式各样的图片
 */
public final class DrawableHelper {

    private DrawableHelper() {
        throw new IllegalStateException("no need instance.");
    }

    private static final float CORNER_RADIUS[] = new float[8];

    public static final int LEFT_TOP = 1;
    public static final int RIGHT_TOP = LEFT_TOP << 1;
    public static final int RIGHT_BOTTOM = RIGHT_TOP << 1;
    public static final int LEFT_BOTTOM = RIGHT_BOTTOM << 1;
    public static final int LEFT = LEFT_TOP | LEFT_BOTTOM;
    public static final int RIGHT = RIGHT_TOP | RIGHT_BOTTOM;
    public static final int TOP = LEFT_TOP | RIGHT_TOP;
    public static final int BOTTOM = LEFT_BOTTOM | RIGHT_BOTTOM;
    public static final int ALL = LEFT | RIGHT;
    public static final int NONE = ~ALL;

    @IntDef({LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM, LEFT, RIGHT, TOP, BOTTOM, ALL, NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CornerDirection {
    }

    public static final int RECTANGLE = GradientDrawable.RECTANGLE;
    public static final int OVAL = GradientDrawable.OVAL;

    @IntDef({RECTANGLE, OVAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
    }

    /**
     * 创建一个圆角drawable
     *
     * @param shape       形状
     * @param solidColor  图片填充颜色
     * @param strokeColor 图片边框颜色
     * @param strokeWidth 边框宽度
     * @param radius      圆角角度
     * @param direction   圆角方向
     * @param size        默认大小
     * @return
     */
    public static Drawable createCornerDrawable(@Shape int shape,
                                                @ColorInt int solidColor,
                                                @ColorInt int strokeColor,
                                                @IntRange(from = 0) int strokeWidth,
                                                @FloatRange(from = 0) float radius,
                                                @CornerDirection int direction,
                                                int[] size) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(solidColor);
        drawable.setShape(shape);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadii(radius(radius, direction));
        if (size != null && size.length >= 2) {
            drawable.setSize(size[0], size[1]);
        }
        return drawable;
    }

    /**
     * 由一个drawable生成bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;
        else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0))
            return null;

        try {
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private static float[] radius(float radius, int direction) {
        Arrays.fill(CORNER_RADIUS, 0f);
        switch (direction) {
            case LEFT_TOP:
                CORNER_RADIUS[0] = CORNER_RADIUS[1] = radius;
                break;
            case RIGHT_TOP:
                CORNER_RADIUS[2] = CORNER_RADIUS[3] = radius;
                break;
            case RIGHT_BOTTOM:
                CORNER_RADIUS[4] = CORNER_RADIUS[5] = radius;
                break;
            case LEFT_BOTTOM:
                CORNER_RADIUS[6] = CORNER_RADIUS[7] = radius;
                break;
            case LEFT:
                CORNER_RADIUS[0] = CORNER_RADIUS[1] = CORNER_RADIUS[6] = CORNER_RADIUS[7] = radius;
                break;
            case RIGHT:
                CORNER_RADIUS[2] = CORNER_RADIUS[3] = CORNER_RADIUS[4] = CORNER_RADIUS[5] = radius;
                break;
            case TOP:
                CORNER_RADIUS[0] = CORNER_RADIUS[1] = CORNER_RADIUS[2] = CORNER_RADIUS[3] = radius;
                break;
            case BOTTOM:
                CORNER_RADIUS[4] = CORNER_RADIUS[5] = CORNER_RADIUS[6] = CORNER_RADIUS[7] = radius;
                break;
            case ALL:
                Arrays.fill(CORNER_RADIUS, radius);
                break;
            case NONE:
                break;
        }
        return CORNER_RADIUS;
    }


    public static StateListDrawable createSelectedListDrawable(Drawable normal,Drawable selected){
        StateListDrawable stateDrawable = new StateListDrawable();
        stateDrawable.addState(new int[]{android.R.attr.state_selected}, selected);
        stateDrawable.addState(new int[]{}, normal);
        return stateDrawable;
    }

}
