package com.byl.jdrefresh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.byl.jdrefresh.R;

import java.util.List;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.MyViewHolder> {

    Activity context;
    List<String> listData;

    public ListAdapter2(Activity context, List<String> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListAdapter2.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String content = listData.get(position);
        if (position == 0) {
            holder.tv_snap.setVisibility(View.VISIBLE);
            holder.tv_content.setVisibility(View.GONE);
        } else {
            holder.tv_snap.setVisibility(View.GONE);
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.tv_content.setText(content);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_snap;
        TextView tv_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_snap = itemView.findViewById(R.id.tv_snap);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
