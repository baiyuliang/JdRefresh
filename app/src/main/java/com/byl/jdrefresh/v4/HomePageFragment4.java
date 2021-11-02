package com.byl.jdrefresh.v4;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.GridAdapter;
import com.byl.jdrefresh.adapter.ListAdapter3;
import com.byl.jdrefresh.fragment.BaseFragment;
import com.byl.jdrefresh.utils.StatusBarUtil;
import com.byl.jdrefresh.utils.SysUtils;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment4 extends BaseFragment {

    AdView adView;
    FrameLayout fl_bg_search;
    ImageView bg_search;

    RelativeLayout rl_search;
    TextView tv_search;
    TextView tv_qr;
    TextView tv_snap;
    RecyclerView mRecyclerViewGrid;
    RecyclerView mRecyclerView;
    AppBarLayout mAppBarLayout;

    int adViewHeight;//AdView高度
    int adHeight;//背景广告高度
    int bgSearchShowHeight;//搜索框背景图显示出来的高度
    int maxWidth;//搜索框最大宽度
    int scaleWidth;//搜索框缩放宽度
    int height;//搜索框高度（搜索框最大位移高度与本身高度一致，根据需求改变【注意：改变后需要保证整体逻辑一致性】）
    int x = 3;//位移系数
    int showSnapHeight;//显示出吸顶布局需要滑动的高度

    public static HomePageFragment4 getInstance() {
        HomePageFragment4 fragment = new HomePageFragment4();
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_page_home4;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        adViewHeight = SysUtils.getScreenWidth(mContext) * (146 + 6) / 150;//根据设计素材动态计算AdView高度(6为底部广告露出高度)
        adHeight = SysUtils.getScreenWidth(mContext) * 146 / 150;//根据设计素材动态计算广告背景图高度
        bgSearchShowHeight = StatusBarUtil.getStatusBarHeight(mContext) + SysUtils.Dp2Px(mContext, 15 + 30 + 15);
        maxWidth = SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 30);
        scaleWidth = SysUtils.Dp2Px(mContext, 40);
        height = SysUtils.Dp2Px(mContext, 30);
        showSnapHeight = adViewHeight + SysUtils.Dp2Px(mContext, 80 * 2 + 10) - bgSearchShowHeight;////根据滑动距离显示吸顶布局(自己计算好需要吸顶的布局离顶部的距离)

        adView = contentView.findViewById(R.id.adView);
        rl_search = contentView.findViewById(R.id.rl_search);
        tv_search = contentView.findViewById(R.id.tv_search);
        tv_qr = contentView.findViewById(R.id.tv_qr);
        fl_bg_search = contentView.findViewById(R.id.rl_bg_search);
        bg_search = contentView.findViewById(R.id.bg_search);
        tv_snap = contentView.findViewById(R.id.tv_snap);
        tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth, height));
        mRecyclerViewGrid = contentView.findViewById(R.id.mRecyclerViewGrid);
        mRecyclerView = contentView.findViewById(R.id.mRecyclerView);
        mAppBarLayout = contentView.findViewById(R.id.mAppBarLayout);

        StatusBarUtil.setPadding(mContext, rl_search);

        bg_search.setLayoutParams(new FrameLayout.LayoutParams(-1, adHeight));
        fl_bg_search.setLayoutParams(new RelativeLayout.LayoutParams(-1, StatusBarUtil.getStatusBarHeight(mContext) + SysUtils.Dp2Px(mContext, 55)));

        adView.setHeight(adViewHeight);
        adView.setAdLayoutParams(adHeight);
        int adBottomWidth = SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 30);
        adView.setAdBottomLayoutParams(adBottomWidth, adBottomWidth * 106 / 345);
        int bannerWidth = SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 25);
        int bannerHeight = bannerWidth * 142 / 350;
        adView.setBannerLayoutParams(bannerWidth, bannerHeight, adHeight * 124 / 366);

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int _scrollY = -verticalOffset;
            int scrollY = -verticalOffset / x;
            //搜索框动态上下位移（最大为height）,以及动态宽度
            if (scrollY <= height) {
                //滑动距离为0时重置为初始状态
                if (scrollY == 0) {
                    tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth, height));
                    tv_search.setTranslationY(height);
                } else {
                    tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth - scaleWidth * scrollY / height, height));
                    tv_search.setTranslationY(height - scrollY);
                }
                //扫码按钮改变
                if (scrollY < height - 15) {
                    tv_qr.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tv_qr.setTextColor(Color.parseColor("#666666"));
                }
            } else {
                tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth - scaleWidth, height));
                tv_search.setTranslationY(0);
                tv_qr.setTextColor(Color.parseColor("#666666"));
            }
            if (_scrollY >= showSnapHeight) {
                tv_snap.setVisibility(View.VISIBLE);
            } else {
                tv_snap.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initClick() {

    }

    @Override
    public void initData() {
        //在线图片若无法访问，可以使用mipmap中资源图片
        String adUrl = "https://oss.yanlu18.com/user/617d02df889ab579ee640895.png";
        String adBottomUrl = "https://oss.yanlu18.com/user/617d4c93889ad28d5b82b821.png";
        List<String> bannerUrls = new ArrayList<>();
        bannerUrls.add("https://oss.yanlu18.com/user/617d0330889ab579ee640896.png");
        bannerUrls.add("https://desk-fd.zol-img.com.cn/g5/M00/02/02/ChMkJ1bKxWCIDg_cAAIdQKY_M80AALHVAEDDcEAAh1Y513.jpg");
        bannerUrls.add("https://desk-fd.zol-img.com.cn/g2/M00/06/09/Cg-4WlV3sBSIBdowAAhqlCSiQS8AAE9-QE49UMACGqs413.jpg");

        Glide.with(mContext).load(adUrl).into(bg_search);
        adView.setData(adUrl, adBottomUrl, bannerUrls);

        List list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item" + i);
        }
        GridAdapter gridAdapter = new GridAdapter(mContext, list);
        GridLayoutManager myGridLayoutManager = new GridLayoutManager(mContext, 2);
        myGridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerViewGrid.setLayoutManager(myGridLayoutManager);
        mRecyclerViewGrid.setAdapter(gridAdapter);

        ListAdapter3 adapter = new ListAdapter3(mContext, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }

}
