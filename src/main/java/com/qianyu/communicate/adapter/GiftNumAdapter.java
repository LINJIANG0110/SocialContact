package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wl_user on 2017/8/24.
 */

public class GiftNumAdapter extends MyBaseAdapter<GiftList.SouvenirNosBean, GiftNumAdapter.ViewHolder> {

    public GiftNumAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_pop_gift_num, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            GiftList.SouvenirNosBean souvenirNosBean = data.get(position);
            holder.mGiftNum.setText(souvenirNosBean.getQuantity()+"");
            holder.mGiftContent.setText(souvenirNosBean.getMeaning());
            if (position == data.size() - 1) {
                holder.mLineView.setVisibility(View.GONE);
            } else {
                holder.mLineView.setVisibility(View.VISIBLE);
            }
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.mGiftNum)
        TextView mGiftNum;
        @InjectView(R.id.mGiftContent)
        TextView mGiftContent;
        @InjectView(R.id.mLineView)
        View mLineView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
