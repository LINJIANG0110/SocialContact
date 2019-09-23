package com.qianyu.communicate.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class MyMBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends XRecyclerView.Adapter<VH> {
    public Activity context;
    public List<T> data = new ArrayList<>();
    private ViewGroup parent;
    public LayoutInflater mInflater;
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public OnRecyclerViewItemLongClickListener onItemLongClickListener = null;

    public MyMBaseAdapter(Activity context, List<T> data) {
        super();
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        mInflater = LayoutInflater.from(context);
//        mInflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, int viewType) {
        VH viewHolder = getViewHolder(parent,viewType);
        this.parent = parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(parent, data, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(parent, data, position);
                }
                return true;
            }
        });
        onBindViewHolder_(holder, position);
    }


    protected abstract VH getViewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindViewHolder_(VH holder, int position);

    public void clear() {
        if (data != null) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }

    public void appendSingle(T data) {
        if (this.data != null) {
            this.data.add(data);
        }
        notifyDataSetChanged();
    }

    public void removeSingle(T data) {
        if (this.data != null) {
            this.data.remove(data);
        }
        notifyDataSetChanged();
    }

    public void removeSingle(int p) {
        if (this.data != null) {
            this.data.remove(p);
        }
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (this.data != null) {
            this.data.clear();
        }
        notifyDataSetChanged();
    }

    public void append(List<T> list) {
        if (this.data != null && list != null) {
            this.data.addAll(list);
        }
        notifyDataSetChanged();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    @Override
    public int getItemCount() {
//        if (data.size() > 0) {
        return data.size();
//        } else {
//            return 4;
//        }
    }

    public void appendAll(List<T> list) {
        if (this.data != null && list != null) {
            this.data.clear();
            this.data.addAll(list);
        }
        notifyDataSetChanged();
    }

    //define interface
    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, List<T> data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }  //define interface

    public interface OnRecyclerViewItemLongClickListener<T> {
        void onItemLongClick(View view, List<T> data, int position);
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }
}
