package com.seeker.lucky.widget.memberview.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import androidx.annotation.NonNull;

/**
 * @author Seeker
 * @date 2019/1/8/008  16:19
 */
class ArrowDrawable extends BaseDrawable {

    private Paint mPaint = new Paint();

    private Path mPath = new Path();

    private ArrowDrawable(){
        mPaint.setColor(0xFFBFBFBF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    static ArrowDrawable newInstance(){
        return new ArrowDrawable();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect target = getTarget();
        mPaint.setStrokeWidth(target.width()/16f);
        mPath.reset();
        float half = target.width()/2f;
        mPath.moveTo(target.centerX()-half/3,half*0.3f);
        mPath.lineTo(target.centerX()+half/3,target.centerY());
        mPath.lineTo(target.centerX()-half/3,target.bottom-half*0.3f);
        canvas.drawPath(mPath,mPaint);
    }
}
