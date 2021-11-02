package com.byl.jdrefresh.v4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.byl.jdrefresh.databinding.ViewAdBinding;
import com.byl.jdrefresh.utils.SysUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;


public class AdView extends FrameLayout {

    AppCompatActivity context;
    ViewAdBinding vb;

    public AdView(@NonNull Context context) {
        super(context);
        this.context = (AppCompatActivity) context;
    }

    public AdView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = (AppCompatActivity) context;
    }

    public AdView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = (AppCompatActivity) context;
    }

    {
        vb = ViewAdBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void setData(String adUrl, List<String> bannerUrls) {
        Glide.with(context)
                .load(adUrl)
                .into(vb.ivAd);
        vb.banner.setAdapter(new BannerImageAdapter<String>(bannerUrls) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(context).setIndicator(new CircleIndicator(context));
    }

    //根据设计素材尺寸动态设置
    public void setAdHeight(int height) {
        vb.flAd.setLayoutParams(new LinearLayout.LayoutParams(-1, height));
    }

    //banner宽高根据设计素材尺寸动态设置
    public void setBannerLayoutParams(int horizontalMargin, int height, int topMargin) {
        LayoutParams layoutParams = new LayoutParams(SysUtils.getScreenWidth(context) - horizontalMargin, height);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        //显示的位置（距离顶部的距离，根据设计素材实现）
        layoutParams.topMargin = topMargin;
        vb.banner.setLayoutParams(layoutParams);
    }


}
