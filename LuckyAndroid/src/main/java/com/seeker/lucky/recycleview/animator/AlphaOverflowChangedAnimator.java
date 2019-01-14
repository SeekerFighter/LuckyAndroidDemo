package com.seeker.lucky.recycleview.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.seeker.lucky.recycleview.LuckyItemToucListener;

/**
 * @author Seeker
 * @date 2018/7/31/031  14:05
 */
public class AlphaOverflowChangedAnimator implements LuckyItemToucListener.OnOverflowChangedAnimator {

    private float lastPercent = 0f;

    @Override
    public void onOverflowChanged(View overflowView, int overflowWidth, float percent) {

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(overflowView,"alpha",lastPercent,percent);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(overflowView,"scaleX",lastPercent,percent);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(overflowView,"scaleY",lastPercent,percent);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(overflowView,"translationX",overflowWidth*(1-percent));
        animatorSet.setDuration(0);
        animatorSet.play(alpha).with(scaleX).with(scaleY).with(translationX);
        animatorSet.start();

        lastPercent = percent;
    }

}
