package com.qianyu.communicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.RedPackageDelBean;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

/**
 * Created by JavaDog on 2019-8-22.
 * 红包获取记录适配器
 */

public class RPUserRecordApdater extends BaseAdapter {

    private Context context;
    private List<RedPackageDelBean.UserRecordBean> recordData;
    private LayoutInflater mInflater;

    public RPUserRecordApdater(Context context, List<RedPackageDelBean.UserRecordBean> recordData) {
        this.context = context;
        this.recordData = recordData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // +1 最后一个为删除按钮
        return recordData.size();
    }

    @Override
    public Object getItem(int position) {
        return recordData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RPUserRecordApdater.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_redpackage_record, parent, false);
            holder = new RPUserRecordApdater.ViewHolder(convertView);
        } else {
            holder = (RPUserRecordApdater.ViewHolder) convertView.getTag();
        }
        holder.bindData((RedPackageDelBean.UserRecordBean) getItem(position));
        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView ivUserHead;
        TextView tvUsername, tvTime, tvIdiom, tvResmoney;

        ViewHolder(View view) {
            ivUserHead = (SimpleDraweeView) view.findViewById(R.id.iv_userhead);
            tvUsername = (TextView) view.findViewById(R.id.tv_username);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvIdiom = (TextView) view.findViewById(R.id.tv_idiom);
            tvResmoney = (TextView) view.findViewById(R.id.tv_resmoney);
            view.setTag(this);
        }

        void bindData(RedPackageDelBean.UserRecordBean data) {
            tvUsername.setText(data.nickName);
            tvTime.setText(TimeUtils.getHourAndMin(data.createTime));
            tvIdiom.setText(data.word);
            tvResmoney.setText(data.diamond);
            ivUserHead.setImageURI(TextUtils.isEmpty(data.headUrl) ? "" : data.headUrl);
        }
    }
}