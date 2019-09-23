package com.qianyu.communicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.ImpressLabel;

import net.neiquan.applibrary.flowtag.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter_<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    public TagAdapter_(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_impress_tag, null);
        final TextView textView = view.findViewById(R.id.mTagTv);
        if (mDataList != null && mDataList.size() > 0) {
            String label = (String) mDataList.get(position);
            textView.setText(TextUtils.isEmpty(label) ? "" : label);
//            if(position==mDataList.size()-1){
//                textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_cornor_dashed));
//            }
        }
        return view;
    }


    public void onlyAddAll(List<T> datas) {
        if (datas != null) {
            mDataList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        return position % 2 == 0;
    }
}
