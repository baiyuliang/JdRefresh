package com.byl.jdrefresh.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


import net.lucode.hackware.magicindicator.buildins.UIUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @Title :
 * @Author : BaiYuliang
 * @Date : 2020-08-22
 * @Desc :
 */
public class IndicatorUtils {

    public static SimplePagerTitleView getSimplePagerTitleView(Context context, String title, View.OnClickListener onClickListener) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.parseColor("#FFFFFF"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#FFFFFF"));
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setText(title);
        int padding = UIUtil.dip2px(context, 7);
        simplePagerTitleView.setPadding(padding, 0, padding, 0);
        simplePagerTitleView.setOnClickListener(onClickListener);
        return simplePagerTitleView;
    }

    public static LinePagerIndicator getLinePagerIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setColors(Color.parseColor("#FFFFFF"));
        //指示器线条动画
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
        //指示器线条宽高及圆角弧度
        linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 3));
        linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 25));
        linePagerIndicator.setRoundRadius(3);
        return linePagerIndicator;
    }


}
