package com.byl.jdrefresh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.byl.jdrefresh.adapter.HomeFragmentPageAdapter;
import com.byl.jdrefresh.adapter.HomeNavigatorAdapter;
import com.byl.jdrefresh.fragment.HomePageFragment;
import com.byl.jdrefresh.fragment.OtherPageFragment;
import com.byl.jdrefresh.utils.StatusBarUtil;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_search;
    RelativeLayout rlTop;
    JdScrollView jdScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_search = findViewById(R.id.ll_search);
        rlTop = findViewById(R.id.rl_top);
        jdScrollView = findViewById(R.id.jdScrollView);

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPadding(this, rlTop);

        ll_search.getBackground().mutate().setAlpha(180);

//        jdScrollView.getView().setBackgroundColor();//改变底层背景颜色
//        jdScrollView.getIv_ad().setImageResource();//改变广告图

        jdScrollView.setOnPullListener(new JdScrollView.OnPullListener() {
            @Override
            public void onPull(int alpha) {
                rlTop.getBackground().mutate().setAlpha(alpha);
            }

            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    Toast.makeText(MainActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                    jdScrollView.refreshFinish();
                }, 1500);
            }
        });

        initData();
    }

    private void initData() {
        List<String> listTitle = new ArrayList<>();
        List<Fragment> listFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listTitle.add("Tab" + (i + 1));
            if (i == 0)
                listFragments.add(HomePageFragment.getInstance());
            else
                listFragments.add(OtherPageFragment.getInstance());
        }
        CommonNavigator commonNavigator = new CommonNavigator(this);
        HomeNavigatorAdapter commonNavigatorAdapter = new HomeNavigatorAdapter(listTitle, jdScrollView.getViewPager());
        commonNavigator.setAdapter(commonNavigatorAdapter);
        commonNavigator.setLeftPadding(UIUtil.dip2px(this, 10));
        commonNavigator.setRightPadding(UIUtil.dip2px(this, 15));
        jdScrollView.getMagicIndicator().setNavigator(commonNavigator);

        ViewPagerHelper.bind(jdScrollView.getMagicIndicator(), jdScrollView.getViewPager());

        HomeFragmentPageAdapter homeFragmentPageAdapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), listFragments);
        jdScrollView.getViewPager().setAdapter(homeFragmentPageAdapter);
        jdScrollView.getViewPager().setOffscreenPageLimit(listFragments.size() - 1);
    }
}