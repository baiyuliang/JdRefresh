package com.byl.jdrefresh.adapter.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseDelegateAdapter<T> extends DelegateAdapter.Adapter<RecyclerHolder> {
    private AppCompatActivity activity;
    private List<T> realDatas;
    private int startPage = 1;
    private int displayNumber = 20;
    private int currentPage;
    private LayoutHelper mLayoutHelper;


    public BaseDelegateAdapter(AppCompatActivity activity, LayoutHelper mLayoutHelper) {
        this.activity = activity;
        this.mLayoutHelper = mLayoutHelper;
    }


    public AppCompatActivity getActivity() {
        return activity;
    }

    public T getItem(int position) {
        return null != this.realDatas && this.realDatas.size() > position ? this.realDatas.get(position) : null;
    }


    public int getPage(boolean loadMore) {
        return loadMore ? this.nextPage() : this.startPage;
    }

    public int setpage(boolean loadMore) {
        return loadMore ? this.pagePlus() : this.resetPage();
    }

    public int getStartPage() {
        return this.startPage;
    }

    public int nextPage() {
        return this.currentPage + 1;
    }

    public int pagePlus() {
        return this.currentPage++;
    }

    public int resetPage() {
        this.currentPage = this.startPage;
        return this.currentPage;
    }

    public int getDisplayNumber() {
        return this.displayNumber;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public void setDisplayNumber(int displayNumber) {
        this.displayNumber = displayNumber;
    }

    public List<T> getRealDatas() {
        return this.realDatas;
    }


    public abstract View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int position);

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }


    @Override
    public int getItemCount() {
        return null != this.realDatas ? this.realDatas.size() : 0;
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.activity);
        View root = this.createView(inflater, viewGroup, viewType);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder recyclerHolder, int position) {
        if (null != this.realDatas && position < this.realDatas.size()) {
            this.convert(recyclerHolder, this.realDatas.get(position), position);
        } else {
            this.convert(recyclerHolder, (T) null, position);
        }
    }

    public abstract void convert(RecyclerHolder recyclerHolder, T t, int position);

    public void setDatas(Collection<T> datas) {
        this.setDatas(datas, false);
    }

    public Collection<T> getDatas() {
        return realDatas;
    }

    public void setDatas(Collection<T> datas, boolean loadMore) {
        if (loadMore) {
            if (null != datas && null != this.realDatas) {
                this.realDatas.addAll(datas);
            }
        } else {
            this.realDatas = new ArrayList(datas);
        }

        this.setpage(loadMore);
        this.notifyDataSetChanged();
    }


    public void addDatas(Collection<T> datas) {
        this.addDatas(datas.size(), datas);
    }

    public void addDatas(int index, Collection<T> datas) {
        if (null != datas && null != this.realDatas) {
            this.realDatas.addAll(index, datas);
            this.notifyDataSetChanged();
        }

    }

    public void addData(T data) {
        if (null != data && null != this.realDatas) {
            this.addData(this.realDatas.size(), data);
        }

    }

    public void addData(int index, T data) {
        if (null != data && null != this.realDatas) {
            this.realDatas.add(index, data);
            this.notifyItemInserted(index);
        }

    }

    public void removeData(int position) {
        if (null != this.realDatas && this.realDatas.size() > position) {
            this.realDatas.remove(position);
            this.notifyItemRemoved(position);
        }

    }

    public void moveData(int fromPosition, int toPosition) {
        Collections.swap(this.realDatas, fromPosition, toPosition);
        this.notifyItemMoved(fromPosition, toPosition);
    }

    public void updataItem(int position, T data) {
        if (null != data && null != this.realDatas && this.realDatas.size() > position) {
            this.realDatas.set(position, data);
            this.notifyItemChanged(position);
        }

    }

}
