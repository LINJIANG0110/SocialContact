package com.qianyu.communicate.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.chatuidemo.Constant;
import com.qianyu.chatuidemo.ui.ChatActivity;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.GiftBgAdapter;
import com.qianyu.communicate.adapter.InfoCircleAdapter;
import com.qianyu.communicate.adapter.SkillBgAdapter;
import com.qianyu.communicate.adapter.TagAdapter_;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.entity.ReportList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserInfo;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.image.ImagePreviewActivity;
import com.qianyu.communicate.utils.SpringUtils;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MyRecylerView;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;
import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.AlertDialog;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.PixelUtil;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class PersonalPageActivity_ extends BaseActivity implements View.OnScrollChangeListener {
    @InjectView(R.id.mBounceScrollView)
    ScrollView mBounceScrollView;
    @InjectView(R.id.mHeadRv)
    RelativeLayout mHeadRv;
    @InjectView(R.id.mLeftLogo)
    ImageView mLeftLogo;
    @InjectView(R.id.mLeftTv)
    TextView mLeftTv;
    @InjectView(R.id.mSkillLogo)
    ImageView mSkillLogo;
    @InjectView(R.id.mChatLL)
    LinearLayout mChatLL;
    @InjectView(R.id.mConcernLL)
    LinearLayout mConcernLL;
    @InjectView(R.id.mHeadFL)
    FrameLayout mHeadFL;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mFans1FL)
    FrameLayout mFans1FL;
    @InjectView(R.id.mFans2FL)
    FrameLayout mFans2FL;
    @InjectView(R.id.mFans3FL)
    FrameLayout mFans3FL;
    @InjectView(R.id.mFans1Img)
    SimpleDraweeView mFans1Img;
    @InjectView(R.id.mFans2Img)
    SimpleDraweeView mFans2Img;
    @InjectView(R.id.mFans3Img)
    SimpleDraweeView mFans3Img;
    @InjectView(R.id.mFamilyHeadImg)
    SimpleDraweeView mFamilyHeadImg;
    @InjectView(R.id.mFamilyName)
    TextView mFamilyName;
    @InjectView(R.id.mFamilyTv)
    TextView mFamilyTv;
    @InjectView(R.id.friendOprate)
    FrameLayout friendOprate;
    @InjectView(R.id.addFriendLogo)
    ImageView addFriendLogo;
    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.familyDetail)
    LinearLayout familyDetail;
    @InjectView(R.id.mTitleHeadImg)
    SimpleDraweeView mTitleHeadImg;
    @InjectView(R.id.concernLL)
    LinearLayout concernLL;
    @InjectView(R.id.fansRankLL)
    LinearLayout fansRankLL;
    @InjectView(R.id.mIdNum)
    TextView mIdNum;
    @InjectView(R.id.mJobTitle)
    TextView mJobTitle;
    @InjectView(R.id.mAgeTv)
    TextView mAgeTv;
    @InjectView(R.id.mLevelTv)
    TextView mLevelTv;
    @InjectView(R.id.mHisConcernLL)
    LinearLayout mHisConcernLL;
    @InjectView(R.id.mIdNumTv)
    TextView mIdNumTv;
    @InjectView(R.id.mLocationTv_)
    TextView mLocationTv_;
    @InjectView(R.id.mUserName)
    TextView mUserName;
    @InjectView(R.id.mSexLL)
    LinearLayout mSexLL;
    @InjectView(R.id.mHeadLL)
    LinearLayout mHeadLL;
    @InjectView(R.id.mSexLogo)
    ImageView mSexLogo;
    @InjectView(R.id.userOfficial)
    ImageView userOfficial;
    @InjectView(R.id.mIdNumLL)
    LinearLayout mIdNumLL;
    @InjectView(R.id.concernLogo)
    ImageView concernLogo;
    @InjectView(R.id.concernTv)
    TextView concernTv;
    @InjectView(R.id.mGiftRecylerView)
    MyRecylerView mGiftRecylerView;
    @InjectView(R.id.hisGiftLL)
    LinearLayout hisGiftLL;
    @InjectView(R.id.mCircleLL)
    LinearLayout mCircleLL;
    @InjectView(R.id.mCicleRecylerView)
    MyRecylerView mCicleRecylerView;
    @InjectView(R.id.mSkillRecylerView)
    MyRecylerView mSkillRecylerView;
    @InjectView(R.id.mHisFansView)
    View mHisFansView;
    @InjectView(R.id.hisFansLL)
    LinearLayout hisFansLL;
    @InjectView(R.id.mLocationTv)
    TextView mLocationTv;
    @InjectView(R.id.mSignTv)
    TextView mSignTv;
    @InjectView(R.id.mLastLoginTime)
    TextView mLastLoginTime;
    @InjectView(R.id.mTagFlowLayout)
    FlowTagLayout mTagFlowLayout;
    private String signal = "2";
    private long userId;
    private GiftBgAdapter giftBgAdapter;
    private InfoCircleAdapter userCircleAdapter;
    private SkillBgAdapter skillBgAdapter;
    private TagAdapter_<String> tagAdapter;
    private int titleHeight;
    private UserInfo userinfo;

    @Override
    public int getRootViewId() {
        setSystemBarTint_();
        EventBus.getDefault().register(this);
        return R.layout.activity_personal_page_;
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {

    }


    public void setViews() {
        if (getIntent() != null) {
            userId = getIntent().getLongExtra("userId", 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            titleHeight = PixelUtil.getScreenHeight(this) / 8;
            mBounceScrollView.setOnScrollChangeListener(this);
        }
    }


    @Override
    public void initData() {
        setResult(101);
        initTagRecylerView();
        initRecylerView();
        loadPersonalInfo();
    }

    @OnClick({R.id.mHeadLL, R.id.mHeadFL, R.id.friendOprate, R.id.addFriendLogo, R.id.backFL, R.id.familyDetail,
            R.id.mCircleLL, R.id.hisGiftLL, R.id.mHisConcernLL, R.id.mConcernLL, R.id.mChatLL,
            R.id.hisFansLL, R.id.mSkillLogo, R.id.concernLL, R.id.fansRankLL})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        switch (view.getId()) {
            case R.id.mHeadLL:
            case R.id.mHeadFL:
                if (userinfo != null && userinfo.getUserInfo() != null) {
                    AppLog.e("===================mHeadFL=============" + mHeadFL);
                    final ArrayList<ImageItem> mImageList = new ArrayList<>();
                    mImageList.clear();
                    ImageItem imageItem = new ImageItem();
                    imageItem.setPath(TextUtils.isEmpty(userinfo.getUserInfo().getHeadUrl()) ? "" : userinfo.getUserInfo().getHeadUrl());
                    mImageList.add(imageItem);
                    Intent intent3 = new Intent();
                    intent3.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, 0);
                    intent3.setClass(PersonalPageActivity_.this, ImagePreviewActivity.class);
                    intent3.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                    startActivity(intent3);
                }
                break;
            case R.id.friendOprate:
                if (user != null) {
                    showFriendOptPopWindow();
                }
                break;
            case R.id.addFriendLogo:
                if (user == null) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(PersonalPageActivity_.this, RegistActivity.class));
                    return;
                }
                if (userId == user.getUserId()) {
                    ToastUtil.shortShowToast("不能添加自己为好友哦...");
                    return;
                }
                NetUtils.getInstance().applyUser(userId, user.getUserId(), new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        ToastUtil.shortShowToast(msg);
                        addFriendLogo.setImageResource(R.mipmap.yiguanzhu);
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        ToastUtil.shortShowToast(msg);
                    }
                }, null);
                break;
            case R.id.backFL:
                finish();
                break;
            case R.id.familyDetail:
                if (userinfo != null && userinfo.getUserInfo() != null) {
                    Tools.enterFamily(PersonalPageActivity_.this, userinfo.getUserInfo().getGroupId(), false);
                }
                break;
            case R.id.mCircleLL:
                Intent intent6 = new Intent(PersonalPageActivity_.this, MyCircleActivity.class);
                intent6.putExtra("userId", userId);
                startActivity(intent6);
                break;
            case R.id.hisGiftLL:
                Intent intent5 = new Intent(PersonalPageActivity_.this, MyGiftActivity.class);
                intent5.putExtra("userId", userId);
                startActivity(intent5);
                break;
            case R.id.hisFansLL:
                Intent intent7 = new Intent(PersonalPageActivity_.this, MyConcernActivity.class);
                intent7.putExtra("sign", "2");
                //userId 某个人的id
                intent7.putExtra("userId", userId);
                startActivity(intent7);
                break;
            case R.id.mHisConcernLL:
//                SpringUtils.springAnim(mConcernLL);
//                if (userinfo != null && userinfo.getUserInfo() != null) {
//                    if (user == null) {
//                        ToastUtil.shortShowToast("请先登录...");
//                        startActivity(new Intent(PersonalPageActivity.this, RegistActivity.class));
//                        return;
//                    }
//                    if (userId == user.getUserId()) {
//                        ToastUtil.shortShowToast("不能和自己聊天哦...");
//                        return;
//                    }
//                    Intent intent4 = new Intent(PersonalPageActivity.this, ChatActivity.class);
//                    intent4.putExtra(Constant.EXTRA_USER_ID, userinfo.getUserInfo().getPhone());
//                    startActivity(intent4);
//                }
                break;
            case R.id.mConcernLL:
                SpringUtils.springAnim(mConcernLL);
                if (userinfo != null && userinfo.getUserInfo() != null) {
                    if (user == null) {
                        ToastUtil.shortShowToast("请先登录...");
                        startActivity(new Intent(PersonalPageActivity_.this, RegistActivity.class));
                        return;
                    }
                    if (userId == user.getUserId()) {
                        ToastUtil.shortShowToast("不能和自己聊天哦...");
                        return;
                    }
                    Intent intent4 = new Intent(PersonalPageActivity_.this, ChatActivity.class);
                    intent4.putExtra(Constant.EXTRA_USER_ID, userinfo.getUserInfo().getPhone());
                    startActivity(intent4);
                }
                break;
            case R.id.mChatLL:
                SpringUtils.springAnim(mChatLL);
                if (userId == user.getUserId()) {
                    ToastUtil.shortShowToast("不能给自己送礼哦...");
                    return;
                }
                if (userinfo != null && userinfo.getUserInfo() != null) {
                    Intent intent2 = new Intent(PersonalPageActivity_.this, GiftListActivity.class);
                    intent2.putExtra("userId", userId);
                    intent2.putExtra("phone", userinfo.getUserInfo().getPhone());
                    startActivity(intent2);
                }
                break;
            case R.id.mSkillLogo:
                SpringUtils.springAnim(mSkillLogo);
                if (userId == user.getUserId()) {
                    ToastUtil.shortShowToast("不能对自己使用技能哦...");
                    return;
                }
                Intent intent = new Intent(PersonalPageActivity_.this, SkillActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                break;
            case R.id.concernLL:
                Intent intent4 = new Intent(PersonalPageActivity_.this, MyConcernActivity.class);
                intent4.putExtra("sign", "2");
                //userId 某个人的id
                intent4.putExtra("userId", userId);
                startActivity(intent4);
                break;
            case R.id.fansRankLL:
//                Intent intent1 = new Intent(PersonalPageActivity.this, FansRankListActivity.class);
//                //userId 某个人的id
//                intent1.putExtra("userId", userId + "");
//                intent1.putExtra("fansId", user == null ? "" : user.getUserId() + "");
//                startActivity(intent1);
                break;
        }
    }

    private void initRecylerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(PersonalPageActivity_.this, LinearLayoutManager.HORIZONTAL, false);
        mGiftRecylerView.setLayoutManager(layoutManager);
        giftBgAdapter = new GiftBgAdapter(PersonalPageActivity_.this, null);
        mGiftRecylerView.setAdapter(giftBgAdapter);
        mGiftRecylerView.setNestedScrollingEnabled(false);
        giftBgAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(PersonalPageActivity_.this, MyGiftActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager1 =
                new LinearLayoutManager(PersonalPageActivity_.this, LinearLayoutManager.HORIZONTAL, false);
        mCicleRecylerView.setLayoutManager(layoutManager1);
        userCircleAdapter = new InfoCircleAdapter(PersonalPageActivity_.this, null);
        mCicleRecylerView.setAdapter(userCircleAdapter);
        mCicleRecylerView.setNestedScrollingEnabled(false);
        userCircleAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                Intent intent = new Intent(PersonalPageActivity_.this, MyCircleActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        GridLayoutManager layoutManager2 =
                new GridLayoutManager(PersonalPageActivity_.this, 2);
        mSkillRecylerView.setLayoutManager(layoutManager2);
        skillBgAdapter = new SkillBgAdapter(PersonalPageActivity_.this, null);
        mSkillRecylerView.setAdapter(skillBgAdapter);
        mSkillRecylerView.setNestedScrollingEnabled(false);
    }

    private void loadPersonalInfo() {
        NetUtils.getInstance().userInfo(userId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                userinfo = model.getModel();
                if (userinfo != null) {
                    addFriendLogo.setImageResource(userinfo.getFriendStatus() == 2 ? R.mipmap.yiguanzhu : R.mipmap.guanzhu);
                    UserInfo.UserInfoBean userInfo = userinfo.getUserInfo();
                    UserInfo.UserCircleBean userCircle = userinfo.getUserCircle();
                    List<UserInfo.SkillMapBean> skillMap = userinfo.getSkillMap();
                    List<UserInfo.UserInfoBean> fanList = userinfo.getFanList();
                    List<UserInfo.GiftListBean> giftList = userinfo.getGiftList();
                    if (userInfo != null) {
                        mHeadImg.setImageURI(TextUtils.isEmpty(userInfo.getHeadUrl()) ? "" : userInfo.getHeadUrl());
                        mUserName.setText(TextUtils.isEmpty(userInfo.getNickName()) ? "" : userInfo.getNickName());
                        mAgeTv.setText(userInfo.getAge() + "岁");
                        mLevelTv.setText(userInfo.getLevel() + "级");
                        mLocationTv_.setText(TextUtils.isEmpty(userInfo.getCurrentAddress()) ? "" : userInfo.getCurrentAddress());
                        mIdNumTv.setText(userInfo.getUserId() + "");
                        if (userInfo.getGroupId() <= 0) {
                            mFamilyHeadImg.setVisibility(View.GONE);
                        } else {
                            mFamilyHeadImg.setVisibility(View.VISIBLE);
                            mFamilyHeadImg.setImageURI(TextUtils.isEmpty(userInfo.getGroupheadUrl()) ? "" : userInfo.getGroupheadUrl());
                            mFamilyName.setText(TextUtils.isEmpty(userInfo.getGroupName()) ? "" : userInfo.getGroupName());
                        }
                        mSignTv.setText(TextUtils.isEmpty(userInfo.getDetails()) ? "" : userInfo.getDetails());
                        mLastLoginTime.setText(TimeUtils.getDescriptionTimeFromTimestamp(userInfo.getLastLoginTime()));
                        userOfficial.setVisibility(userInfo.getExpand()>0?View.VISIBLE:View.GONE);
                        String label = userInfo.getLabel();
                        if (!TextUtils.isEmpty(label)) {
                            try {
                                List<String> userLabels = new ArrayList<>();
                                String[] strings = label.split(",");
                                for (int i = 0; i < strings.length; i++) {
                                    userLabels.add(strings[i]);
                                }
                                tagAdapter.onlyAddAll(userLabels);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (userCircle != null) {
                        String fileItemUrl = userCircle.getFileItemUrl();
                        if (!TextUtils.isEmpty(fileItemUrl)) {
                            List<String> urls = new ArrayList<>();
                            String[] strings = fileItemUrl.split(",");
                            for (int i = 0; i < strings.length; i++) {
                                urls.add(strings[i]);
                            }
                            userCircleAdapter.appendAll(urls);
                        }
                    }
                    if (skillMap != null && skillMap.size() > 0) {
                        skillBgAdapter.appendAll(skillMap);
                    }
                    if (giftList != null && giftList.size() > 0) {
                        giftBgAdapter.appendAll(giftList);
                    }
                    if (fanList != null && fanList.size() > 0) {
                        switch (fanList.size()) {
                            case 1:
                                mFans1Img.setImageURI(TextUtils.isEmpty(fanList.get(0).getHeadUrl()) ? "" : fanList.get(0).getHeadUrl());
//                                mFans1FL.setVisibility(View.VISIBLE);
//                                mFans2FL.setVisibility(View.INVISIBLE);
//                                mFans3FL.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                mFans1Img.setImageURI(TextUtils.isEmpty(fanList.get(0).getHeadUrl()) ? "" : fanList.get(0).getHeadUrl());
                                mFans2Img.setImageURI(TextUtils.isEmpty(fanList.get(1).getHeadUrl()) ? "" : fanList.get(1).getHeadUrl());
//                                mFans1FL.setVisibility(View.VISIBLE);
//                                mFans2FL.setVisibility(View.VISIBLE);
//                                mFans3FL.setVisibility(View.INVISIBLE);
                                break;
                            default:
                                mFans1Img.setImageURI(TextUtils.isEmpty(fanList.get(0).getHeadUrl()) ? "" : fanList.get(0).getHeadUrl());
                                mFans2Img.setImageURI(TextUtils.isEmpty(fanList.get(1).getHeadUrl()) ? "" : fanList.get(1).getHeadUrl());
                                mFans3Img.setImageURI(TextUtils.isEmpty(fanList.get(2).getHeadUrl()) ? "" : fanList.get(2).getHeadUrl());
                                break;
                        }
                    } else {
//                        hisFansLL.setVisibility(View.GONE);
//                        mHisFansView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {

            }
        }, UserInfo.class);
    }

    private void initTagRecylerView() {
        mTagFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tagAdapter = new TagAdapter_<String>(this);
        mTagFlowLayout.setAdapter(tagAdapter);
        mTagFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
//                startActivity(new Intent(getActivity(), ImpressTagActivity.class));

//                for (int i = 0; i < selectedList.size(); i++) {
//                    AppLog.e("==========setOnTagSelectListener===========" + selectedList.get(i));
//                    Search data = ((Search) parent.getAdapter().getItem(selectedList.get(i)));
//                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
//                    intent.putExtra("search", data.getTitle());
//                    startActivity(intent);
//                }
            }
        });
    }

    private void concernOrNot() {
        User user = MyApplication.getInstance().user;
        if (user != null) {
//            Tools.showDialog(this);
            AppLog.e(user.getUserId() + "=======concernOrNot======" + userId + "==============" + signal);
        } else {
            ToastUtil.shortShowToast("请先登录....");
            startActivity(new Intent(PersonalPageActivity_.this, RegistActivity.class));
        }
    }

    private void showFriendOptPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.friend_opt_pop)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.Animation_right)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(0.5f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final PopupWindow popupWindow, View view, int layoutResId) {
                        LinearLayout markLL = view.findViewById(R.id.markLL);
                        LinearLayout blackLL = view.findViewById(R.id.blackLL);
                        LinearLayout deleteLL = view.findViewById(R.id.deleteLL);
                        final ImageView markLogo = view.findViewById(R.id.markLogo);
                        final ImageView blackLogo = view.findViewById(R.id.blackLogo);
                        final ImageView deleteLogo = view.findViewById(R.id.deleteLogo);
                        final User user = MyApplication.getInstance().user;
                        markLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                SpringUtils.springAnim(markLogo);
                                if (userId == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能举报自己哦...");
                                    return;
                                }
                                reportList();
                            }
                        });
                        blackLL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                                SpringUtils.springAnim(blackLogo);
                                if (userId == user.getUserId()) {
                                    ToastUtil.shortShowToast("不能拉黑自己哦...");
                                    return;
                                }
                                if (userinfo != null && userinfo.getUserInfo() != null) {
                                    new AlertDialog.Builder(PersonalPageActivity_.this).setTitle("确认将" + userinfo.getUserInfo().getNickName() + "加入到黑名单吗?")
                                            .setMessage("被加入黑名单的用户将无法给你发私信、评论、关注你，你将无法收到Ta@你的消息提示")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    addBlack();
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            }).create().show();
                                }
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAsDropDown(friendOprate, 0, 10);
    }

    private void reportList() {
        final List<ReportList> list = new ArrayList<>();
        list.add(new ReportList(1, "威胁辱骂"));
        list.add(new ReportList(2, "色情骚扰"));
        list.add(new ReportList(3, "垃圾广告"));
        list.add(new ReportList(4, "欺诈违法"));
        AlertChooser.Builder builder = new AlertChooser.Builder(PersonalPageActivity_.this).setTitle("请选择举报类型");
        for (int i = 0; i < list.size(); i++) {
            final String reportName = list.get(i).getReportName();
            builder.addItem(reportName, new AlertChooser.OnItemClickListener() {
                @Override
                public void OnItemClick(AlertChooser chooser) {
                    chooser.dismiss();
                    User user = MyApplication.getInstance().user;
                    if (user == null) {
                        ToastUtil.shortShowToast("请先登录");
                        startActivity(new Intent(PersonalPageActivity_.this, RegistActivity.class));
                        return;
                    }
                    NetUtils.getInstance().saveReport(user.getUserId(), userId, reportName, "", "", new NetCallBack() {
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
        builder.setNegativeItem("取消", new AlertChooser.OnItemClickListener() {
            @Override
            public void OnItemClick(AlertChooser chooser) {
                chooser.dismiss();
            }
        }).show();
    }

    private void addBlack() {
        NetUtils.getInstance().pullBack(userId, new NetCallBack() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onScrollChange(View v, int x, int y, int oldX, int oldY) {
        if (y <= 0) {
            mHeadRv.setBackgroundColor(Color.argb((int) 0, 185, 245, 247));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= titleHeight) {
            float scale = (float) y / titleHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            mHeadRv.setBackgroundColor(Color.argb((int) alpha, 185, 245, 247));
        } else {
            mHeadRv.setBackgroundColor(Color.argb((int) 255, 185, 245, 247));
        }
    }
}

