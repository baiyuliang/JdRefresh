package com.byl.jdrefresh.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;

public class AnimUtils {

    public static void start(int distance, int duration, OnAnimListener onAnimListener) {
        ValueAnimator animator = ValueAnimator.ofInt(distance);
        animator.setDuration(duration);
        animator.start();
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            onAnimListener.onUpdate(value);
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimListener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public interface OnAnimListener {
        void onUpdate(int value);

        void onEnd();
    }
}
