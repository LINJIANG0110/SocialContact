package com.qianyu.communicate.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.donkingliang.banner.CustomBanner;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.CoinGetActivity;
import com.qianyu.communicate.activity.FriendListActivity;
import com.qianyu.communicate.activity.FubowGetActivity;
import com.qianyu.communicate.activity.GiftListActivity;
import com.qianyu.communicate.activity.HotMallActivity;
import com.qianyu.communicate.activity.InfoEditActivity;
import com.qianyu.communicate.activity.MallManageActivity;
import com.qianyu.communicate.activity.MyConcernActivity;
import com.qianyu.communicate.activity.MyLevelActivity;
import com.qianyu.communicate.activity.RegistActivity;
import com.qianyu.communicate.activity.SettingActivity;
import com.qianyu.communicate.activity.SkillDetailActivity;
import com.qianyu.communicate.activity.SuggestBackActivity;
import com.qianyu.communicate.activity.WalletActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseFragment;
import com.qianyu.communicate.base.BaseWebActivity;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.image.ImagePreviewActivity;
import com.qianyu.communicate.utils.NumberUtils;
import com.qianyu.communicate.utils.Tools;
import com.youth.banner.loader.ImageLoader;

import net.neiquan.applibrary.utils.PixelUtil;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.http.APPURL;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tv.buka.sdk.utils.DeviceUtils;

/**
 * Created by Administrator on 2017/11/8 0008.
 * 导航：我的
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.vipTv)
    TextView vipTv;
    @InjectView(R.id.mNameTv)
    TextView mNameTv;
    @InjectView(R.id.mIdTv)
    TextView mIdTv;
    @InjectView(R.id.mLeveTv)
    TextView mLeveTv;
    @InjectView(R.id.mHeadLL)
    LinearLayout mHeadLL;
    @InjectView(R.id.mFansTv)
    TextView mFansTv;
    @InjectView(R.id.mFansLL)
    LinearLayout mFansLL;
    @InjectView(R.id.mMoneyTv)
    TextView mMoneyTv;
    @InjectView(R.id.mConcernLL)
    LinearLayout mConcernLL;
    @InjectView(R.id.mDiamondTv)
    TextView mDiamondTv;
    @InjectView(R.id.mDiamondLL)
    LinearLayout mDiamondLL;
    @InjectView(R.id.mCoinLL)
    LinearLayout mCoinLL;
    @InjectView(R.id.mCollectTv)
    TextView mCollectTv;
    @InjectView(R.id.mChargeRv)
    RelativeLayout mChargeRv;
    @InjectView(R.id.mTaskTv)
    TextView mTaskTv;
    @InjectView(R.id.mTaskRv)
    RelativeLayout mTaskRv;
    @InjectView(R.id.mMyRoomRv)
    RelativeLayout mMyRoomRv;
    @InjectView(R.id.mMyRoomTv)
    TextView mMyRoomTv;
    @InjectView(R.id.mSkillRv)
    RelativeLayout mSkillRv;
    @InjectView(R.id.mBagRv)
    RelativeLayout mBagRv;
    @InjectView(R.id.mLevelTv)
    TextView mLevelTv;
    @InjectView(R.id.mLevelRv)
    RelativeLayout mLevelRv;
    @InjectView(R.id.mDaynmicRv)
    RelativeLayout mDaynmicRv;
    @InjectView(R.id.mMallRv)
    RelativeLayout mMallRv;
    @InjectView(R.id.mMallView)
    View mMallView;
    @InjectView(R.id.mMallManageRv)
    RelativeLayout mMallManageRv;
    @InjectView(R.id.mGiftRv)
    RelativeLayout mGiftRv;
    @InjectView(R.id.mSettingRv)
    RelativeLayout mSettingRv;
    @InjectView(R.id.mAgeTv)
    TextView mAgeTv;
    @InjectView(R.id.ageSexLL)
    LinearLayout ageSexLL;
    @InjectView(R.id.sexLogo)
    ImageView sexLogo;
    @InjectView(R.id.mWalletLL)
    LinearLayout mWalletLL;
    @InjectView(R.id.mFriendCount)
    TextView mFriendCount;
    @InjectView(R.id.suggestBack)
    RelativeLayout suggestBack;
    @InjectView(R.id.iv_edit)
    ImageView ivEdit;
    private PersonalInfo personalInfo;
    @InjectView(R.id.lldot)
    LinearLayout point_group;
    @InjectView(R.id.banner)
    CustomBanner mBanner;
    // 图片资源id
    private List<String> imageData;
    private ImmersionBar mImmersionBar;

    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    private void initViewPager(List<String> heads) {
        imageData = new ArrayList<>();
        imageData.addAll(heads);
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                //这里返回的是轮播图的项的布局 支持任何的布局
                ImageView imageView = new ImageView(context);
                return imageView;
            }

            @Override
            public void updateUI(Context context, final View view, int position, String data) {
                final ImageView mView = (ImageView) view;
                mView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getActivity())
                        .load(data)
                        .asBitmap()//强制Glide返回一个Bitmap对象
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                Bitmap zoomImg2 = Tools.zoomImg(bitmap, PixelUtil.getScreenWidth(getActivity()),
                                        PixelUtil.getScreenWidth(getActivity()) * 2 / 3);
                                mView.setImageBitmap(zoomImg2);
                            }
                        });
            }
        }, imageData);
        mBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int pos, Object o) {
                if (null != imageData && imageData.size() > 0) {
                    final ArrayList<ImageItem> mImageList = new ArrayList<>();
                    for (int i = 0; i < imageData.size(); i++) {
                        ImageItem item = new ImageItem();
                        item.setName("");
                        item.setTime(0);
                        item.setPath(imageData.get(i));
                        mImageList.add(item);
                    }
                    Intent intent3 = new Intent();
                    intent3.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, pos);
                    intent3.setClass(getActivity(), ImagePreviewActivity.class);
                    intent3.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
                    startActivity(intent3);
                }
            }
        });
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.MINE_TAB) {
            loadDatas();
        } else if (event.getState() == EventBuss.FRIEND_REQUEST) {
            mFriendCount.setVisibility(View.VISIBLE);
        } else if (event.getState() == EventBuss.FRIEND_CLEAR) {
            mFriendCount.setVisibility(View.GONE);
        }
    }


    @Override
    public void initData() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);  //状态栏字体是深色，不写默认为亮色
        mImmersionBar.init();
        loadDatas();
    }

    private void loadDatas() {
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Log.e("Mine照片墙", result);
                    personalInfo = (PersonalInfo) model.getModel();
                    if (personalInfo != null) {
                        mHeadImg.setImageURI(TextUtils.isEmpty(personalInfo.getHeadUrl()) ? "" : personalInfo.getHeadUrl());
                        String examinePic = personalInfo.getExaminePic();
                        List<String> tempList = new ArrayList<>();
                        if (null != examinePic && !"".equals(examinePic)) {
                            if (examinePic.contains(",")) {
                                String tempStr[] = examinePic.split(",");
                                for (int i = 0; i < tempStr.length; i++) {
                                    tempList.add(tempStr[i]);
                                }
                            } else {
                                tempList.add(examinePic);
                            }
                            initViewPager(tempList);
                        } else {
                            tempList.add(personalInfo.getHeadUrl());
                            initViewPager(tempList);
                        }
                        mNameTv.setText(TextUtils.isEmpty(personalInfo.getNickName()) ? "" : personalInfo.getNickName());
                        mAgeTv.setText(personalInfo.getAge() + "");
                        mLeveTv.setText(personalInfo.getLevel() + "");
                        mMoneyTv.setText(NumberUtils.rounLong(personalInfo.getFubao()));
                        mDiamondTv.setText(NumberUtils.rounLong(personalInfo.getDiamond()));
                        mCollectTv.setText(NumberUtils.rounLong(personalInfo.getGold()));
                        mIdTv.setText("ID:" + personalInfo.getUserId());
                        mLevelTv.setText("经验值   " + NumberUtils.rounLong(personalInfo.getExperience()) + "/" + NumberUtils.rounLong(personalInfo.getNeedExperience()) + "");
                        mMallManageRv.setVisibility(personalInfo.getExpand() > 0 ? View.VISIBLE : View.GONE);
                        mMallView.setVisibility(personalInfo.getExpand() > 0 ? View.VISIBLE : View.GONE);
                        int sex = personalInfo.getSex();
                        switch (sex) {
                            case 1:
                                sexLogo.setImageResource(R.mipmap.xiangqing_nan1);
                                mAgeTv.setTextColor(getResources().getColor(R.color.btn_blue_));
                                break;
                            case 2:
                                sexLogo.setImageResource(R.mipmap.xiangqing_nv1);
                                mAgeTv.setTextColor(getResources().getColor(R.color.colorRed_));
                                break;
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.vipTv, R.id.mHeadImg, R.id.mFansLL, R.id.mConcernLL, R.id.mDiamondLL, R.id.mCoinLL, R.id.mMyRoomRv, R.id.mTaskRv, R.id.mSkillRv, R.id.mBagRv,
            R.id.mLevelRv, R.id.mDaynmicRv, R.id.mMallRv, R.id.mMallManageRv, R.id.mGiftRv, R.id.mSettingRv, R.id.iv_edit, R.id.suggestBack})
    public void onViewClicked(View view) {
        User user = MyApplication.getInstance().user;
        Intent intent = new Intent(getActivity(), BaseWebActivity.class);
        switch (view.getId()) {
            case R.id.suggestBack:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                getActivity().startActivity(new Intent(getActivity(), SuggestBackActivity.class));
                break;
            case R.id.vipTv:
                intent.putExtra("title", "VIP");
                intent.putExtra("url", "http://188.131.236.58/qianyu/Vip");
                startActivity(intent);
                break;
            case R.id.iv_edit:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                if (personalInfo != null) {
                    Intent intent1 = new Intent(getActivity(), InfoEditActivity.class);
                    intent1.putExtra("personalInfo", personalInfo);
                    startActivity(intent1);
                }
                break;
            case R.id.mHeadImg:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                if (personalInfo != null) {
                    Intent intent1 = new Intent(getActivity(), InfoEditActivity.class);
                    intent1.putExtra("personalInfo", personalInfo);
                    startActivity(intent1);
                }
                break;
            case R.id.mFansLL:
                if (user != null) {
                    Intent intent1 = new Intent(getActivity(), MyConcernActivity.class);
                    intent1.putExtra("sign", "1");
                    intent1.putExtra("userId", user.getUserId());
                    startActivity(intent1);
                } else {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                }
                break;
            case R.id.mConcernLL:
                if (personalInfo != null) {
                    Intent intent2 = new Intent(getActivity(), FubowGetActivity.class);
                    intent2.putExtra("fubao", personalInfo.getFubao());
                    startActivity(intent2);
                }
                break;
            case R.id.mDiamondLL:
                startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            case R.id.mCoinLL:
                if (personalInfo != null) {
                    Intent intent3 = new Intent(getActivity(), CoinGetActivity.class);
                    intent3.putExtra("coin", personalInfo.getGold());
                    startActivity(intent3);
                }
                break;
            case R.id.mMyRoomRv:
                intent.putExtra("title", "VIP");
                intent.putExtra("url", "http://188.131.236.58/qianyu/Vip");
                startActivity(intent);
                break;
            case R.id.mTaskRv:
                intent.putExtra("title", "每日任务");
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10001&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(getActivity()));
                startActivity(intent);
                break;
            case R.id.mSkillRv:
                startActivity(new Intent(getActivity(), SkillDetailActivity.class));
                break;
            case R.id.mBagRv:
                intent.putExtra("title", "我的背包");
                intent.putExtra("url", APPURL.BASE_H5_URL + "gameType=10006&userId=" + user.getUserId() + "&time=" + System.currentTimeMillis() + "&token=" + user.getToken() + "&uid=" + user.getUserId() + "&termType=1&deviceId=" + DeviceUtils.getDeviceId(getActivity()));
                startActivity(intent);
                break;
            case R.id.mLevelRv:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyLevelActivity.class));
                break;
            case R.id.mDaynmicRv:
                if (!MyApplication.getInstance().isLogin()) {
                    ToastUtil.shortShowToast("请先登录...");
                    startActivity(new Intent(getActivity(), RegistActivity.class));
                    return;
                }
                Intent intent1 = new Intent(getActivity(), FriendListActivity.class);
                intent1.putExtra("mType", "");
                intent1.putExtra("friend", true);
                startActivity(intent1);
                break;
            case R.id.mMallRv:
                startActivity(new Intent(getActivity(), HotMallActivity.class));
                break;
            case R.id.mMallManageRv:
                startActivity(new Intent(getActivity(), MallManageActivity.class));
                break;
            case R.id.mGiftRv:
                startActivity(new Intent(getActivity(), GiftListActivity.class));
                break;
            case R.id.mSettingRv:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
