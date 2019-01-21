package com.seeker.lucky.widget.recycleview.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

import com.seeker.lucky.widget.recycleview.LuckyItemToucListener;

/**
 * @author Seeker
 * @date 2018/7/31/031  14:21
 */
public class TranslationOverflowChangedAnimator implements LuckyItemToucListener.OnOverflowChangedAnimator{

    private float lastPercent = 0f;

    @Override
    public void onOverflowChanged(View overflowView, int overflowWidth, float percent) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(overflowView,"translationX",overflowWidth*(1-percent));
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(overflowView,"scaleX",lastPercent,percent);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(overflowView,"scaleY",lastPercent,percent);
        animatorSet.playTogether(translationX,scaleX,scaleY);
        animatorSet.setDuration(0);
        animatorSet.start();
        lastPercent = percent;
    }

}
