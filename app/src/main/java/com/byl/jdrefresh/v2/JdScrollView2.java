package com.byl.jdrefresh.v2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.HomeNavigatorAdapter;
import com.byl.jdrefresh.utils.AnimUtils;
import com.byl.jdrefresh.utils.StatusBarUtil;
import com.byl.jdrefresh.utils.SysUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

/**
 * @Title :  首页下拉刷新特效
 * @Author : BaiYuliang
 * @Date : 2020-08-22
 * @Desc :
 */
public class JdScrollView2 extends NestedScrollView {

    private Context context;
    private View view;
    private ImageView iv_ad;
    private LinearLayout ll_content;
    private TextView tv_refresh_state;
    private MagicIndicator magicIndicator;
    private CustomRecyclerView recyclerView;


    private int marginTop, paddingTop;
    private static final float REFRESH_RATIO = 2.0f;//下拉系数（阻尼系数）,越大下拉灵敏度越低
    private static final int AD_START_SCROLL_DISTANCE = 100;//广告图开始滑动的距离阈值（超过该值时才开始滑动）
    private static final int REFRESHL_DISTANCE = 150;//刷新距离阈值（超过该值时才开始刷新）
    private float startY = -1,//手指起始触摸位置
            offsetY,//手指移动距离（真实移动距离）
            oldDistance;//经过下拉系数换算的最终距离(实际使用的距离，也就是tab栏移动的距离)
    private float maxOffsetY;//下拉最大距离
    private int scrollY;//整个界面向上滑动的距离
    private int adScrollDistance;//广告图移动高度
    private boolean isInterceptTouch;
    private boolean isInterceptScroll;

    private static final int REFRESH_DONE = 0;//刷新完成/初始状态
    private static final int PULL_TO_REFRESH = 1;//下拉刷新
    private static final int RELEASE_TO_REFRESH = 2;//释放刷新
    private static final int REFRESHING = 3;//刷新中

    private static int REFRESH_STATUS = PULL_TO_REFRESH;

    private int screenHeight;//屏幕高度
    private int imageShowHeight = 180;//背景图露出的高度(dp)
    private int topRemainHeight;//不知道该起什么名字了,这个高度是指,背景图露出的高度内，除去状态栏高度和搜索栏高度后剩余的高度

    public JdScrollView2(Context context) {
        super(context);
        init(context);
    }

    public JdScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JdScrollView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_jd_scrollview2, this, true);
        view = findViewById(R.id.view);
        iv_ad = findViewById(R.id.iv_ad);
        ll_content = findViewById(R.id.ll_content);
        tv_refresh_state = findViewById(R.id.tv_refresh_state);
        magicIndicator = findViewById(R.id.magicIndicator);
        recyclerView = findViewById(R.id.recyclerView);

    }

    void init(Context context) {
        this.context = context;
        setOverScrollMode(OVER_SCROLL_NEVER);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, SysUtils.Dp2Px(context, imageShowHeight)));
        screenHeight = SysUtils.getScreenHeight(context);
        topRemainHeight = SysUtils.Dp2Px(context, imageShowHeight) - StatusBarUtil.getStatusBarHeight(context) - SysUtils.Dp2Px(context, 40);//40是搜索栏高度，是多少就写多少
        //为什么图片高度要在屏幕高度的基础上多加了topRemainHeight呢，这个是在自动全屏并跳转时，能将顶部的tab栏完全隐藏掉，具体可看博客详解
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeight + topRemainHeight);
        marginTop = -(screenHeight + topRemainHeight - SysUtils.Dp2Px(context, imageShowHeight));
        layoutParams.topMargin = marginTop;
        iv_ad.setLayoutParams(layoutParams);
        iv_ad.setImageAlpha(0);
        paddingTop = StatusBarUtil.getStatusBarHeight(context);
        ll_content.setPadding(0, paddingTop, 0, 0);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        scrollY = t;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (!isInterceptScroll)
            super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        startY = recyclerView.getStartY() == 0 ? ev.getRawY() : recyclerView.getStartY();//先走的是recyclerView的OnTouch，所以从recyclerView取startY最准确
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //禁止触摸事件 以及 正在刷新时 禁止MOVE
                if (isInterceptTouch || REFRESH_STATUS == REFRESHING) return true;
                //这一步为了解决正在刷新时，手指又下拉，导致刷新完成时立马又自动下拉到了手指位置：
                //由于recyclerView做了dispatchTouchEvent监听，所以上面的情况只会出现在startY != 0 && recyclerView.getStartY() == 0这个条件下，
                //其它条件两者都是相等的
                if (startY != 0 && recyclerView.getStartY() == 0) return true;
                //如果是先向下滑动，然后不松开手指，又返回向上滑动
                if (maxOffsetY > 0 && maxOffsetY > offsetY) scrollY = 0;
                //如果是向上滑动，则不处理
                if (maxOffsetY <= 0 && scrollY > 0) return super.onTouchEvent(ev);
                //手指移动距离
                offsetY = ev.getRawY() - startY;
                //记录手指移动的最大值
                if (offsetY > maxOffsetY) maxOffsetY = offsetY;
                if (scrollY == 0 && offsetY > 0) {
                    isInterceptScroll = true;
                    float distance = offsetY / REFRESH_RATIO;//根据下拉系数，换算成最终距离
                    if (Math.abs(distance - oldDistance) < 1) return super.onTouchEvent(ev);
                    REFRESH_STATUS = PULL_TO_REFRESH;
                    if (distance >= AD_START_SCROLL_DISTANCE) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_ad.getLayoutParams();
                        if (layoutParams.topMargin < 0) {
                            adScrollDistance = (int) (distance - AD_START_SCROLL_DISTANCE);
                            layoutAd(marginTop + adScrollDistance);
                        }
                    }
                    if (distance > REFRESHL_DISTANCE) {
                        REFRESH_STATUS = RELEASE_TO_REFRESH;
                        tv_refresh_state.setText("释放刷新");

                    } else {
                        REFRESH_STATUS = PULL_TO_REFRESH;
                        tv_refresh_state.setText("下拉刷新");
                    }
                    int alpha = distance > 0 && distance * 2 <= 255 ? (int) distance * 2 : 255;
                    if (onPullListener != null) onPullListener.onPull(255 - alpha);
                    iv_ad.setImageAlpha(alpha);
                    ll_content.setPadding(0, (int) (paddingTop + distance), 0, 0);
                    oldDistance = distance;
                    return true;
                } else if (scrollY == 0 && maxOffsetY > 0 && offsetY <= 0) {
                    layoutAd(marginTop);
                    iv_ad.setImageAlpha(0);
                    ll_content.setPadding(0, paddingTop, 0, 0);
                    isInterceptTouch = true;//复位后禁止再次滑动
                    isInterceptScroll = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (adScrollDistance > 500) {
                    isInterceptTouch = true;
                    AnimUtils.start(-(int) (marginTop + adScrollDistance), 500, new AnimUtils.OnAnimListener() {
                        @Override
                        public void onUpdate(int value) {
                            layoutAd(marginTop + adScrollDistance + value);
                            ll_content.setPadding(0, (int) (paddingTop + adScrollDistance + AD_START_SCROLL_DISTANCE) + value, 0, 0);
                        }

                        @Override
                        public void onEnd() {
                            context.startActivity(new Intent(context, AdActivity.class));
                            new Handler().postDelayed(() -> {
                                tv_refresh_state.setText("下拉刷新");
                                isInterceptTouch = false;
                                recyclerView.setRefreshing(false);
                                isInterceptScroll = false;
                                REFRESH_STATUS = REFRESH_DONE;
                                layoutAd(marginTop);
                                iv_ad.setImageAlpha(0);
                                if (onPullListener != null) onPullListener.onPull(255);
                                ll_content.setPadding(0, paddingTop, 0, 0);
                                reset();
                            }, 300);
                        }
                    });
                    return true;
                }
                isInterceptTouch = false;
                if (maxOffsetY > 0) {
                    if (REFRESH_STATUS == RELEASE_TO_REFRESH) {//释放刷新
                        disableTab();
                        isInterceptTouch = true;
                        REFRESH_STATUS = REFRESHING;//刷新中
                        recyclerView.setRefreshing(true);
                        AnimUtils.start(adScrollDistance, 200, new AnimUtils.OnAnimListener() {
                            @Override
                            public void onUpdate(int value) {
                                layoutAd(marginTop + adScrollDistance - value);
                                ll_content.setPadding(0, (int) (paddingTop + oldDistance - value), 0, 0);
                            }

                            @Override
                            public void onEnd() {
                                tv_refresh_state.setText("正在刷新...");
                                reset();
                                if (onPullListener != null) {
                                    onPullListener.onPull(0);
                                    onPullListener.onRefresh();
                                }
                            }
                        });
                    } else {//没有触动刷新，直接复位
                        if (offsetY <= 0) {//手指松开时如果已经滑动到顶部，则不需要再执行动画
                            tv_refresh_state.setText("下拉刷新");
                            isInterceptTouch = false;
                            recyclerView.setRefreshing(false);
                            isInterceptScroll = false;
                            REFRESH_STATUS = REFRESH_DONE;
                            layoutAd(marginTop);
                            iv_ad.setImageAlpha(0);
                            if (onPullListener != null) onPullListener.onPull(255);
                            ll_content.setPadding(0, paddingTop, 0, 0);
                            reset();
                        } else {
                            AnimUtils.start((int) oldDistance, 150, new AnimUtils.OnAnimListener() {
                                @Override
                                public void onUpdate(int value) {
                                    if (adScrollDistance > 0 && value <= adScrollDistance) {
                                        layoutAd(marginTop + adScrollDistance - value);
                                    }
                                    ll_content.setPadding(0, paddingTop + (int) (oldDistance - value), 0, 0);
                                }

                                @Override
                                public void onEnd() {
                                    tv_refresh_state.setText("下拉刷新");
                                    isInterceptTouch = false;
                                    recyclerView.setRefreshing(false);
                                    isInterceptScroll = false;
                                    REFRESH_STATUS = REFRESH_DONE;
                                    layoutAd(marginTop);
                                    iv_ad.setImageAlpha(0);
                                    if (onPullListener != null) onPullListener.onPull(255);
                                    ll_content.setPadding(0, paddingTop, 0, 0);
                                    reset();
                                }
                            });
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    void layoutAd(int marginTop) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_ad.getLayoutParams();
        layoutParams.topMargin = marginTop;
        iv_ad.setLayoutParams(layoutParams);
    }

    void reset() {
        offsetY = 0;
        scrollY = 0;
        adScrollDistance = 0;
        oldDistance = 0;
        maxOffsetY = 0;
    }

    /**
     * 刷新完成
     */
    public void refreshFinish() {
        AnimUtils.start(AD_START_SCROLL_DISTANCE, 100, new AnimUtils.OnAnimListener() {
            @Override
            public void onUpdate(int value) {
                ll_content.setPadding(0, paddingTop + AD_START_SCROLL_DISTANCE - value, 0, 0);
            }

            @Override
            public void onEnd() {
                tv_refresh_state.setText("下拉刷新");
                isInterceptTouch = false;
                isInterceptScroll = false;
                REFRESH_STATUS = REFRESH_DONE;
                recyclerView.setRefreshing(false);
                layoutAd(marginTop);
                iv_ad.setImageAlpha(0);
                if (onPullListener != null) onPullListener.onPull(255);
                ll_content.setPadding(0, paddingTop, 0, 0);
                enableTab();
            }
        });
    }

    /**
     * 禁止tab点击事件（正在刷新时禁止）
     */
    private void disableTab() {
        ((HomeNavigatorAdapter) ((CommonNavigator) magicIndicator.getNavigator()).getAdapter()).disable();
    }

    /**
     * 启用tab点击事件
     */
    private void enableTab() {
        ((HomeNavigatorAdapter) ((CommonNavigator) magicIndicator.getNavigator()).getAdapter()).enable();
    }

    OnPullListener onPullListener;

    public interface OnPullListener {
        void onPull(int alpha);

        void onRefresh();
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    public View getView() {
        return view;
    }

    public ImageView getIv_ad() {
        return iv_ad;
    }

    public LinearLayout getLl_content() {
        return ll_content;
    }

    public TextView getTv_refresh_state() {
        return tv_refresh_state;
    }

    public MagicIndicator getMagicIndicator() {
        return magicIndicator;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
