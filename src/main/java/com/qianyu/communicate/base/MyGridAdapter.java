package com.qianyu.communicate.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyj on 2016/9/28.
 */

public abstract class MyGridAdapter<T> extends BaseAdapter {
    public Context context;
    public List<T> data = new ArrayList<>();
    private int layoutid;

    public MyGridAdapter(Context context, List<T> data, int layoutid) {
        super();
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        this.layoutid = layoutid;
    }

    public void clear() {
        if (data != null) {
            this.data.clear();
            notifyDataSetChanged();
        }
    }

    public void append(List<T> list) {
        if (this.data != null) {
            this.data.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //return data.size();
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHoder vh = BaseViewHoder.getViewHolder(convertView, context, parent, layoutid, position);
        if (data.size() > 0) {
            convert(position,vh, data.get(position));
        } else {
            convert(position,vh, null);
        }
        return vh.getConvertView();
    }

    protected abstract void convert(int position,BaseViewHoder vh, T item);
    
}
