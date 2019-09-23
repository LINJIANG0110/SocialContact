package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.RechargeList;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class HotMallAdapter extends MyBaseAdapter<RechargeList, HotMallAdapter.ViewHolder> {


    public HotMallAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_hot_mall, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final RechargeList rechargesBean = data.get(position);
            holder.mMallName.setText(TextUtils.isEmpty(rechargesBean.getProductName()) ? "" : rechargesBean.getProductName());
            holder.mMallDetail.setText(TextUtils.isEmpty(rechargesBean.getProductDescribe()) ? "" : rechargesBean.getProductDescribe());
            holder.mHotLogo.setVisibility(rechargesBean.getIsHot()==1?View.VISIBLE:View.GONE);
            switch (rechargesBean.getProductType()) {
                case 1:
                    holder.mDiamondLogo.setVisibility(View.GONE);
                    holder.mMallImg.setImageResource(R.mipmap.diamond1);
                    holder.mMallMoney.setTextColor(context.getResources().getColor(R.color.colorRed));
                    holder.mMallMoney.setText("￥" + rechargesBean.getProductPrice());
                    break;
                case 2:
                    holder.mDiamondLogo.setVisibility(View.VISIBLE);
                    holder.mMallMoney.setTextColor(context.getResources().getColor(R.color.oval_purple));
                    holder.mMallMoney.setText(rechargesBean.getProductPrice()+"");
                    ImageUtil.loadPicNet(rechargesBean.getPicPath(),holder.mMallImg);
                    break;
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mMallImg)
        ImageView mMallImg;
        @InjectView(R.id.mMallName)
        TextView mMallName;
        @InjectView(R.id.mMallDetail)
        TextView mMallDetail;
        @InjectView(R.id.mDiamondLogo)
        ImageView mDiamondLogo;
        @InjectView(R.id.mMallMoney)
        TextView mMallMoney;
        @InjectView(R.id.mHotLogo)
        ImageView mHotLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
