package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.FamilyMemberDetailActivity;
import com.qianyu.communicate.activity.FamilyReduceActivity;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.activity.FriendInviteActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper_;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.FamilyManage;
import com.qianyu.communicate.entity.FamilyMember;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.TimeUtils;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tv.buka.sdk.BukaSDKManager;

import static com.qianyu.communicate.activity.FamilyRoomActivity.firstMikeUser;
import static com.qianyu.communicate.activity.FamilyRoomActivity.secondMikeUser;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyMemberAdapter extends MyBaseAdapter<FamilyDetail.MembersBean, FamilyMemberAdapter.ViewHolder> {
    private FamilyList.ContentBean familyInfo;
    private UserBean currentUserBean;
    private boolean manage = false;

    public boolean isManage() {
        return manage;
    }

    public void setManage(boolean manage) {
        this.manage = manage;
        notifyDataSetChanged();
    }

    public FamilyMemberAdapter(Activity context, List data, FamilyList.ContentBean familyInfo, UserBean userBean) {
        super(context, data);
        this.familyInfo = familyInfo;
        this.currentUserBean = userBean;
    }


    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_member, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final FamilyDetail.MembersBean membersBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(membersBean.getHeadUrl()) ? "" : membersBean.getHeadUrl());
            holder.userName.setText(TextUtils.isEmpty(membersBean.getNickName()) ? "" : membersBean.getNickName());
            holder.mTimeTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(membersBean.getLastLoginTime()));
            holder.mManageTv.setVisibility(isManage() ? View.VISIBLE : View.GONE);
            if (position == getItemCount() - 2 || position == getItemCount() - 1) {
                Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + ((position == getItemCount() - 2) ? R.mipmap.add : R.mipmap.reduce));
                holder.mHeadImg.setImageURI(uri);
                holder.mHeadImg.setVisibility(isManage() ? View.GONE : View.VISIBLE);
                holder.headLogo.setVisibility(View.GONE);
                holder.userName.setVisibility(View.GONE);
                holder.mTimeTv.setVisibility(View.GONE);
                holder.mManageTv.setVisibility(View.GONE);
                holder.mHeadImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentUserBean.getUserType() > 2) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        Intent intent = new Intent(context, (position == getItemCount() - 2) ? FriendInviteActivity.class : FamilyReduceActivity.class);
                        intent.putExtra("family", familyInfo);
                        intent.putExtra("mType","");
                        intent.putExtra("userBean", currentUserBean);
                        intent.putExtra("friend", false);
                        context.startActivity(intent);
                    }
                });
            } else {
                holder.mHeadImg.setVisibility(View.VISIBLE);
                holder.headLogo.setVisibility(View.VISIBLE);
                holder.userName.setVisibility(View.VISIBLE);
                holder.mTimeTv.setVisibility(View.VISIBLE);
            }
            switch (membersBean.getUserType()) {
                case 1:
                    holder.headLogo.setImageResource(R.mipmap.patriarch);
                    break;
                case 2:
                    holder.headLogo.setImageResource(R.mipmap.manager);
                    break;
                case 3:
                case 4:
                case 5:
                    holder.headLogo.setVisibility(View.GONE);
                    break;
            }
            holder.mManageTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final UserBean userBean = new UserBean();
                    userBean.setUserId(membersBean.getUserId());
                    userBean.setNickName(membersBean.getNickName());
                    userBean.setHeadUrl(membersBean.getHeadUrl());
                    userBean.setUserType(membersBean.getUserType());
                    if (currentUserBean.getUserId() == userBean.getUserId()) {
                        ToastUtil.shortShowToast("不能对自己操作哦...");
                        return;
                    }
                    showPowerPopWindow(userBean, position, holder.mManageTv);
                }
            });
        }
    }

    private void showPowerPopWindow(final UserBean userBean, final int position, TextView powerManage) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.power_manage_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_Fade)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        RecyclerView mManagerRecylerView = view.findViewById(R.id.mManagerRecylerView);
                        TextView cancelTv = view.findViewById(R.id.cancelTv);
                        TextView sureTv = view.findViewById(R.id.sureTv);
                        final List<FamilyManage> list = new ArrayList<>();
                        list.add(new FamilyManage(1, "下麦"));
                        list.add(new FamilyManage(2, "上一麦"));
                        list.add(new FamilyManage(3, "上二麦"));
                        list.add(new FamilyManage(4, "取消禁言"));
                        list.add(new FamilyManage(5, "禁言72小时"));
                        list.add(new FamilyManage(6, "取消禁入"));
                        list.add(new FamilyManage(7, "禁入两小时"));
                        list.add(new FamilyManage(8, userBean.getUserType() == 2 ? "取消管理员" : "设为管理员"));
                        list.add(new FamilyManage(9, "从群聊移除"));
                        cancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        sureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                changeUserState(userBean, position, list);
                            }
                        });
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                                StaggeredGridLayoutManager.VERTICAL);
                        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
                        mManagerRecylerView.setLayoutManager(layoutManager);
                        final FamilyManageAdapter adapter = new FamilyManageAdapter(context, list);
                        mManagerRecylerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
                                for (int i = 0; i < data.size(); i++) {
                                    ((FamilyManage) data.get(i)).setSelected(false);
                                }
                                ((FamilyManage) data.get(position)).setSelected(true);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(powerManage, Gravity.CENTER, 0, 0);
    }

    private void changeUserState(final UserBean userBean, final int position, List<FamilyManage> list) {
        List<tv.buka.sdk.entity.User> userArr = BukaSDKManager.getUserManager().getUserArr();
        for (int i = 0; i < list.size(); i++) {
            FamilyManage familyManage = list.get(i);
            if (familyManage.isSelected()) {
                switch (familyManage.getId()) {
                    case 1: {
                        if (currentUserBean.getUserType() > userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        if (firstMikeUser != null && firstMikeUser.getUserId() == userBean.getUserId()) {
                            BukaHelper_.getInstance(context).rpc(null, Constant.RPC_END_FIRST_MIKE, JSON.toJSONString(userBean));
                        } else if (secondMikeUser != null && secondMikeUser.getUserId() == userBean.getUserId()) {
                            BukaHelper_.getInstance(context).rpc(null, Constant.RPC_END_SECOND_MIKE, JSON.toJSONString(userBean));
                        } else {
                            ToastUtil.shortShowToast("当前用户还没上麦呢...");
                        }
                    }
                    break;
                    case 2: {
                        if (currentUserBean.getUserType() > userBean.getUserType() || currentUserBean.getUserType() > 2) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        if ((firstMikeUser != null&&firstMikeUser.getUserId()==userBean.getUserId())||(secondMikeUser != null&&secondMikeUser.getUserId()==userBean.getUserId())) {
                            ToastUtil.shortShowToast("该用户已上麦，请勿重复操作...");
                            return;
                        }
                        if (firstMikeUser != null) {
                            if (currentUserBean.getUserType() >= firstMikeUser.getUserType()) {
                                ToastUtil.shortShowToast("一麦已经有人上了...");
                                return;
                            }
                            BukaHelper_.getInstance(context).rpc(null, Constant.RPC_END_FIRST_MIKE, JSON.toJSONString(firstMikeUser));
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_BEGIN_FIRST_MIKE, JSON.toJSONString(userBean));
                    }
                    break;
                    case 3: {
                        if (currentUserBean.getUserType() > userBean.getUserType() || currentUserBean.getUserType() > 2) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        if ((firstMikeUser != null&&firstMikeUser.getUserId()==userBean.getUserId())||(secondMikeUser != null&&secondMikeUser.getUserId()==userBean.getUserId())) {
                            ToastUtil.shortShowToast("该用户已上麦，请勿重复操作...");
                            return;
                        }
                        if (secondMikeUser != null) {
                            if (currentUserBean.getUserType() >= secondMikeUser.getUserType()) {
                                ToastUtil.shortShowToast("二麦已经有人上了...");
                                return;
                            }
                            BukaHelper_.getInstance(context).rpc(null, Constant.RPC_END_SECOND_MIKE, JSON.toJSONString(secondMikeUser));
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_BEGIN_SECOND_MIKE, JSON.toJSONString(userBean));
                    }
                    break;
                    case 4:
                        if (currentUserBean.getUserType() >= userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_ALLOW_SPEAK, JSON.toJSONString(userBean));
                        forbidSpeakAndIn(userBean, 1, 0, 0);
                        break;
                    case 5:
                        if (currentUserBean.getUserType() >= userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_FORBID_SPEAK, JSON.toJSONString(userBean));
                        forbidSpeakAndIn(userBean, 1, 1, 72 * 60 * 60);
                        break;
                    case 6:
                        if (currentUserBean.getUserType() >= userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_ALLOW_IN, JSON.toJSONString(userBean));
                        forbidSpeakAndIn(userBean, 2, 0, 0);
                        break;
                    case 7:
                        if (currentUserBean.getUserType() >= userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        BukaHelper_.getInstance(context).rpc(null, Constant.RPC_FORBID_IN, JSON.toJSONString(userBean));
                        forbidSpeakAndIn(userBean, 2, 1, 2 * 60 * 60);
                        break;
                    case 8:
                        if (this.currentUserBean.getUserType() != 1) {
                            ToastUtil.shortShowToast("只有群主才能设置管理员...");
                            return;
                        }
                        if (userBean.getUserType() == 1) {
                            ToastUtil.shortShowToast("群主不能被设置为管理员...");
                            return;
                        }
                        NetUtils.getInstance().setManager(userBean.getUserId(), familyInfo.getGroupId(), userBean.getUserType() == 2 ? 3 : 2, new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                ToastUtil.shortShowToast(msg);
                                if (userBean.getUserType() == 3) {
                                    BukaHelper_.getInstance(context).rpc(null, Constant.RPC_SET_MANAGER, JSON.toJSONString(userBean));
                                }
                                data.get(position).setUserType(userBean.getUserType() == 2 ? 3 : 2);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {
                                ToastUtil.shortShowToast(msg);
                            }
                        }, null);
                        break;
                    case 9:
                        if (this.currentUserBean.getUserType() >= userBean.getUserType()) {
                            ToastUtil.shortShowToast("权限不足...");
                            return;
                        }
                        NetUtils.getInstance().reduceGroup(familyInfo.getGroupId(), userBean.getUserId()+"", new NetCallBack() {
                            @Override
                            public void onSuccess(String result, String msg, ResultModel model) {
                                ToastUtil.shortShowToast(msg);
                                BukaHelper_.getInstance(context).rpc(null, Constant.RPC_TICK_SB, JSON.toJSONString(userBean));
                                removeSingle(position);
                                notifyDataSetChanged();
                                EventBuss event = new EventBuss(EventBuss.FAMILY_REDUCE);
                                event.setMessage(userBean.getUserId());
                                EventBus.getDefault().post(event);
                            }

                            @Override
                            public void onFail(String code, String msg, String result) {
                                ToastUtil.shortShowToast(msg);
                            }
                        }, null);
                        break;
                }
            }
        }
    }

    private void forbidSpeakAndIn(UserBean userBean, int userState, int isenable, int time) {
        NetUtils.getInstance().changeUserState(currentUserBean.getUserId(), userBean.getUserId(), familyInfo.getGroupId(), userState, isenable, time, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                ToastUtil.shortShowToast(msg);
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.headLogo)
        ImageView headLogo;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.mManageTv)
        TextView mManageTv;
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
