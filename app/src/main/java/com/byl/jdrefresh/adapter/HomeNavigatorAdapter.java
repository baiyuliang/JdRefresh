package com.byl.jdrefresh.adapter;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import com.byl.jdrefresh.utils.IndicatorUtils;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

/**
 * @Title :
 * @Author : BaiYuliang
 * @Date : 2020-08-22
 * @Desc :
 */
public class HomeNavigatorAdapter extends CommonNavigatorAdapter {

    List<String> listTitle;
    ViewPager mViewPager;
    boolean disable;

    public HomeNavigatorAdapter(List<String> listTitle, ViewPager mViewPager) {
        this.listTitle = listTitle;
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return listTitle == null ? 0 : listTitle.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = IndicatorUtils.getSimplePagerTitleView(context, listTitle.get(index), v -> {
            if (!disable) mViewPager.setCurrentItem(index);
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return IndicatorUtils.getLinePagerIndicator(context);
    }

    public void enable() {
        disable = false;
    }

    public void disable() {
        disable = true;
    }
}