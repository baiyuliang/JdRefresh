package com.byl.jdrefresh.v3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.vlayout.LayoutHelper;
import com.byl.jdrefresh.R;
import com.byl.jdrefresh.adapter.base.BaseDelegateAdapter;
import com.byl.jdrefresh.adapter.base.RecyclerHolder;


public class ListDelegateAdapter extends BaseDelegateAdapter<String> {


    public ListDelegateAdapter(Context context, LayoutHelper mLayoutHelper) {
        super((AppCompatActivity) context, mLayoutHelper);
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int position) {
        return layoutInflater.inflate(R.layout.item_list2, viewGroup, false);
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, String content, int position) {
        TextView tv_snap = recyclerHolder.getView(R.id.tv_snap);
        tv_snap.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
    }

}
