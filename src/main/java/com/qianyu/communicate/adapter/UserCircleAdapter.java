package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.GiftList;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class UserCircleAdapter extends MyBaseAdapter<GiftList.ContentBean, UserCircleAdapter.ViewHolder> {


    public UserCircleAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_user_circle, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
//        if (data != null && data.size() > 0) {
//            GiftList.GoodSBean giftList = data.get(position);
//            ImageUtil.loadPicNet(giftList.getGoodPath(), holder.mHeadImg);
//        }
        ImageUtil.loadHeadImgNetCorner("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg", holder.mHeadImg);
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
