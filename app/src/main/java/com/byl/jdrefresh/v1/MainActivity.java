package com.byl.jdrefresh.v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byl.jdrefresh.R;
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

    RelativeLayout rlTop;
    LinearLayout ll_search;
    TextView tv_search;
    ImageView iv_message;
    JdScrollView jdScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_search = findViewById(R.id.ll_search);
        rlTop = findViewById(R.id.rl_top);
        tv_search = findViewById(R.id.tv_search);
        iv_message = findViewById(R.id.iv_message);
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
                ll_search.getBackground().mutate().setAlpha(alpha);
                tv_search.setAlpha(alpha);
                iv_message.getDrawable().setAlpha(alpha);
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

        listTitle.add("首页");
        listFragments.add(HomePageFragment.getInstance());
        listTitle.add("手机");
        listFragments.add(OtherPageFragment.getInstance());
        listTitle.add("男装");
        listFragments.add(OtherPageFragment.getInstance());
        listTitle.add("电脑办公");
        listFragments.add(OtherPageFragment.getInstance());
        listTitle.add("生鲜");
        listFragments.add(OtherPageFragment.getInstance());
        listTitle.add("数码");
        listFragments.add(OtherPageFragment.getInstance());
        listTitle.add("医药健康");
        listFragments.add(OtherPageFragment.getInstance());

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