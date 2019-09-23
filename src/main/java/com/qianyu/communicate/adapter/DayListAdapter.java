package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.FansList;
import com.qianyu.communicate.utils.NumberUtils;

import com.qianyu.communicate.base.MyBaseAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class DayListAdapter extends MyBaseAdapter<FansList.SouvenirModelsBean, DayListAdapter.ViewHolder> {

    public DayListAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_rank_day_list, null));
    }

    @Override
    protected void onBindViewHolder_(ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            FansList.SouvenirModelsBean fansList = data.get(position);
            holder.mNameTv.setText(TextUtils.isEmpty(fansList.getNickName()) ? "" : fansList.getNickName());
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(fansList.getHeadPath()) ? "" : fansList.getHeadPath());
            holder.mMoneyTv.setText(NumberUtils.rounDouble(fansList.getTotalMoney()));
        }
        switch (position) {
            case 0:
                holder.mHeadImgBg.setVisibility(View.VISIBLE);
                holder.mHeadImgBg.setImageResource(R.mipmap.fensi_guanjun);
                holder.mNumTv.setBackground(context.getResources().getDrawable(R.mipmap.fensi_jinpai));
                holder.mNumTv.setText("");
                break;
            case 1:
                holder.mHeadImgBg.setVisibility(View.VISIBLE);
                holder.mHeadImgBg.setImageResource(R.mipmap.fensi_yajun);
                holder.mNumTv.setBackground(context.getResources().getDrawable(R.mipmap.fensi_yinpai));
                holder.mNumTv.setText("");
                break;
            case 2:
                holder.mHeadImgBg.setVisibility(View.VISIBLE);
                holder.mHeadImgBg.setImageResource(R.mipmap.fensi_jijun);
                holder.mNumTv.setBackground(context.getResources().getDrawable(R.mipmap.fensi_tongpai));
                holder.mNumTv.setText("");
                break;
            default:
                holder.mHeadImgBg.setVisibility(View.INVISIBLE);
                holder.mHeadImgBg.setImageResource(R.mipmap.fensi_guanjun);
                holder.mNumTv.setBackground(context.getResources().getDrawable(R.mipmap.fensi_putong));
                holder.mNumTv.setText("" + (position + 1));
                break;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mNumTv)
        TextView mNumTv;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.mLogoImg)
        ImageView mLogoImg;
        @InjectView(R.id.mHeadImgBg)
        ImageView mHeadImgBg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
