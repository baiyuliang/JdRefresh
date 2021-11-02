package com.byl.jdrefresh.adapter.base;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerHolder extends RecyclerView.ViewHolder {
    private static final int MAX_VIEW_INSTANCE = 20;
    private boolean once = true;
    private final SparseArray<View> mViews = new SparseArray(20);

    public RecyclerHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getView(int viewId) {
        View view = (View) this.mViews.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            this.mViews.put(viewId, view);
        }

        return (T) view;
    }

    public RecyclerHolder setText(int viewId, String text) {
        try {
            TextView view = (TextView) this.getView(viewId);
            view.setText(text);
        } catch (Exception e) {

        }
        return this;
    }

    public boolean isOnce() {
        if (this.once) {
            this.once = !this.once;
            return !this.once;
        } else {
            return this.once;
        }
    }

    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = (ImageView) this.getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public RecyclerHolder hideViewGone(int viewId) {
        this.getView(viewId).setVisibility(View.GONE);
        return this;
    }

    public RecyclerHolder hideViewInvisible(int viewId) {
        this.getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    public RecyclerHolder showView(int viewId) {
        this.getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    public RecyclerHolder setViewVisible(int viewId, boolean isVisible) {
        if (isVisible) {
            this.showView(viewId);
        } else {
            this.hideViewGone(viewId);
        }

        return this;
    }

    public RecyclerHolder setViewVisibleInvisible(int viewId, boolean isVisible) {
        if (isVisible) {
            this.showView(viewId);
        } else {
            this.hideViewInvisible(viewId);
        }

        return this;
    }

    public RecyclerHolder setClickListener(int viewId, View.OnClickListener onClickListener) {
        this.getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    public RecyclerHolder setEnabled(int viewId, boolean enabled) {
        this.getView(viewId).setEnabled(enabled);
        return this;
    }

    public RecyclerHolder setBackgroundResource(int viewId, int resId) {
        this.getView(viewId).setBackgroundResource(resId);
        return this;
    }
}
