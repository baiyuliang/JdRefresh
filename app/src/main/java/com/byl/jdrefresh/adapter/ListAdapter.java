package com.byl.jdrefresh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.byl.jdrefresh.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    Activity context;
    List<String> listData;

    public ListAdapter(Activity context, List<String> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String content = listData.get(position);
        holder.tv_content.setText(content);
        holder.ll_content.setOnClickListener(v -> {
            Toast.makeText(context, "点击了>>" + content, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_content;
        TextView tv_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_content = itemView.findViewById(R.id.ll_content);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
