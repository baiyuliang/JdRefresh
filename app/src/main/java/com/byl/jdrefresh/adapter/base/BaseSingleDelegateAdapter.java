package com.byl.jdrefresh.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;

/**
 * @Title 单一模块
 * @Author Bai Yuliang
 * @Desc
 * @Date ${DATE} ${TIME}
 */
public abstract class BaseSingleDelegateAdapter extends BaseDelegateAdapter<BaseSingleDelegateAdapter.EmptyModel> {

    public Context context;

    public BaseSingleDelegateAdapter(Context context) {
        super((AppCompatActivity) context, new LinearLayoutHelper());
        this.context = context;
        ArrayList list = new ArrayList();
        list.add(new EmptyModel());
        setDatas(list);
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int position) {
        return layoutInflater.inflate(createView(), viewGroup, false);
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, EmptyModel emptyModel, int position) {
        convert(recyclerHolder, position);
    }

    public abstract int createView();

    public abstract void convert(RecyclerHolder recyclerHolder, int position);

    static class EmptyModel {
    }
}
