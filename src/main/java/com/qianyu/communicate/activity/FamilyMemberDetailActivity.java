package com.qianyu.communicate.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.FamilyBossAdapter;
import com.qianyu.communicate.adapter.FamilyMemberAdapter;
import com.qianyu.communicate.adapter.FamilyRequestAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.BukaHelper_;
import com.qianyu.communicate.bukaSdk.bkconstant.Constant;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.EnterGroup;
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
 * 家族成员家族详情
 */

public class FamilyMemberDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.exitFamily)
    LinearLayout exitFamily;
    @InjectView(R.id.inviteFamily)
    LinearLayout inviteFamily;
    @InjectView(R.id.recruitBroadCast)
    LinearLayout recruitBroadCast;
    @InjectView(R.id.bottomLL)
    LinearLayout bottomLL;
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
    @InjectView(R.id.mAllMembers)
    TextView mAllMembers;
    @InjectView(R.id.mRecallMembers)
    TextView mRecallMembers;
    @InjectView(R.id.mHeadRv)
    RelativeLayout mHeadRv;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mFamilyNameLL)
    LinearLayout mFamilyNameLL;
    @InjectView(R.id.mFamilyNameTv)
    TextView mFamilyNameTv;
    @InjectView(R.id.mFamilyWelcomeLL)
    LinearLayout mFamilyWelcomeLL;
    @InjectView(R.id.mFamilyWelcome)
    TextView mFamilyWelcome;
    @InjectView(R.id.mFamilyIntrodceLL)
    LinearLayout mFamilyIntrodceLL;
    @InjectView(R.id.mFamilyIntrodce)
    TextView mFamilyIntrodce;
    @InjectView(R.id.mRequestRecylerView)
    MyRecylerView mRequestRecylerView;
    @InjectView(R.id.mBossRecylerView)
    MyRecylerView mBossRecylerView;
    @InjectView(R.id.mChatLogo)
    ImageView mChatLogo;
    @InjectView(R.id.mMoneyTv)
    TextView mMoneyTv;
    @InjectView(R.id.mCoinTv)
    TextView mCoinTv;
    @InjectView(R.id.mManagerSalaryTv)
    TextView mManagerSalaryTv;
    @InjectView(R.id.mOwnerSalaryTv)
    TextView mOwnerSalaryTv;
    @InjectView(R.id.mWeekSalaryLL)
    LinearLayout mWeekSalaryLL;
    @InjectView(R.id.mPresidentTv)
    TextView mPresidentTv;
    @InjectView(R.id.mGroupOwnerTv)
    TextView mGroupOwnerTv;
    @InjectView(R.id.mRequestTv)
    TextView mRequestTv;
    @InjectView(R.id.mBossTitleTv)
    TextView mBossTitleTv;
    @InjectView(R.id.msgNotify)
    RelativeLayout msgNotify;
    @InjectView(R.id.mVertifyLogo)
    ImageView mVertifyLogo;
    private FamilyList.ContentBean familyInfo;
    private PhotoChoicePop mPhotoChoicePop;
    private FamilyMemberAdapter familyMemberAdapter;
    private FamilyRequestAdapter requestAdapter;
    private FamilyBossAdapter familyBossAdapter;
    private UserBean userBean;
    private String joinType = "2";
    private EnterGroup enterGroup;

    @Override
    public int getRootViewId() {
        return R.layout.activity_family_member_detail;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("家族详情");
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.FAMILY_NAME) {
            String message = (String) event.getMessage();
            mFamilyNameTv.setText(message);
            mFamilyName.setText(message);
        } else if (event.getState() == EventBuss.FAMILY_WELCOME) {
            String message = (String) event.getMessage();
            mFamilyWelcome.setText(message);
        } else if (event.getState() == EventBuss.FAMILY_INTRODUCE) {
            String message = (String) event.getMessage();
            mFamilyIntrodce.setText(message);
        } else if (event.getState() == EventBuss.FAMILY_APPLY) {
            loadDatas();
        } else if (event.getState() == EventBuss.FAMILY_REDUCE) {
            long userId = (long) event.getMessage();
            List<FamilyDetail.MembersBean> data = familyMemberAdapter.data;
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    FamilyDetail.MembersBean membersBean = data.get(i);
                    if (userId == membersBean.getUserId()) {
                        familyMemberAdapter.removeSingle(membersBean);
                    }
                }
            }
        }
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            familyInfo = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            userBean = ((UserBean) getIntent().getSerializableExtra("userBean"));
            enterGroup = ((EnterGroup) getIntent().getSerializableExtra("enterGroup"));
            if (familyInfo != null) {
                loadDatas();
            }
        }
        initRecylerView();
    }

    private void loadDatas() {
        NetUtils.getInstance().familyDetail(familyInfo.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                FamilyDetail familyDetail = (FamilyDetail) model.getModel();
                if (familyDetail != null) {
                    FamilyDetail.GroupInfoBean groupInfo = familyDetail.getGroupInfo();
                    List<FamilyDetail.MembersBean> members = familyDetail.getMembers();
                    List<FamilyDetail.ApplyListBean> applyList = familyDetail.getApplyList();
                    FamilyDetail.WagesBean wages = familyDetail.getWages();
                    List<FamilyDetail.WagelistBean> wagelist = familyDetail.getWagelist();
                    List<FamilyDetail.GroupBossStateBean> bossStateBeanList = familyDetail.getGroupBossState();
                    if (groupInfo != null) {
                        mFamilyName.setText(TextUtils.isEmpty(groupInfo.getGroupName()) ? "" : groupInfo.getGroupName());
                        mFamilyPlace.setText(TextUtils.isEmpty(groupInfo.getAddress()) ? "" : groupInfo.getAddress());
                        mFamilyID.setText("ID:" + groupInfo.getGroupId());
                        mHeadImg.setImageURI(TextUtils.isEmpty(groupInfo.getHeadUrl()) ? "" : groupInfo.getHeadUrl());
                        mFamilyNameTv.setText(TextUtils.isEmpty(groupInfo.getGroupName()) ? "" : groupInfo.getGroupName());
                        mFamilyWelcome.setText(TextUtils.isEmpty(groupInfo.getIntroduce()) ? "欢迎进入家族!" : groupInfo.getIntroduce());
                        mFamilyIntrodce.setText(TextUtils.isEmpty(groupInfo.getDetails()) ? "欢迎进入家族!" : groupInfo.getDetails());
                        joinType = groupInfo.getJoinType() + "";
                        mVertifyLogo.setImageResource(TextUtils.equals("1", joinType) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                    }
                    if (members != null && members.size() > 0) {
                        members.add(new FamilyDetail.MembersBean());
                        members.add(new FamilyDetail.MembersBean());
                        familyMemberAdapter.appendAll(members);
                    }
                    if (userBean.getUserType() <= 2 && applyList != null && applyList.size() > 0) {
                        mRequestTv.setText("申请列表（" + applyList.size() + "）");
                        requestAdapter.setgId(familyInfo.getGroupId());
                        requestAdapter.appendAll(applyList);
                    } else {
                        mRequestTv.setVisibility(View.GONE);
                    }
                    if (wages != null) {
                        mMoneyTv.setText("x" + wages.getFubao());
                        mCoinTv.setText("x" + wages.getGold());
                    }
                    if (wagelist != null && wagelist.size() > 0) {
                        for (int i = 0; i < wagelist.size(); i++) {
                            FamilyDetail.WagelistBean wagelistBean = wagelist.get(i);
                            if (wagelistBean.getUserType() == 1) {
                                mOwnerSalaryTv.setText(wagelistBean.getRewardName());
                            }
                            if (wagelistBean.getUserType() == 2) {
                                mManagerSalaryTv.setText(wagelistBean.getRewardName());
                            }
                        }
                    }
                    if (bossStateBeanList != null && bossStateBeanList.size() > 0) {
                        familyBossAdapter.appendAll(bossStateBeanList);
                    } else {
                        mBossTitleTv.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, FamilyDetail.class);
    }

    @OnClick({R.id.mCollectFamily, R.id.mAllMembers, R.id.mRecallMembers, R.id.mHeadRv, R.id.mHeadImg,R.id.mFamilyNameLL, R.id.mFamilyWelcomeLL,
            R.id.mFamilyIntrodceLL, R.id.mVertifyLogo, R.id.mChatLogo, R.id.mWeekSalaryLL, R.id.mPresidentTv,
            R.id.mGroupOwnerTv, R.id.exitFamily, R.id.inviteFamily, R.id.recruitBroadCast})
    public void onViewClicked(View view) {
        Intent intent = new Intent(FamilyMemberDetailActivity.this, FamilyMemberActivity.class);
        intent.putExtra("family", familyInfo);
        intent.putExtra("userBean", userBean);
        Intent intent1 = new Intent(FamilyMemberDetailActivity.this, EditFamilykActivity.class);
        intent1.putExtra("family", familyInfo);
        switch (view.getId()) {
            case R.id.mCollectFamily:
                break;
            case R.id.mAllMembers:
                startActivity(intent);
                break;
            case R.id.mRecallMembers:
                showCallPopWindow();
                break;
            case R.id.mHeadRv:
            case R.id.mHeadImg:
                if (userBean.getUserType() == 1) {
                    showAlertChooser();
                }
                break;
            case R.id.mFamilyNameLL:
                if (userBean.getUserType() == 1) {
                    intent1.putExtra("type", 0);
                    startActivity(intent1);
                }
                break;
            case R.id.mFamilyWelcomeLL:
                if (userBean.getUserType() == 1) {
                    intent1.putExtra("type", 1);
                    startActivity(intent1);
                }
                break;
            case R.id.mFamilyIntrodceLL:
                if (userBean.getUserType() == 1) {
                    intent1.putExtra("type", 2);
                    startActivity(intent1);
                }
                break;
            case R.id.mVertifyLogo:
                if (TextUtils.equals("1", joinType)) {
                    joinType = "2";
                } else {
                    joinType = "1";
                }
                NetUtils.getInstance().familyUpdate(familyInfo.getGroupId(), familyInfo.getGroupName(), "", "", "", joinType, "", -1, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        mVertifyLogo.setImageResource(TextUtils.equals("1", joinType) ? R.mipmap.shezhi_guanbi : R.mipmap.shezhi_dakai);
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, null);
                break;
            case R.id.mChatLogo:
                break;
            case R.id.mWeekSalaryLL:
                Intent intent3 = new Intent(this, FamilySalaryActivity.class);
                intent3.putExtra("groupId", familyInfo.getGroupId());
                startActivity(intent3);
                break;
            case R.id.mPresidentTv:
                User user = MyApplication.getInstance().user;
                if (enterGroup != null && enterGroup.getGroupOwnerInfo() != null && user.getUserId() != enterGroup.getGroupOwnerInfo().getUserId()) {
                    Intent intent2 = new Intent(FamilyMemberDetailActivity.this, ChatActivity.class);
                    intent2.putExtra(com.qianyu.chatuidemo.Constant.EXTRA_USER_ID, enterGroup.getGroupOwnerInfo().getPhone());
                    startActivity(intent2);
                }
                break;
            case R.id.mGroupOwnerTv:
                break;
            case R.id.exitFamily:
                new AlertDialog.Builder(FamilyMemberDetailActivity.this).setTitle("退出聊天室?")
                        .setMessage("您是否确定退出聊天室？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                NetUtils.getInstance().exitGroup(familyInfo.getGroupId(), userBean.getUserId(), new NetCallBack() {
                                    @Override
                                    public void onSuccess(String result, String msg, ResultModel model) {
                                        ToastUtil.shortShowToast(msg);
                                        EventBus.getDefault().post(new EventBuss(EventBuss.FAMILY_EXIT));
                                        finish();
                                    }

                                    @Override
                                    public void onFail(String code, String msg, String result) {
                                        ToastUtil.shortShowToast(msg);
                                    }
                                }, null);
                            }
                        }).create().show();
                break;
            case R.id.inviteFamily:
                Intent intent2 = new Intent(FamilyMemberDetailActivity.this, FriendInviteActivity.class);
                intent2.putExtra("mType","");
                intent2.putExtra("friend", false);
                intent2.putExtra("family", familyInfo);
                startActivity(intent2);
                break;
            case R.id.recruitBroadCast:
                showRecruitPopWindow();
                break;
        }
    }

    private void showCallPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.call_member_pop_)
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
                        mHeadImg.setImageURI(TextUtils.isEmpty(userBean.getHeadUrl()) ? "" : userBean.getHeadUrl());
                        TextView mUserName = view.findViewById(R.id.mUserName);
                        mUserName.setText(TextUtils.isEmpty(userBean.getNickName()) ? "" : userBean.getNickName());
                        final EditText mCallET = view.findViewById(R.id.mCallET);
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
                                String str = mCallET.getText().toString();
                                String msg = TextUtils.isEmpty(str) ? "族长正在召唤本家族成员回归，点击即可传送!" : str;
                                NetUtils.getInstance().familyCall(Long.parseLong(userBean.getGroupId()), userBean.getUserId(), msg, new NetCallBack() {
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
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mRecallMembers, Gravity.CENTER, 0, 0);
    }

    private void showRecruitPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.recruit_broadcast_pop)
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
                        final EditText mCallET = view.findViewById(R.id.mCallET);
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
                                String str = mCallET.getText().toString();
                                String msg = TextUtils.isEmpty(str) ? "本群美女帅哥土豪多多，欢迎大家来玩，点击立即进入，即可传送！" : str;
                                NetUtils.getInstance().worldCall(Long.parseLong(userBean.getGroupId()), userBean.getUserId(), msg, new NetCallBack() {
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
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(recruitBroadCast, Gravity.CENTER, 0, 0);
    }

    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(6,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mMemberRecylerView.setLayoutManager(layoutManager);
        familyMemberAdapter = new FamilyMemberAdapter(this, null, familyInfo, userBean);
        mMemberRecylerView.setAdapter(familyMemberAdapter);

        LinearLayoutManager layoutManager1 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRequestRecylerView.setLayoutManager(layoutManager1);
        requestAdapter = new FamilyRequestAdapter(this, null);
        mRequestRecylerView.setAdapter(requestAdapter);

        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBossRecylerView.setLayoutManager(layoutManager2);
        familyBossAdapter = new FamilyBossAdapter(this, null);
        mBossRecylerView.setAdapter(familyBossAdapter);
        familyBossAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                final FamilyDetail.GroupBossStateBean bossStateBean = (FamilyDetail.GroupBossStateBean) data.get(position);
                if (bossStateBean.getIsallowcall() != 0) {
                    new AlertDialog.Builder(FamilyMemberDetailActivity.this).setTitle("召唤Boss")
                            .setMessage("您确定召唤" + bossStateBean.getBossName() + "吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    NetUtils.getInstance().familyBoss(Long.parseLong(userBean.getGroupId()), userBean.getUserId(), "群主召唤，参加家族Boss战！", new NetCallBack() {
                                        @Override
                                        public void onSuccess(String result, String msg, ResultModel model) {
                                            ToastUtil.shortShowToast(msg);
                                            bossStateBean.setDonum(bossStateBean.getDonum()-1);
                                            familyBossAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onFail(String code, String msg, String result) {
                                            ToastUtil.shortShowToast(msg);
                                        }
                                    }, null);
                                }
                            }).create().show();
                }
            }
        });
    }

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private static final int STORAGE_PERMISSION = 10001;
    public static final int CAMERA_PERMISSION = 10002;

    private void showAlertChooser() {
        EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        new AlertChooser.Builder(this).setTitle("选择图片").addItem("相机", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                requestCameraStorage();
                chooser.dismiss();
            }
        }).addItem("相册", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                requestExternalStorage();
//                showPhotoChoicePop();
                chooser.dismiss();
            }
        }).setNegativeItem("取消", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                chooser.dismiss();
            }
        }).show();
    }

    @AfterPermissionGranted(STORAGE_PERMISSION)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
            showPhotoChoicePop();
        } else {
            EasyPermissions.requestPermissions(this, "", STORAGE_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(CAMERA_PERMISSION)
    public void requestCameraStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
        } else {
//            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.CAMERA);
            EasyPermissions.requestPermissions(this, "", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    private String addImg = "";
    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
//                    Tools.showDialog(PublishCircleActivity.this);
                    BitmapUtis.compress(photoInfo.getPhotoPath(), 600, 600, new BitmapUtis.CompressCallback() {
                        @Override
                        public void onsucces(String s) {
                            NetUtils.getInstance().fileUpload(new File(s), new UploadListener() {

                                @Override
                                public void onUISuccess(final String res) {
                                    AppLog.e("==========onUISuccess=======" + res);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(res);
                                                final String imgPath = jsonObject.getString("data");
                                                NetUtils.getInstance().familyUpdate(familyInfo.getGroupId(), familyInfo.getGroupName(), "", "", imgPath, "", "", -1, new NetCallBack() {
                                                    @Override
                                                    public void onSuccess(String result, String msg, ResultModel model) {
                                                        Tools.dismissWaitDialog();
                                                        ToastUtil.shortShowToast(msg);
                                                        mHeadImg.setImageURI(imgPath);
                                                    }

                                                    @Override
                                                    public void onFail(String code, String msg, String result) {
                                                        Tools.dismissWaitDialog();
                                                        ToastUtil.shortShowToast(msg);
                                                    }
                                                }, null);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onUIFailure(Exception e) {
                                    ToastUtil.shortShowToast("上传失败!");
                                    Tools.dismissWaitDialog();
                                    AppLog.e("==========onUIFailure=======" + e.getMessage());
                                }

                                @Override
                                public void onUIProgress(Progress progress) {

                                }
                            });
                        }

                        @Override
                        public void onfail() {
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast("图片压缩失败!");
                        }
                    });
                }
            }
        }

        @Override
        public void onHandlerFailure(int requestCode, String errorMsg) {
            AppLog.e("=============onHandlerFailure====================");
        }
    };

    @Override
    public void onPermissionsGranted(List<String> perms) {
        AppLog.e("=============onPermissionsGranted111====================");
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {
        AppLog.e("=============onPermissionsDenied111====================");
    }

    private void showPhotoChoicePop() {
        if (mPhotoChoicePop == null) {
            mPhotoChoicePop = new PhotoChoicePop(this, photoCallBack);
        }
        mPhotoChoicePop.showPop(mHeadRv);
    }

    /**
     * 图片选择器
     */
    private PhotoChoicePop.CallBackPop photoCallBack = new PhotoChoicePop.CallBackPop() {
        @Override
        public void close(String path) {
            BitmapUtis.compress(path, 600, 600, new BitmapUtis.CompressCallback() {
                @Override
                public void onsucces(String s) {
                    NetUtils.getInstance().fileUpload(new File(s), new UploadListener() {

                        @Override
                        public void onUISuccess(final String res) {
                            AppLog.e("==========onUISuccess=======" + res);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject(res);
                                        final String imgPath = jsonObject.getString("data");
                                        NetUtils.getInstance().familyUpdate(familyInfo.getGroupId(), familyInfo.getGroupName(), "", "", imgPath, "", "", -1, new NetCallBack() {
                                            @Override
                                            public void onSuccess(String result, String msg, ResultModel model) {
                                                Tools.dismissWaitDialog();
                                                ToastUtil.shortShowToast(msg);
                                                mHeadImg.setImageURI(imgPath);
                                            }

                                            @Override
                                            public void onFail(String code, String msg, String result) {
                                                Tools.dismissWaitDialog();
                                                ToastUtil.shortShowToast(msg);
                                            }
                                        }, null);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onUIFailure(Exception e) {
                            ToastUtil.shortShowToast("上传失败!");
                            Tools.dismissWaitDialog();
                            AppLog.e("==========onUIFailure=======" + e.getMessage());
                        }

                        @Override
                        public void onUIProgress(Progress progress) {

                        }
                    });
                }

                @Override
                public void onfail() {
                    Tools.dismissWaitDialog();
                    ToastUtil.shortShowToast("图片压缩失败!");
                }
            });
        }
    };


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
