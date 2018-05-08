package com.myjb.dev.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myjb.dev.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    class ViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        GeneralView itemView;

        public ViewHolder(GeneralView itemView) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        public void onBindData(int position, T data) {
            itemView.onBindData(position, data);
        }

        @Override
        public void onClick(View v) {
            Collections.reverse(list);
            notifyItemRangeChanged(0, list.size());
        }

        @Override
        public boolean onLongClick(View v) {
            list.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            return true;
        }
    }

    private Context context;
    private List<String> list;

    public RecyclerAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new CustomView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindData(position, list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        context = null;
        list.clear();
    }
}
