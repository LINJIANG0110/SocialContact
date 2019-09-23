package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.content.DialogInterface;
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

import com.qianyu.communicate.R;
import com.qianyu.communicate.entity.FamilyManage;
import com.qianyu.communicate.entity.FamilyMember;
import com.facebook.drawee.view.SimpleDraweeView;

import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class FamilyDetailAdapter extends MyBaseAdapter<FamilyMember.ContentBean, FamilyDetailAdapter.ViewHolder> {

    public FamilyDetailAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_family_detail, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            final FamilyMember.ContentBean contentBean = data.get(position);
            final FamilyMember.ContentBean.UserInfoBean userInfo = contentBean.getUserInfo();
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(userInfo.getHeadUrl()) ? "" : userInfo.getHeadUrl());
            holder.userName.setText(TextUtils.isEmpty(userInfo.getNickName()) ? "" : userInfo.getNickName());
            switch (contentBean.getUserType()) {
                case 1:
                    holder.headLogo.setVisibility(View.VISIBLE);
                    holder.headLogo.setImageResource(R.mipmap.patriarch);
                    break;
                case 2:
                    holder.headLogo.setVisibility(View.VISIBLE);
                    holder.headLogo.setImageResource(R.mipmap.manager);
                    break;
                case 3:
                    holder.headLogo.setVisibility(View.GONE);
                    break;
                case 4:
                    holder.headLogo.setVisibility(View.GONE);
                    break;
                default:
                    holder.headLogo.setVisibility(View.GONE);
                    break;
            }
            holder.mTimeLL.setVisibility(contentBean.isManage() ? View.GONE : View.VISIBLE);
            holder.mOperateLL.setVisibility(contentBean.isManage() ? View.VISIBLE : View.GONE);
            holder.powerManage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPowerPopWindow(holder.powerManage);
                }
            });
            holder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context).setTitle("踢人")
                            .setMessage("您确定踢出" + userInfo.getNickName() + "吗？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    deleteMember(contentBean);
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }
            });
        }
    }

    private void deleteMember(final FamilyMember.ContentBean contentBean) {
//        Tools.showDialog(context);
//        NetUtils.getInstance().reduceGroup(contentBean.getGroupId(), contentBean.getUserInfo().getUserId(), new NetCallBack() {
//            @Override
//            public void onSuccess(String result, String msg, ResultModel model) {
//                Tools.dismissWaitDialog();
//                ToastUtil.shortShowToast(msg);
//                removeSingle(contentBean);
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFail(String code, String msg, String result) {
//                Tools.dismissWaitDialog();
//                ToastUtil.shortShowToast(msg);
//            }
//        }, null);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.mRootLL)
        LinearLayout mRootLL;
        @InjectView(R.id.mHeadImg)
        SimpleDraweeView mHeadImg;
        @InjectView(R.id.headLogo)
        ImageView headLogo;
        @InjectView(R.id.userName)
        TextView userName;
        @InjectView(R.id.mTimeLL)
        LinearLayout mTimeLL;
        @InjectView(R.id.mOperateLL)
        LinearLayout mOperateLL;
        @InjectView(R.id.mTimeTv)
        TextView mTimeTv;
        @InjectView(R.id.powerManage)
        TextView powerManage;
        @InjectView(R.id.mDeleteTv)
        TextView mDeleteTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    private void showPowerPopWindow(TextView powerManage) {
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
                            }
                        });

                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                                StaggeredGridLayoutManager.VERTICAL);
                        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
                        mManagerRecylerView.setLayoutManager(layoutManager);
                        final List<FamilyManage> list = new ArrayList<>();
                        list.add(new FamilyManage(1,"上一麦"));
                        list.add(new FamilyManage(2,"上二麦"));
                        list.add(new FamilyManage(3,"禁言72小时"));
                        list.add(new FamilyManage(4,"取消禁言"));
                        list.add(new FamilyManage(5,"封号2小时"));
                        list.add(new FamilyManage(6,"取消封号"));
                        list.add(new FamilyManage(7,"踢出家族"));
                        list.add(new FamilyManage(8,"下麦"));
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
}
