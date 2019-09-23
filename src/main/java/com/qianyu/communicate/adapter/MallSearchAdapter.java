package com.qianyu.communicate.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.SearchFriend;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者 ： dyj
 * 时间 ： 2016/12/29.
 */

public class MallSearchAdapter extends MyBaseAdapter<SearchFriend.ContentBean, MallSearchAdapter.ViewHolder> {
    private double lat;
    private double lng;

    public void setLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public MallSearchAdapter(Activity context, List data) {
        super(context, data);
    }

    @Override
    protected ViewHolder getViewHolder() {
        return new ViewHolder(mInflater.inflate(R.layout.item_mall_search, null));
    }

    @Override
    protected void onBindViewHolder_(final ViewHolder holder, final int position) {
        if (data != null && data.size() > 0) {
            final SearchFriend.ContentBean contentBean = data.get(position);
            holder.mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl()) ? "" : contentBean.getHeadUrl());
            holder.mContentTv.setText(TextUtils.isEmpty(contentBean.getNickName()) ? "" : contentBean.getNickName());
            holder.mLevelTv.setText("Lv  " + contentBean.getLevel());
            holder.mDistanceTv.setText(TimeUtils.getDescriptionTimeFromTimestamp(contentBean.getLastLoginTime()) + "·" + NumberUtils.getDistance(lng, lat, contentBean.getLastLoginLongitude(), contentBean.getLastLoginLatitude()));
            holder.mAgeTv.setText("" + contentBean.getAge());
            holder.mSignTv.setText("ID:"+contentBean.getUserId());
            int sex = contentBean.getSex();
            switch (sex) {
                case 1:
                    holder.ageSexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_blue_));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
//                    mUserName.setTextColor(getResources().getColor(R.color.btn_blue_));
                    break;
                case 2:
                    holder.ageSexLL.setBackground(context.getResources().getDrawable(R.drawable.shape_cornor_pink));
                    holder.sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
//                    mUserName.setTextColor(getResources().getColor(R.color.colorRed_));
                    break;
            }
            holder.mChargeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecruitPopWindow(holder.mChargeTv,contentBean);
                }
            });
        }
    }

    private void showRecruitPopWindow(TextView mChargeTv, final SearchFriend.ContentBean contentBean) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(context)
                //设置PopupWindow布局
                .setView(R.layout.mall_recharge_pop)
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
                        TextView mCancelTv = view.findViewById(R.id.mCancelTv);
                        TextView mSureTv = view.findViewById(R.id.mSureTv);
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        TextView mUserName = view.findViewById(R.id.mUserName);
                        TextView mIdNum = view.findViewById(R.id.mIdNum);
                        ImageView mFuBaoReduce = view.findViewById(R.id.mFuBaoReduce);
                        ImageView mFuBaoAdd = view.findViewById(R.id.mFuBaoAdd);
                        final EditText mFuBaoET = view.findViewById(R.id.mFuBaoET);
                        mFuBaoET.requestFocus();
                        ImageView mCoinReduce = view.findViewById(R.id.mCoinReduce);
                        ImageView mCoinAdd = view.findViewById(R.id.mCoinAdd);
                        final EditText mCoinET = view.findViewById(R.id.mCoinET);
                        mCoinET.requestFocus();
                        ImageView mDiamondReduce = view.findViewById(R.id.mDiamondReduce);
                        ImageView mDiamondAdd = view.findViewById(R.id.mDiamondAdd);
                        final EditText mDiamondET = view.findViewById(R.id.mDiamondET);
                        mDiamondET.requestFocus();
                        mHeadImg.setImageURI(TextUtils.isEmpty(contentBean.getHeadUrl())?"":contentBean.getHeadUrl());
                        mUserName.setText(""+contentBean.getNickName());
                        mIdNum.setText("ID："+contentBean.getUserId());
                        mCancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        mSureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String fubao = mFuBaoET.getText().toString().trim();
                                String coin = mCoinET.getText().toString().trim();
                                String diamond = mDiamondET.getText().toString().trim();
                                if(TextUtils.isEmpty(fubao)&&TextUtils.isEmpty(coin)&&TextUtils.isEmpty(diamond)){
                                    ToastUtil.shortShowToast("请先输入要充值的数量...");
                                    return;
                                }
                                long fubaoNum = Long.parseLong(TextUtils.isEmpty(fubao)?"0":fubao);
                                long coinNum = Long.parseLong(TextUtils.isEmpty(coin)?"0":coin);
                                long diamondNum = Long.parseLong(TextUtils.isEmpty(diamond)?"0":diamond);
                                if(fubaoNum==0&&coinNum==0&&diamondNum==0){
                                    ToastUtil.shortShowToast("请先输入要充值的数量...");
                                    return;
                                }
                                Tools.showDialog(context);
                                NetUtils.getInstance().rechargeOther(contentBean.getUserId(), fubaoNum, coinNum, diamondNum, new NetCallBack() {
                                    @Override
                                    public void onSuccess(String result, String msg, ResultModel model) {
                                        ToastUtil.shortShowToast(msg);
                                        Tools.dismissWaitDialog();
                                        popupWindow.dismiss();
                                    }

                                    @Override
                                    public void onFail(String code, String msg, String result) {
                                        ToastUtil.shortShowToast(msg);
                                        Tools.dismissWaitDialog();
                                        popupWindow.dismiss();
                                    }
                                },null);
                            }
                        });
                        mallRecharge(mCoinReduce, mCoinAdd, mCoinET);
                        mallRecharge(mFuBaoReduce, mFuBaoAdd, mFuBaoET);
                        mallRecharge(mDiamondReduce, mDiamondAdd, mDiamondET);
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mChargeTv, Gravity.CENTER, 0, 0);
    }

    private void mallRecharge(ImageView mReduce, ImageView mAdd, final EditText mNumET) {
        mReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mNumET.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    ToastUtil.shortShowToast("请先输入充值数量...");
                } else {
                    int count = Integer.parseInt(str);
                    mNumET.setText(count <= 0 ? "0" : ((count - 1) + ""));
                }
            }
        });
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mNumET.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    mNumET.setText("1");
                } else {
                    mNumET.setText((Integer.parseInt(str) + 1) + "");
                }
            }
        });
        mNumET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = mNumET.getText().toString().trim();
                mNumET.setSelection(str.length());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
        @InjectView(R.id.mSignTv)
        TextView mSignTv;
        @InjectView(R.id.mLevelTv)
        TextView mLevelTv;
        @InjectView(R.id.liveStateLL)
        LinearLayout liveStateLL;
        @InjectView(R.id.mMoneyTv)
        TextView mMoneyTv;
        @InjectView(R.id.mChargeTv)
        TextView mChargeTv;
        @InjectView(R.id.mStateLogo)
        ImageView mStateLogo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
