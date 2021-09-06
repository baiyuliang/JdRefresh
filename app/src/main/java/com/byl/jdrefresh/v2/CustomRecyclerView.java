package com.byl.jdrefresh.v2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * @Title :
 * @Author : BaiYuliang
 * @Date : 2020-08-22
 * @Desc :
 */
public class CustomRecyclerView extends RecyclerView {

    private float startX;
    private float startY;
    private boolean isRefreshing;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
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
                if (offsetX >= offsetY) {//水平滑动自己处理
                    return true;//消费掉
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public float getStartY() {
        return startY;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }
}
