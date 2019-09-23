package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.RechargeList;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class WalletAdapter extends MyBaseAdapter<RechargeList, WalletAdapter.ViewHolder> {


    public WalletAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_wallet, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final RechargeList rechargesBean = data.get(position);
            holder.mCoinNum.setText(TextUtils.isEmpty(rechargesBean.getProductName())?"":rechargesBean.getProductName());
            holder.mCoinPay.setText("售价" + rechargesBean.getProductPrice() + "元");
            holder.walletLL.setBackground(context.getResources().getDrawable(rechargesBean.isSelected() ? R.drawable.shape_cornor_line : R.drawable.shape_cornor_white));
            holder.chosenLogo.setVisibility(rechargesBean.isSelected() ? View.VISIBLE : View.GONE);
            holder.mCoinEffect.setText(TextUtils.isEmpty(rechargesBean.getProductDescribe())?"":rechargesBean.getProductDescribe());
//            holder.walletLL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    for (int i = 0; i < data.size(); i++) {
//                        data.get(i).setSelected(false);
//                    }
//                    data.get(position).setSelected(true);
//                    notifyDataSetChanged();
//                    holder.walletLL.setBackground(context.getResources().getDrawable(rechargesBean.isSelected()?R.drawable.shape_cornor_line:R.drawable.shape_cornor_white));
//                    holder.chosenLogo.setVisibility(rechargesBean.isSelected()?View.VISIBLE:View.GONE);
//                }
//            });
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.walletLL)
        LinearLayout walletLL;
        @InjectView(R.id.mCoinNum)
        TextView mCoinNum;
        @InjectView(R.id.mCoinPay)
        TextView mCoinPay;
        @InjectView(R.id.mCoinEffect)
        TextView mCoinEffect;
        @InjectView(R.id.chosenLogo)
        ImageView chosenLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
