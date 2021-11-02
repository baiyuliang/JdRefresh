package com.byl.jdrefresh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.byl.jdrefresh.R;

import java.util.List;

public class ListAdapter3 extends RecyclerView.Adapter<ListAdapter3.MyViewHolder> {

    Activity context;
    List<String> listData;

    public ListAdapter3(Activity context, List<String> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListAdapter3.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String content = listData.get(position);
        holder.tv_content.setText(content);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
