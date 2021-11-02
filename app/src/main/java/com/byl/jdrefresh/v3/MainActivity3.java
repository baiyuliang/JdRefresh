package com.byl.jdrefresh.v3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.HomeFragmentPageAdapter;
import com.byl.jdrefresh.adapter.HomeNavigatorAdapter;
import com.byl.jdrefresh.fragment.OtherPageFragment;
import com.byl.jdrefresh.utils.StatusBarUtil;
import com.byl.jdrefresh.utils.SysUtils;
import com.byl.jdrefresh.v1.CustomViewPager;
import com.byl.jdrefresh.v2.HomePageFragment2;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        viewPager = findViewById(R.id.customViewPager);

        StatusBarUtil.immersive(this);

        initData();
    }

    private void initData() {
        List<String> listTitle = new ArrayList<>();
        List<Fragment> listFragments = new ArrayList<>();

        listTitle.add("首页");
        listFragments.add(HomePageFragment3.getInstance());

        HomeFragmentPageAdapter homeFragmentPageAdapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(homeFragmentPageAdapter);

    }

}