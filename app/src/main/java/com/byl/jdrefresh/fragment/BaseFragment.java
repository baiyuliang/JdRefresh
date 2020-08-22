package com.byl.jdrefresh.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected Activity mContext;
    public View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutResId(), container, false);
            initView();
            initClick();
            initData();
        }
        return contentView;
    }

    public abstract int getLayoutResId();

    public abstract void initView();

    public abstract void initClick();

    public abstract void initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        contentView = null;
    }
}
