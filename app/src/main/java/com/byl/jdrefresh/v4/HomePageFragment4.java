package com.byl.jdrefresh.v4;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.ListAdapter;
import com.byl.jdrefresh.adapter.ListAdapter2;
import com.byl.jdrefresh.fragment.BaseFragment;
import com.byl.jdrefresh.utils.StatusBarUtil;
import com.byl.jdrefresh.utils.SysUtils;
import com.byl.jdrefresh.v3.adapter.ListDelegateAdapter;
import com.byl.jdrefresh.v3.adapter.TopDelegateAdapter;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment4 extends BaseFragment {

    AdView adView;
    RelativeLayout rl_search;
    TextView tv_search;
    TextView tv_qr;
    ImageView bg_search;
    TextView tv_snap;
    RecyclerView mRecyclerView;
    AppBarLayout mAppBarLayout;

    int adHeight;//背景广告高度
    int bgSearchShowHeight;//搜索框背景图显示出来的高度
    int maxWidth;//搜索框最大宽度
    int scaleWidth;//搜索框缩放宽度
    int height;//搜索框高度（搜索框最大位移高度与本身高度一致，根据需求改变【注意：改变后需要保证整体逻辑一致性】）
    int x = 3;

    boolean isFirst = true;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        adHeight = SysUtils.Dp2Px(mContext, 350);
        bgSearchShowHeight = StatusBarUtil.getStatusBarHeight(mContext) + SysUtils.Dp2Px(mContext, 15 + 30 + 15);
        maxWidth = SysUtils.getScreenWidth(mContext) - SysUtils.Dp2Px(mContext, 30);
        scaleWidth = SysUtils.Dp2Px(mContext, 40);
        height = SysUtils.Dp2Px(mContext, 30);

        adView = contentView.findViewById(R.id.adView);
        rl_search = contentView.findViewById(R.id.rl_search);
        tv_search = contentView.findViewById(R.id.tv_search);
        tv_qr = contentView.findViewById(R.id.tv_qr);
        bg_search = contentView.findViewById(R.id.bg_search);
        tv_snap = contentView.findViewById(R.id.tv_snap);
        tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth, height));
        mRecyclerView = contentView.findViewById(R.id.mRecyclerView);
        mAppBarLayout = contentView.findViewById(R.id.mAppBarLayout);

        StatusBarUtil.setPadding(mContext, rl_search);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, adHeight);
        layoutParams.topMargin = -(adHeight - bgSearchShowHeight);
        bg_search.setLayoutParams(layoutParams);

        adView.setAdHeight(adHeight);
        adView.setBannerLayoutParams(SysUtils.Dp2Px(mContext, 40), SysUtils.Dp2Px(mContext, 120), SysUtils.Dp2Px(mContext, 120));

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
                bg_search.setVisibility(View.GONE);
            } else {
                tv_search.setLayoutParams(new RelativeLayout.LayoutParams(maxWidth - scaleWidth, height));
                tv_search.setTranslationY(0);
                tv_qr.setTextColor(Color.parseColor("#666666"));
            }
            //根据滑动距离显示搜索框背景图
            if (_scrollY >= adHeight - bgSearchShowHeight) {
                bg_search.setVisibility(View.VISIBLE);
            } else {
                bg_search.setVisibility(View.GONE);
            }
            //根据滑动距离显示吸顶布局
            if (_scrollY >= adHeight + SysUtils.Dp2Px(mContext, 10) - bgSearchShowHeight) {
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
        String adUrl = "https://desk-fd.zol-img.com.cn/g5/M00/02/02/ChMkJlbKxV-IJNtJABEuu9OIl3cAALHVAC99gAAES7T533.jpg";
        Glide.with(mContext).load(adUrl).into(bg_search);
        List<String> bannerUrls = new ArrayList<>();
        bannerUrls.add("https://desk-fd.zol-img.com.cn/g5/M00/02/02/ChMkJlbKxV-IJNtJABEuu9OIl3cAALHVAC99gAAES7T533.jpg");
        bannerUrls.add("https://desk-fd.zol-img.com.cn/g5/M00/02/02/ChMkJ1bKxWCIDg_cAAIdQKY_M80AALHVAEDDcEAAh1Y513.jpg");
        bannerUrls.add("https://desk-fd.zol-img.com.cn/g2/M00/06/09/Cg-4WlV3sBSIBdowAAhqlCSiQS8AAE9-QE49UMACGqs413.jpg");
        adView.setData(adUrl, bannerUrls);

        List list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item" + i);
        }
        ListAdapter2 adapter = new ListAdapter2(mContext, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
    }


}
