package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.GiftList;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.utils.ImageUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class GiftFuBowAdapter extends MyBaseAdapter<GiftList.ContentBean, GiftFuBowAdapter.ViewHolder> {


    public GiftFuBowAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_gift_fubow, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            GiftList.ContentBean goodSBean = data.get(position);
//            ImageUtil.loadPicNet(goodSBean.getGiftUrl(), holder.imageView);
            Uri uri = Uri.parse(TextUtils.isEmpty(goodSBean.getGiftUrl()) ? "" : goodSBean.getGiftUrl());
            final DraweeController draweeController =
                    Fresco.newDraweeControllerBuilder()
                            .setUri(uri)
                            .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                            .build();
            holder.imageView.setController(draweeController);
            holder.textView.setText(goodSBean.getGiftName());
            holder.mMoneyTv.setText(goodSBean.getGiftPrice() + "");
            holder.costLogo.setImageResource(goodSBean.getGiftType() == 1 ? R.mipmap.wallet1 : (goodSBean.getGiftType() == 2 ? R.mipmap.coin1 : R.mipmap.diamond1));
            holder.giftBeautfyLL.setVisibility((goodSBean.getGiftType() == 0 || goodSBean.getGiftType() == 3) ? View.VISIBLE : View.INVISIBLE);
            holder.giftBeautfyAddLL.setVisibility((goodSBean.getGiftType() == 0 || goodSBean.getGiftType() == 3) ? View.VISIBLE : View.INVISIBLE);
            holder.giftExprience.setText("+" + goodSBean.getAcceptExperience());
            holder.giftBeautfy.setText("+" + goodSBean.getAcceptCharm());
            holder.giftBeautfyAdd.setText("+" + goodSBean.getAcceptCharmSpecial() * 100 + "%");
            holder.chosenLogo.setVisibility(goodSBean.isSelected() ? View.VISIBLE : View.GONE);
            holder.giftChooseFL.setBackground(context.getResources().getDrawable(goodSBean.isSelected() ? R.drawable.shape_wrap_line_app_color : R.drawable.shape_wrap_line_gray));
//            holder.giftChooseFL.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    holder.giftChooseFL.setBackground(context.getResources().getDrawable(R.drawable.shape_wrap_line_app_color));
////                context.startActivity(new Intent(context, GiftDetailActivity.class));
//                }
//            });
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.chosenLogo)
        ImageView chosenLogo;
        @InjectView(R.id.imageView)
        SimpleDraweeView imageView;
        @InjectView(R.id.costLogo)
        ImageView costLogo;
        @InjectView(R.id.textView)
        TextView textView;
        @InjectView(R.id.giftExprience)
        TextView giftExprience;
        @InjectView(R.id.giftBeautfyLL)
        LinearLayout giftBeautfyLL;
        @InjectView(R.id.giftBeautfyAddLL)
        LinearLayout giftBeautfyAddLL;
        @InjectView(R.id.giftBeautfy)
        TextView giftBeautfy;
        @InjectView(R.id.giftBeautfyAdd)
        TextView giftBeautfyAdd;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        @InjectView(R.id.item_layout)
        FrameLayout giftChooseFL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
