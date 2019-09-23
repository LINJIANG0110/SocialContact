package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.FamilyInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

import android.widget.DatePicker.OnDateChangedListener;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class ChatRoomCretaeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.mTimeLL)
    LinearLayout mTimeLL;
    @InjectView(R.id.mSubjectLL)
    LinearLayout mSubjectLL;
    @InjectView(R.id.mTimeTv)
    TextView mTimeTv;
    @InjectView(R.id.mSubjectTv)
    TextView mSubjectTv;
    @InjectView(R.id.mMoneyTv)
    TextView mMoneyTv;
    @InjectView(R.id.startLive)
    TextView startLive;
    @InjectView(R.id.showLiveImg)
    ImageView showLiveImg;
    @InjectView(R.id.mNameEt)
    EditText mNameEt;
    @InjectView(R.id.mAudioLogo)
    ImageView mAudioLogo;
    @InjectView(R.id.mAudioTv)
    TextView mAudioTv;
    @InjectView(R.id.mAudioLL)
    LinearLayout mAudioLL;
    @InjectView(R.id.mVideoLogo)
    ImageView mVideoLogo;
    @InjectView(R.id.mVideoTv)
    TextView mVideoTv;
    @InjectView(R.id.mVideoLL)
    LinearLayout mVideoLL;
    @InjectView(R.id.mFreeLogo)
    ImageView mFreeLogo;
    @InjectView(R.id.mFreeTv)
    TextView mFreeTv;
    @InjectView(R.id.mFreeLL)
    LinearLayout mFreeLL;
    @InjectView(R.id.mMoneyLogo)
    ImageView mMoneyLogo;
    @InjectView(R.id.mMoneyLL)
    LinearLayout mMoneyLL;
    @InjectView(R.id.mMoneyNum)
    EditText mMoneyNum;
    @InjectView(R.id.mMoneyNumLL)
    LinearLayout mMoneyNumLL;
    private String liveType = "1";
    private String freeType = "1";
    private PhotoChoicePop mPhotoChoicePop;
    private long timeMillis;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    private String timeStr;
    private FamilyInfo familyInfo;
    private String subject;

    @Override
    public int getRootViewId() {
        return R.layout.activity_chat_room_create;
    }

    @Override
    public void setViews() {
        if (getIntent() != null && (FamilyInfo) getIntent().getSerializableExtra("family") != null) {
            familyInfo = ((FamilyInfo) getIntent().getSerializableExtra("family"));
            setTitleTv("编辑聊天室");
            startLive.setText("完成");
            if (familyInfo != null) {
                mNameEt.setText(TextUtils.isEmpty(familyInfo.getFamilyName()) ? "" : familyInfo.getFamilyName());
                mSubjectTv.setText(TextUtils.isEmpty(familyInfo.getFamily_describe()) ? "" : familyInfo.getFamily_describe());
                imgPath = familyInfo.getFamilyPath();
                ImageUtil.loadPicNet(TextUtils.isEmpty(imgPath) ? "" : imgPath, showLiveImg);
                freeType = familyInfo.getFreeType();
                if (TextUtils.equals("2", freeType)) {
                    mMoneyNumLL.setVisibility(View.VISIBLE);
                    mMoneyNum.setText("" + familyInfo.getPrice());
                } else {
                    mMoneyNumLL.setVisibility(View.GONE);
                }
                changeMoneyBg();
                timeMillis = familyInfo.getTimeMillis();
            }
        } else {
            setTitleTv("创建聊天室");
            startLive.setText("开始直播");
            timeMillis = System.currentTimeMillis();
        }
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        AppLog.e(timeMillis + "===========timeMillis111===========" + (timeMillis + 5 * 60 * 1000));
        year = TimeUtils.getYearByTimeStamp(timeMillis);
        monthOfYear = TimeUtils.getMonthByTimeStamp(timeMillis);
        dayOfMonth = TimeUtils.getDayByTimeStamp(timeMillis);
        hourOfDay = TimeUtils.getHourByTimeStamp(timeMillis);
        minute = TimeUtils.getMinuteByTimeStamp(timeMillis);
        timeStr = TimeUtils.getTime(timeMillis);
        Spanned spanned = Html.fromHtml("<font color='#545454'><u>" + TimeUtils.getTime(timeMillis) + "</u></font>");
        mTimeTv.setText(spanned);
//        Spanned spanned1 = Html.fromHtml("<font color='#fd5522'><u>9.00</u></font>");
//        mMoneyNum.setText(spanned1);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.mSubjectLL, R.id.startLive, R.id.showLiveImg, R.id.mAudioLL, R.id.mVideoLL, R.id.mFreeLL, R.id.mMoneyLL, R.id.mTimeLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSubjectLL:
                startActivityForResult(new Intent(ChatRoomCretaeActivity.this, ChatSubjectActivity.class), 0);
                break;
            case R.id.startLive:
                startLive();
                break;
            case R.id.showLiveImg:
                showAlertChooser();
                break;
            case R.id.mAudioLL:
                liveType = "1";
                changeAVBg();
                break;
            case R.id.mVideoLL:
                liveType = "2";
                changeAVBg();
                break;
            case R.id.mFreeLL:
                freeType = "1";
                mMoneyNumLL.setVisibility(View.GONE);
                changeMoneyBg();
                break;
            case R.id.mMoneyLL:
                freeType = "2";
                mMoneyNumLL.setVisibility(View.VISIBLE);
                changeMoneyBg();
                break;
            case R.id.mTimeLL:
                showTimePopWindow();
                break;
        }

    }

    private void showTimePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.date_time_pop)
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
                        DatePicker datePicker = (DatePicker) view.findViewById(R.id.dpPicker);
                        TimePicker timePicker = (TimePicker) view.findViewById(R.id.tpPicker);
                        datePicker.init(TimeUtils.getYearByTimeStamp(timeMillis), TimeUtils.getMonthByTimeStamp(timeMillis) > 1 ? (TimeUtils.getMonthByTimeStamp(timeMillis) - 1) : 1, TimeUtils.getDayByTimeStamp(timeMillis), new OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year1,
                                                      int monthOfYear1, int dayOfMonth1) {
                                year = year1;
                                monthOfYear = monthOfYear1 + 1;
                                dayOfMonth = dayOfMonth1;
                            }
                        });
                        timePicker.setIs24HourView(true);
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker view, int hourOfDay1, int minute1) {
                                hourOfDay = hourOfDay1;
                                minute = minute1;
                            }
                        });
                        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        view.findViewById(R.id.confirm_tv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
//                                String timeStr_ = year + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : monthOfYear) + "-" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth) + " " + (hourOfDay < 10 ? ("0" + hourOfDay) : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute);
                                timeStr = year + "-" + (monthOfYear < 10 ? ("0" + monthOfYear) : monthOfYear) + "-" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : dayOfMonth) + " " + (hourOfDay < 10 ? ("0" + hourOfDay) : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute);
//                                timeStr = year + "-" + monthOfYear + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;
                                timeMillis = TimeUtils.stringToLong(timeStr, "yyyy-MM-dd HH:mm");
                                AppLog.e(timeMillis + "===========timeMillis222===========" + (timeMillis + 5 * 60));
                                Spanned spanned = Html.fromHtml("<font color='#545454'><u>" + timeStr + "</u></font>");
                                mTimeTv.setText(spanned);
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(mTimeLL, Gravity.BOTTOM, 0, 0);
    }

    private long timeMillis_;

    private void startLive() {
        AppLog.e(timeMillis + "===========timeMillis333========" + System.currentTimeMillis());
        if (timeMillis < System.currentTimeMillis()) {
            ToastUtil.shortShowToast("开播时间不能小于当前时间...");
            return;
        }
        timeMillis_ = (timeMillis < (System.currentTimeMillis() + 60 * 1000)) ? (timeMillis + 5 * 60 * 1000) : timeMillis;
        AppLog.e(timeMillis_ + "===========timeMillis444========" + System.currentTimeMillis());
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(ChatRoomCretaeActivity.this, RegistActivity.class));
            return;
        }
        if (TextUtils.isEmpty(imgPath)) {
            ToastUtil.shortShowToast("请先上传封面图片...");
            return;
        }
        String familyName = mNameEt.getText().toString().trim();
        if (TextUtils.isEmpty(familyName)) {
            ToastUtil.shortShowToast("请先填写直播标题...");
            return;
        }
        if (TextUtils.isEmpty(subject)) {
            ToastUtil.shortShowToast("请先填写主题概要...");
            return;
        }
        String price = mMoneyNum.getText().toString().trim();
        double mPrice = 0;
        if (!TextUtils.isEmpty(price)) {
            mPrice = Double.parseDouble(price);
        }
        if (mPrice == 0 && TextUtils.equals("2", freeType)) {
            ToastUtil.shortShowToast("请先输入价格...");
            return;
        }
    }

    private void changeAVBg() {
        mAudioLogo.setImageResource(TextUtils.equals("1", liveType) ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
        mAudioTv.setTextColor(getResources().getColor(TextUtils.equals("1", liveType) ? R.color.text_black : R.color.text_gray));
        mVideoLogo.setImageResource(TextUtils.equals("2", liveType) ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
        mVideoTv.setTextColor(getResources().getColor(TextUtils.equals("2", liveType) ? R.color.text_black : R.color.text_gray));
    }

    private void changeMoneyBg() {
        mFreeLogo.setImageResource(TextUtils.equals("1", freeType) ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
        mFreeTv.setTextColor(getResources().getColor(TextUtils.equals("1", freeType) ? R.color.text_black : R.color.text_gray));
        mMoneyLogo.setImageResource(TextUtils.equals("2", freeType) ? R.mipmap.wxdl_xuanze : R.mipmap.wxdl_xuanzehui);
        mMoneyTv.setTextColor(getResources().getColor(TextUtils.equals("2", freeType) ? R.color.text_black : R.color.text_gray));
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


    private String imgPath;
    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    AppLog.e("=============imgUrl==========" + photoInfo.getPhotoPath());
//                    Tools.showDialog(ChatRoomCretaeActivity.this);
                    BitmapUtis.compress(photoInfo.getPhotoPath(), 480, 800, new BitmapUtis.CompressCallback() {
                        @Override
                        public void onsucces(String s) {

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
        mPhotoChoicePop.showPop(showLiveImg);
    }

    /**
     * 图片选择器
     */
    private PhotoChoicePop.CallBackPop photoCallBack = new PhotoChoicePop.CallBackPop() {
        @Override
        public void close(String path) {
            BitmapUtis.compress(path, 480, 800, new BitmapUtis.CompressCallback() {
                @Override
                public void onsucces(String s) {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            if (data != null) {
                subject = data.getStringExtra("subject");
                mSubjectTv.setText(subject);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
