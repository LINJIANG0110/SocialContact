package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.MediaList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.jzvd.JZVideoPlayer;
import com.qianyu.communicate.jzvd.JZVideoPlayerStandard;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.utils.TimeUtils;

import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.utils.PixelUtil;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class PersonalVideoAdapter extends MyBaseAdapter<MediaList, PersonalVideoAdapter.ViewHolder> {


    public PersonalVideoAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_personal_video, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = PixelUtil.getScreenWidth(context) - PixelUtil.dp2px(context, 20);
        layoutParams.height = layoutParams.width * 9 / 16;
        holder.mHeadImg.setLayoutParams(layoutParams);
        if (data != null && data.size() > 0) {
            final MediaList mediaList = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(mediaList.getMediaPic()) ? "" : mediaList.getMediaPic());
            holder.mTitleName.setText(TextUtils.isEmpty(mediaList.getMediaName()) ? "" : mediaList.getMediaName());
            holder.mPlayCount.setText(NumberUtils.roundInt(mediaList.getTotalPerson()));
            holder.mTimeTv.setText(TimeUtils.getTime(mediaList.getCreateTime()));
            holder.mTotalTime.setText("时长" + TimeUtils.getHourAndMin(mediaList.getTotalTime()));
            holder.playLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpringUtils.springAnim(holder.playLogo);
                    addWatchHistory(mediaList.getMediaId());
//                    Intent intent = new Intent(context, JzFullActivity.class);
//                    intent.putExtra("url", mediaList.getMediaPath());
//                    intent.putExtra("name",mediaList.getMediaName());
//                    context.startActivity(intent);
                    JZVideoPlayerStandard.startFullscreen(context, JZVideoPlayerStandard.class, mediaList.getMediaPath(), mediaList.getMediaName());
                }
            });
            String freeType = mediaList.getFreeType();
            if (!TextUtils.isEmpty(freeType)) {
                switch (freeType) {
                    case "1":
                        holder.mMoneyTv.setText("免费");
                        break;
                    default:
                        holder.mMoneyTv.setText("￥" + mediaList.getPrice());
                        break;
                }
            } else {
                holder.mMoneyTv.setText("免费");
            }
            holder.jzVideo.setUp(mediaList.getMediaPath(), JZVideoPlayerStandard.SCREEN_WINDOW_LIST, TextUtils.isEmpty(mediaList.getMediaName())?"":mediaList.getMediaName());
            if (TextUtils.isEmpty(mediaList.getMediaPic())) {
                Glide.with(holder.jzVideo.getContext()).load(R.drawable.video_bg).into(holder.jzVideo.thumbImageView);
            } else {
                Glide.with(holder.jzVideo.getContext()).load(mediaList.getMediaPic()).into(holder.jzVideo.thumbImageView);
            }
            holder.jzVideo.setOnStartVideoListener(new JZVideoPlayer.OnStartVideoListener() {
                @Override
                public void onStartVideo(boolean free) {
                    if (free) {
                        addWatchHistory(mediaList.getMediaId());
                    } else {
                        showMoneyPopWindow(mediaList, holder.mHeadImg);
                    }
                }
            }, mediaList);
        }
    }

    private void showMoneyPopWindow(final MediaList mediaList, SimpleDraweeView mHeadImg) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.money_pay_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_pushUp)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        CardView payCardView = (CardView) view.findViewById(R.id.payCardView);
                        TextView mMoneyTv = (TextView) view.findViewById(R.id.mMoneyTv);
                        mMoneyTv.setText("￥" + mediaList.getPrice());
                        payCardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                wxPay(mediaList);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mHeadImg, Gravity.BOTTOM, 0, 0);
    }

    private void wxPay(MediaList mediaList) {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            context.startActivity(new Intent(context, RegistActivity.class));
            return;
        }
//        NetUtils.getInstance().wxPay(0, Tools.getIPAddress(context), mediaList.getMediaId(), mediaList.getPrice(), 3, new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                WxPay wxPay = (WxPay) model.getModel();
//                if (wxPay != null) {
//                    WXPayHelper.getInstance(context, new WXPayHelper.WXPayCallBack() {
//                        @Override
//                        public void success() {
//                            //没走回调
//                        }
//
//                        @Override
//                        public void falure(String message) {
//
//                        }
//                    }).directPay(wxPay);
//                }
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                ToastUtil.shortShowToast(msg);
//            }
//        }, WxPay.class);
    }

    private void addWatchHistory(int mediaId) {
        User user = MyApplication.getInstance().user;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.jz_video)
        JZVideoPlayerStandard jzVideo;
        @InjectView(R.id.palyLogo)
        ImageView playLogo;
        @InjectView(R.id.mTitleName)
        TextView mTitleName;
        @InjectView(R.id.mPlayCount)
        TextView mPlayCount;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mTotalTime)
        TextView mTotalTime;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
