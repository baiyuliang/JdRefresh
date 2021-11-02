package com.byl.jdrefresh.v3.adapter;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.base.BaseSingleDelegateAdapter;
import com.byl.jdrefresh.adapter.base.RecyclerHolder;
import com.byl.jdrefresh.utils.SysUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;


public class TopDelegateAdapter extends BaseSingleDelegateAdapter {

    String adUrl;
    List<String> bannerUrls;

    public TopDelegateAdapter(Context context) {
        super(context);
    }

    @Override
    public int createView() {
        return R.layout.item_delegate_top;
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, int position) {
        ImageView iv_ad = recyclerHolder.getView(R.id.iv_ad);
        Banner banner = recyclerHolder.getView(R.id.banner);

        if (banner.getAdapter() == null || banner.getAdapter().getItemCount() == 0) {
            Glide.with(getActivity())
                    .load(adUrl)
                    .into(iv_ad);
            //banner宽高根据设计动态设置
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(SysUtils.getScreenWidth(context) - SysUtils.Dp2Px(context, 40), SysUtils.Dp2Px(context, 120));
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            //显示的位置（距离顶部的距离，根据设计素材实现）
            layoutParams.topMargin = SysUtils.Dp2Px(context, 120);
            banner.setAdapter(new BannerImageAdapter<String>(bannerUrls) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    //图片加载自己实现
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                            .into(holder.imageView);
                }
            }).addBannerLifecycleObserver(getActivity()).setIndicator(new CircleIndicator(getActivity()));
        }

    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public void setBannerUrls(List<String> bannerUrls) {
        this.bannerUrls = bannerUrls;
    }
}
