package com.seeker.lucky.memberview.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.NonNull;

/**
 * @author Seeker
 * @date 2019/1/8/008  15:20
 */
final class CheckBoxDrawable {

    private CheckBoxDrawable(){

    }

    static StateListDrawable createCheckStateDrawable(){
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked},new CheckedDrawable());
        stateListDrawable.addState(new int[]{},new NormalDrawable());
        return stateListDrawable;
    }

    private static final class NormalDrawable extends BaseDrawable{

        private Paint mPaint = new Paint();

        NormalDrawable(){
            mPaint.setAntiAlias(true);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            Rect target = getTarget();
            mPaint.setStrokeWidth(target.width()/20f);
            float radius = target.width()/2*0.8f;
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(target.centerX(),target.centerY(),radius,mPaint);
            mPaint.setColor(0xFF9F9F9F);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(target.centerX(),target.centerY(),radius,mPaint);
        }
    }

    private static final class CheckedDrawable extends BaseDrawable {

        private Paint mPaint = new Paint();

        private Path mLinePath = new Path();

        CheckedDrawable(){
            mPaint.setAntiAlias(true);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            Rect target = getTarget();
            mPaint.setStrokeWidth(target.width()/16f);
            float radius = target.width()/2*0.8f;
            mPaint.setColor(0xFF4CD964);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(target.centerX(),target.centerY(),radius,mPaint);
            mLinePath.reset();
            float line1StartX = target.centerX() - target.width()/5;
            mLinePath.moveTo(line1StartX,target.centerY());
            mLinePath.lineTo(line1StartX+radius/3,target.centerY()+radius/3);
            mLinePath.lineTo(line1StartX+radius,target.centerY()-radius/3);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mLinePath,mPaint);
        }
    }
}
