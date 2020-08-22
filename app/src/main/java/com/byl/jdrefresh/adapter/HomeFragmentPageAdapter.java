package com.byl.jdrefresh.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @Title
 * @Author Bai Yuliang
 * @Desc
 * @Date 2020-08-22
 */
public class HomeFragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public HomeFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
