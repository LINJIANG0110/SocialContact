package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.InComeList;
import com.qianyu.communicate.entity.MallBillList;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MallFuBowAdapter extends MyBaseAdapter<MallBillList.PageListBean, MallFuBowAdapter.ViewHolder> {

    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public MallFuBowAdapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_mall_fubow, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            MallBillList.PageListBean pageListBean = data.get(position);
            holder.mChargeLL.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
            holder.mExpanseLL.setVisibility(type == 1 ? View.GONE : View.VISIBLE);
            holder.titleName.setText(TextUtils.isEmpty(pageListBean.getAccNickName()) ? "" : pageListBean.getAccNickName());
            holder.createTime.setText(TimeUtils.getTime(pageListBean.getCreateTime()));
            String diamond = (1 == type ? "+" : "-") + (NumberUtils.rounLong(pageListBean.getDiamond()) + "钻石,");
            String fubao = (1 == type ? "+" : "-") + (NumberUtils.rounLong(pageListBean.getFubao()) + "福宝,");
            String gold = (1 == type ? "+" : "-") + (NumberUtils.rounLong(pageListBean.getGold()) + "金币");
            holder.costNum.setText(diamond + fubao + gold);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(pageListBean.getAccHeadUrl()) ? "" : pageListBean.getAccHeadUrl());
            holder.mNickNameTv.setText(TextUtils.isEmpty(pageListBean.getAccNickName()) ? "" : pageListBean.getAccNickName());
            holder.mIDNumTv.setText("ID:" + pageListBean.getAccUserId());
            holder.mTimeTv.setText(TimeUtils.getTime(pageListBean.getCreateTime()));
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mChargeLL)
        LinearLayout mChargeLL;
        @InjectView(R.id.mExpanseLL)
        LinearLayout mExpanseLL;
        @InjectView(R.id.titleName)
        TextView titleName;
        @InjectView(R.id.createTime)
        TextView createTime;
        @InjectView(R.id.costNum)
        TextView costNum;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNickNameTv)
        TextView mNickNameTv;
        @InjectView(R.id.mIDNumTv)
        TextView mIDNumTv;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
