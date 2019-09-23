package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.qianyu.chatuidemo.Constant;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilyBossAdapter;
import com.qianyu.communicate.adapter.FamilyMemberAdapter;
import com.qianyu.communicate.adapter.FamilyMemberAdapter_;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.FamilyDetail;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MyRecylerView;

import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.listener.Progress;
import net.neiquan.okhttp.listener.UploadListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 游客家族详情
 */

public class FamilyCustomerDetailActivity extends BaseActivity {

    @InjectView(R.id.mFamilyName)
    TextView mFamilyName;
    @InjectView(R.id.mCollectFamily)
    TextView mCollectFamily;
    @InjectView(R.id.mFamilyPlace)
    TextView mFamilyPlace;
    @InjectView(R.id.mFamilyID)
    TextView mFamilyID;
    @InjectView(R.id.mMemberRecylerView)
    MyRecylerView mMemberRecylerView;
    @InjectView(R.id.mAllMembersLL)
    LinearLayout mAllMembersLL;
    @InjectView(R.id.mHeadRv)
    RelativeLayout mHeadRv;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mFamilyWelcomeLL)
    LinearLayout mFamilyWelcomeLL;
    @InjectView(R.id.mFamilyWelcome)
    TextView mFamilyWelcome;
    @InjectView(R.id.mFamilyIntrodceLL)
    LinearLayout mFamilyIntrodceLL;
    @InjectView(R.id.mFamilyIntrodce)
    TextView mFamilyIntrodce;
    @InjectView(R.id.mRequestEnter)
    TextView mRequestEnter;
    @InjectView(R.id.mFamilyInvite)
    TextView mFamilyInvite;
    private FamilyList.ContentBean familyInfo;
    private FamilyMemberAdapter_ adapter;
    private UserBean userBean;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_customer_detail;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("家族详情");
        initRecylerView();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.FAMILY_WELCOME) {
            String message = (String) event.getMessage();
            mFamilyWelcome.setText(message);
        } else if (event.getState() == EventBuss.FAMILY_INTRODUCE) {
            String message = (String) event.getMessage();
            mFamilyIntrodce.setText(message);
        }
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            if (userBean != null) {
                mCollectFamily.setText(userBean.getUserType() == 4 ? "  已关注  " : "    关注    ");
            }
            if (familyInfo != null) {
                loadDatas();
            }
        }
    }

    private void loadDatas() {
        NetUtils.getInstance().familyDetail(familyInfo.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FamilyDetail familyDetail = (FamilyDetail) model.getModel();
                if (familyDetail != null) {
                    FamilyDetail.GroupInfoBean groupInfo = familyDetail.getGroupInfo();
                    List<FamilyDetail.MembersBean> members = familyDetail.getMembers();
                    if (groupInfo != null) {
                        mFamilyName.setText(TextUtils.isEmpty(groupInfo.getGroupName()) ? "" : groupInfo.getGroupName());
                        mFamilyPlace.setText(TextUtils.isEmpty(groupInfo.getAddress()) ? "" : groupInfo.getAddress());
                        mFamilyID.setText("ID:" + groupInfo.getGroupId());
                        mHeadImg.setImageURI(TextUtils.isEmpty(groupInfo.getHeadUrl()) ? "" : groupInfo.getHeadUrl());
                        mFamilyWelcome.setText(TextUtils.isEmpty(groupInfo.getIntroduce()) ? "欢迎进入家族!" : groupInfo.getIntroduce());
                        mFamilyIntrodce.setText(TextUtils.isEmpty(groupInfo.getDetails()) ? "欢迎进入家族!" : groupInfo.getDetails());
                    }
                    if (members != null && members.size() > 0) {
                        adapter.appendAll(members);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FamilyDetail.class);
    }

    @OnClick({R.id.mCollectFamily, R.id.mAllMembersLL, R.id.mRequestEnter,R.id.mFamilyInvite})
    public void onViewClicked(View view) {
        Intent intent = new Intent(FamilyCustomerDetailActivity.this, FamilyMemberActivity_.class);
        intent.putExtra("family", familyInfo);
        switch (view.getId()) {
            case R.id.mCollectFamily:
                Tools.showDialog(this);
                NetUtils.getInstance().concernGroup(familyInfo.getGroupId(), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        Tools.dismissWaitDialog();
                        if (userBean != null) {
                            userBean.setUserType(userBean.getUserType() == 4 ? 5 : 4);
                            mCollectFamily.setText(userBean.getUserType() == 4 ? "  已收藏  " : "    收藏    ");
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                        Tools.dismissWaitDialog();
                    }
                }, null);
                break;
            case R.id.mAllMembersLL:
                startActivity(intent);
                break;
            case R.id.mRequestEnter:
                Tools.showDialog(this);
                NetUtils.getInstance().applyGroup(familyInfo.getGroupId(), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        Tools.dismissWaitDialog();
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                        Tools.dismissWaitDialog();
                    }
                }, null);
                break;
            case R.id.mFamilyInvite:
                Intent intent1 = new Intent(FamilyCustomerDetailActivity.this, FriendInviteActivity.class);
                intent.putExtra("mType","");
                intent1.putExtra("friend", false);
                intent1.putExtra("family", familyInfo);
                startActivity(intent1);
                break;
        }
    }

    private void showRecruitedPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.recruited_member_pop)
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
                        SimpleDraweeView mHeadImg = view.findViewById(R.id.mHeadImg);
                        mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
                        TextView mCancelTv = view.findViewById(R.id.mCancelTv);
                        TextView mSureTv = view.findViewById(R.id.mSureTv);
                        mCancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        mSureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mCollectFamily, Gravity.CENTER, 0, 0);
    }

    private void showEnterPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.enter_family_pop)
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

                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mRequestEnter, Gravity.CENTER, 0, 0);
    }


    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(6,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mMemberRecylerView.setLayoutManager(layoutManager);
        adapter = new FamilyMemberAdapter_(this, null);
        mMemberRecylerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
