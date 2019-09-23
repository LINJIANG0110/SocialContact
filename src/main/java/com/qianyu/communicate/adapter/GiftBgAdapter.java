package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.GiftList;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.UserInfo;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class GiftBgAdapter extends MyBaseAdapter<UserInfo.GiftListBean, GiftBgAdapter.ViewHolder> {


    public GiftBgAdapter(Activity context, List data) {
        super(context, data);
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_gift_bg, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            UserInfo.GiftListBean giftList = data.get(position);
            ImageUtil.loadPicNet(giftList.getGift_url(), holder.mHeadImg);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        ImageView mHeadImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
