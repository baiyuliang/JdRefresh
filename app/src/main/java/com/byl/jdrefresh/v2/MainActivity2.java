package com.byl.jdrefresh.v2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.byl.jdrefresh.v1.CustomViewPager;
import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.HomeFragmentPageAdapter;
import com.byl.jdrefresh.adapter.HomeNavigatorAdapter;
import com.byl.jdrefresh.fragment.OtherPageFragment;
import com.byl.jdrefresh.utils.StatusBarUtil;
import com.byl.jdrefresh.utils.SysUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    RelativeLayout rlTop;
    LinearLayout ll_search;
    TextView tv_search;
    ImageView iv_message;

    LinearLayout ll_content;
    MagicIndicator magicIndicator;
    public CustomViewPager customViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ll_search = findViewById(R.id.ll_search);
        rlTop = findViewById(R.id.rl_top);
        tv_search = findViewById(R.id.tv_search);
        iv_message = findViewById(R.id.iv_message);
        ll_content = findViewById(R.id.ll_content);
        magicIndicator = findViewById(R.id.magicIndicator);
        customViewPager = findViewById(R.id.customViewPager);

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPadding(this, rlTop);

        ll_search.getBackground().mutate().setAlpha(180);

        initData();
    }

    private void initData() {
        List<String> listTitle = new ArrayList<>();
        List<Fragment> listFragments = new ArrayList<>();

        listTitle.add("首页");
        listFragments.add(HomePageFragment2.getInstance());
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
        HomeNavigatorAdapter commonNavigatorAdapter = new HomeNavigatorAdapter(listTitle, customViewPager);
        commonNavigator.setAdapter(commonNavigatorAdapter);
        commonNavigator.setLeftPadding(UIUtil.dip2px(this, 10));
        commonNavigator.setRightPadding(UIUtil.dip2px(this, 15));
        magicIndicator.setNavigator(commonNavigator);

        HomeFragmentPageAdapter homeFragmentPageAdapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), listFragments);
        customViewPager.setAdapter(homeFragmentPageAdapter);
        customViewPager.setOffscreenPageLimit(listFragments.size() - 1);
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
                if (position == 0) {
                    magicIndicator.setVisibility(View.GONE);
                    ll_content.setPadding(0, 0, 0, 0);
                } else {
                    magicIndicator.setVisibility(View.VISIBLE);
                    ll_content.setPadding(0, SysUtils.Dp2Px(MainActivity2.this, 40) + StatusBarUtil.getStatusBarHeight(MainActivity2.this), 0, 0);
                }
            }

            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    public void setAlpha(int alpha) {
        rlTop.getBackground().mutate().setAlpha(alpha < 255 ? 0 : 255);
        ll_search.getBackground().mutate().setAlpha(alpha);
        tv_search.setAlpha(alpha);
        iv_message.getDrawable().setAlpha(alpha);
    }
}