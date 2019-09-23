package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.SubScription;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.SwipeMenuView;

import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MyRoomAdapter extends MyBaseAdapter<SubScription.SubscriptionsBean, MyRoomAdapter.ViewHolder> {

    public MyRoomAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_my_room, null));
    }

    private boolean deleteble=true;
    public void setDeleteble(boolean deleteble){
        this.deleteble=deleteble;
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        //        Uri uri = Uri.parse("http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=902f9e35c5cec3fd8b6baf71e3b8f809/2e2eb9389b504fc2fc5fd3fce3dde71191ef6dc2.jpg");
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + R.drawable.chat_gif);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        holder.mGifLogo.setController(draweeController);
        if (data != null && data.size() > 0) {
            final SubScription.SubscriptionsBean hostListVOListBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(hostListVOListBean.getFamilyPath()) ? "" : hostListVOListBean.getFamilyPath());
            holder.mNameTv.setText(TextUtils.isEmpty(hostListVOListBean.getNickName()) ? "" : hostListVOListBean.getNickName());
            holder.mContentTv.setText(TextUtils.isEmpty(hostListVOListBean.getFamilyName()) ? "" : hostListVOListBean.getFamilyName());
            if (!TextUtils.isEmpty(hostListVOListBean.getJobTitle())) {
                holder.mLevelTv.setVisibility(View.VISIBLE);
                holder.mLevelTv.setText(hostListVOListBean.getJobTitle());
            } else {
                holder.mLevelTv.setVisibility(View.GONE);
            }
            String freeType = hostListVOListBean.getFreeType();
            if (!TextUtils.isEmpty(freeType)) {
                switch (freeType) {
                    case "1":
                        holder.mMoneyTv.setText("免费");
                        break;
                    default:
                        holder.mMoneyTv.setText("￥" + hostListVOListBean.getPrice());
                        break;
                }
            } else {
                holder.mMoneyTv.setText("免费");
            }
//            String liveStatus = hostListVOListBean.getLiveStatus();
//            if (!TextUtils.isEmpty(liveStatus)) {
//                switch (liveStatus) {
//                    case "1":
//                        holder.liveStateLL.setVisibility(View.VISIBLE);
//                        holder.mGifLogo.setController(draweeController);
//                        break;
//                    case "2":
//                        holder.liveStateLL.setVisibility(View.GONE);
//                        break;
//                }
//            } else {
//                holder.liveStateLL.setVisibility(View.GONE);
//            }
            holder.mSwipeMenuView.setSwipeEnable(deleteble);
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mSwipeMenuView.smoothClose();
                    closeFamily(position, hostListVOListBean.getFid());
                }
            });
            holder.contentLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String freeType = hostListVOListBean.getFreeType();
//                    if (!TextUtils.isEmpty(freeType) && !TextUtils.equals("1", freeType)) {
//                        checkPay(hostListVOListBean,holder.contentLL);
//                    } else {
                        addPersonaFamily(hostListVOListBean);
//                    }
                }
            });
        }
    }

    private void showMoneyPopWindow(final SubScription.SubscriptionsBean hostListVOListBean, LinearLayout contentLL) {
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
                        mMoneyTv.setText("￥"+hostListVOListBean.getPrice());
                        payCardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                wxPay(hostListVOListBean);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(contentLL, Gravity.BOTTOM, 0, 0);
    }

    private void wxPay(final SubScription.SubscriptionsBean hostListVOListBean) {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            context.startActivity(new Intent(context, RegistActivity.class));
            return;
        }
//        NetUtils.getInstance().wxPay(0, Tools.getIPAddress(context), hostListVOListBean.getGroupId(), hostListVOListBean.getPrice(), 2, new NetCallBack() {
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


    private void addPersonaFamily(SubScription.SubscriptionsBean hostListVOListBean) {
        final UserBean userBean = new UserBean();
        User user = MyApplication.getInstance().user;
        if (user != null) {
            userBean.setUserId(user.getUserId());
            userBean.setGroupId(hostListVOListBean.getFid() + "");
            userBean.setPhone(user.getPhone());
            userBean.setNickName(user.getNickName());
            userBean.setHeadUrl(user.getHeadUrl());
            userBean.setSex(user.getSex());
            userBean.setAddress(user.getAddress());
            userBean.setAge(user.getAge());
            userBean.setUserNum(user.getUserNum());
        } else {
            userBean.setUserId(0);
            userBean.setGroupId(hostListVOListBean.getFid() + "");
            userBean.setNickName("游客");
        }
        Tools.showDialog(context);
//        NetUtils.getInstance().familyAddPerson(userBean.getUserId(), hostListVOListBean.getGroupId(), DeviceUtils.getDeviceId(context), new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                Tools.dismissWaitDialog();
//                ChatRoom chatRoom = (ChatRoom) model.getModel();
//                userBean.setManager(chatRoom.getManager());
//                userBean.setSilence(chatRoom.getSilence());
//                userBean.setKick(chatRoom.getKick());
//                userBean.setFamilyUserId(chatRoom.getFamilyUserId());
//                if (TextUtils.equals("1", chatRoom.getKick())) {
//                    ToastUtil.shortShowToast("您已被踢出该房间...");
//                    return;
//                }
//                Intent intent = new Intent(context, TextUtils.equals("2", chatRoom.getManager()) ? ChatRoomLiveActivity.class : ChatDetailActivity.class);
//                intent.putExtra("userBean", userBean);
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                Tools.dismissWaitDialog();
//                ToastUtil.shortShowToast(msg);
////                        Intent intent = new Intent(getActivity(), ChatRoomUserActivity.class);
////                        intent.putExtra("userBean", userBean);
////                        startActivity(intent);
//            }
//        }, ChatRoom.class);
    }


    private void closeFamily(final int position, int id) {
//        Tools.showDialog(context);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mSwipeMenuView)
        SwipeMenuView mSwipeMenuView;
        @InjectView(R.id.mGifLogo)
        SimpleDraweeView mGifLogo;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.mNameTv)
        TextView mNameTv;
        @InjectView(R.id.ageSexLL)
        LinearLayout ageSexLL;
        @InjectView(R.id.mAgeTv)
        TextView mAgeTv;
        @InjectView(R.id.sexLogo)
        ImageView sexLogo;
        @InjectView(R.id.mContentTv)
        TextView mContentTv;
        @InjectView(R.id.mDistanceTv)
        TextView mDistanceTv;
        @InjectView(R.id.contentLL)
        LinearLayout contentLL;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
        @InjectView(R.id.liveStateLL)
        LinearLayout liveStateLL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
