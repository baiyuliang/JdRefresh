package com.byl.jdrefresh.v2;

import android.os.Handler;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.HomeNavigatorAdapter;
import com.byl.jdrefresh.adapter.ListAdapter;
import com.byl.jdrefresh.fragment.BaseFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment2 extends BaseFragment {

    JdScrollView2 jdScrollView;

    public static HomePageFragment2 getInstance() {
        HomePageFragment2 fragment = new HomePageFragment2();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_home2;
    }

    @Override
    public void initView() {
        jdScrollView = contentView.findViewById(R.id.jdScrollView);
        jdScrollView.setOnPullListener(new JdScrollView2.OnPullListener() {
            @Override
            public void onPull(int alpha) {
                ((MainActivity2) mContext).setAlpha(alpha);
            }

            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
                    jdScrollView.refreshFinish();
                }, 1500);
            }
        });

        List<String> listTitle = new ArrayList<>();
        listTitle.add("首页");
        listTitle.add("手机");
        listTitle.add("男装");
        listTitle.add("电脑办公");
        listTitle.add("生鲜");
        listTitle.add("数码");
        listTitle.add("医药健康");
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        HomeNavigatorAdapter commonNavigatorAdapter = new HomeNavigatorAdapter(listTitle, ((MainActivity2) mContext).customViewPager);
        commonNavigator.setAdapter(commonNavigatorAdapter);
        commonNavigator.setLeftPadding(UIUtil.dip2px(mContext, 10));
        commonNavigator.setRightPadding(UIUtil.dip2px(mContext, 15));
        jdScrollView.getMagicIndicator().setNavigator(commonNavigator);
        ViewPagerHelper.bind(jdScrollView.getMagicIndicator(), ((MainActivity2) mContext).customViewPager);
    }

    @Override
    public void initClick() {

    }

    @Override
    public void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item" + i);
        }
        ListAdapter adapter = new ListAdapter(mContext, list);
        jdScrollView.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        jdScrollView.getRecyclerView().setAdapter(adapter);
    }


}
