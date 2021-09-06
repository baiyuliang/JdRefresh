package com.byl.jdrefresh.v1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @Title :
 * @Author : BaiYuliang
 * @Date : 2020-08-22
 * @Desc :
 */
public class CustomViewPager extends ViewPager {

    private float startX;
    private float startY;
    private boolean isRefreshing;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //消费掉（即不走自身的onTouch,也不走父容器的onTouch）
        if (isRefreshing) {
            startX = 0;
            startY = 0;
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
    
     @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = Math.abs(x - startX);
                float offsetY = Math.abs(y - startY);
                if (offsetX >= offsetY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    public float getStartY() {
        return startY;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }
}
