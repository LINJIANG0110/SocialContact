package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.qianyu.chatuidemo.DemoHelper;
import com.qianyu.chatuidemo.db.DemoDBManager;
import com.qianyu.communicate.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserEntity;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;
import com.reyun.sdk.ReYunGame;

import net.neiquan.applibrary.numberpicker.view.AlertCityPicker;
import net.neiquan.applibrary.numberpicker.view.AlertDatePicker;
import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.CommonPopupWindow;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.listener.Progress;
import net.neiquan.okhttp.listener.UploadListener;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.RegexValidateUtil;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class InfoCompleteActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.backFL)
    FrameLayout backFL;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.photoFL)
    FrameLayout photoFL;
    @InjectView(R.id.nameEv)
    EditText nameEv;
    @InjectView(R.id.nameLL)
    LinearLayout nameLL;
    @InjectView(R.id.sexTv)
    TextView sexTv;
    @InjectView(R.id.sexLL)
    LinearLayout sexLL;
    @InjectView(R.id.birthTv)
    TextView birthTv;
    @InjectView(R.id.birthLL)
    LinearLayout birthLL;
    @InjectView(R.id.loginCardView)
    CardView loginCardView;
    @InjectView(R.id.agreeLogo)
    ImageView agreeLogo;
    @InjectView(R.id.agreeLL)
    LinearLayout agreeLL;
    @InjectView(R.id.infoCompleteLL)
    LinearLayout infoCompleteLL;
    @InjectView(R.id.phoneET)
    EditText phoneET;
    @InjectView(R.id.phoneLL)
    LinearLayout phoneLL;
    @InjectView(R.id.codeET)
    EditText codeET;
    @InjectView(R.id.codeSend)
    TextView codeSend;
    @InjectView(R.id.codeLL)
    LinearLayout codeLL;
    @InjectView(R.id.mPhoneLL)
    LinearLayout mPhoneLL;
    private String imgPath;
    private String nickName;
    private String birthDay;
    private String address = "";
    private LocationHelper locationHelper;
    private PhotoChoicePop mPhotoChoicePop;
    private int age;
    private UserEntity userEntity;
    private int sex = 0;
    private double lat;
    private double lng;

    @Override
    public int getRootViewId() {
        return R.layout.activity_info_complete;
    }

    @Override
    public void setViews() {
        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    InfoCompleteActivity.this.lat = lat;
                    InfoCompleteActivity.this.lng = lng;
                    address = city;
                    AppLog.e("=========address=========" + address);
                }
            });
            locationHelper.start();
        }
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            userEntity = ((UserEntity) getIntent().getSerializableExtra("userEntity"));
            if (!TextUtils.isEmpty(userEntity.getHeadUrl()))
                imgPath = userEntity.getHeadUrl();
            mHeadImg.setImageURI(userEntity.getHeadUrl());
            if (!TextUtils.isEmpty(userEntity.getNickName()))
                nameEv.setText(userEntity.getNickName());
            if (userEntity.getSex() != 0) {
                sex = userEntity.getSex();
                sexTv.setText(userEntity.getSex() == 1 ? "男" : "女");
            }
            if (!TextUtils.isEmpty(userEntity.getBirthDay())) {
                birthTv.setText(userEntity.getBirthDay());
            }
        }
    }

    @OnClick({R.id.backFL, R.id.photoFL, R.id.sexLL, R.id.birthLL, R.id.loginCardView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.photoFL:
                showAlertChooser();
                break;
            case R.id.sexLL:
                KeyBoardUtils.hideSoftInput(nameEv);
                showSexPopWindow();
                break;
            case R.id.birthLL:
                KeyBoardUtils.hideSoftInput(nameEv);
                new AlertDatePicker.Builder(InfoCompleteActivity.this)
                        .setTitle(getResources().getString(R.string.starByBirth))
                        .setDateSelectListener(new AlertDatePicker.OnDateSelectListener() {
                            @Override
                            public void endSelect(Date selectDate, String selectDateStr, boolean isCancel) {
                                age = TimeUtils.getAgeFromBirthTime(selectDate.getTime());
                                birthDay = selectDateStr;
                                birthTv.setText(selectDateStr);
                            }
                        }).show();
                break;
            case R.id.loginCardView:
                nickName = nameEv.getText().toString().trim();
                if (TextUtils.isEmpty(imgPath)) {
                    ToastUtil.shortShowToast("请先上传您的照片...");
                } else if (TextUtils.isEmpty(nickName)) {
                    ToastUtil.shortShowToast("请先输入您的昵称...");
                } else if (nickName.contains("代充") || nickName.contains("官方")) {
                    ToastUtil.longShowToast("个人昵称中不可出现“代充”、“官方”等关键字...");
                } else if (sex == 0) {
                    ToastUtil.shortShowToast("请先选择您的性别...");
                } else if (TextUtils.isEmpty(birthDay)) {
                    ToastUtil.shortShowToast("请先选择您的生日...");
                } else {
                    nickName = Tools.stringFilter(nickName);
                    updateUser();
                }
                break;
        }
    }

    private void updateUser() {
        AppLog.e("============jpushId===========" + JPushInterface.getRegistrationID(this));
        Tools.showDialog(this);
        NetUtils.getInstance().initUserInfo(userEntity.getOtherType(), userEntity.getOtherToken(), userEntity.getPhone(), "" + lat,
                "" + lng, nickName, imgPath, age, JPushInterface.getRegistrationID(this), birthDay, sex, address, new NetCallBack() {
                    @Override
                    public void onSuccess(String result, String msg, ResultModel model) {
                        User user = model.getModel();
                        JPushInterface.setAlias(InfoCompleteActivity.this, (int) user.getUserId(), user.getUserId() + "");
                        MyApplication.getInstance().saveUser(user);
                        ReYunGame.setRegisterWithAccountID(userEntity.getPhone(), userEntity.getOtherType() == 1 ? ReYunGame.AccountType.QQ.name() : (userEntity.getOtherType() == 2 ? ReYunGame.AccountType.WECHAT.name() : ReYunGame.AccountType.REGISTERED.name()),
                                sex == 1 ? ReYunGame.Gender.M : ReYunGame.Gender.F, age + "", "qianyu", nickName);
                        if (!EMClient.getInstance().isLoggedInBefore()) {
                            loginHX(user);
                        } else {
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast(msg);
                            Intent intent = new Intent(InfoCompleteActivity.this, GuideNewActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFail(String code, String msg, String result) {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
                    }
                }, User.class);
    }

    public void loginHX(final User user) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Tools.dismissWaitDialog();
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user.getPhone())) {
            Tools.dismissWaitDialog();
            MyApplication.getInstance().saveUser(null);
            ToastUtil.shortShowToast("环信账号为空");
            return;
        }
        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();
        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(user.getNickName());
        EMClient.getInstance().login(user.getPhone(), "qianyu123456", new EMCallBack() {

            @Override
            public void onSuccess() {
                Tools.dismissWaitDialog();
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        user.getNickName());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                Intent intent = new Intent(InfoCompleteActivity.this, GuideNewActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                AppLog.e("=============hx=login: onProgress==================");
            }

            @Override
            public void onError(final int code, final String message) {
                AppLog.e("=============hx=login: onError==================" + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Tools.dismissWaitDialog();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showSexPopWindow() {
        sex = 1;
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.layout_sex_select)
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
                        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
                        TextView confirm_tv = view.findViewById(R.id.confirm_tv);
                        FrameLayout manSexFL = view.findViewById(R.id.manSexFL);
                        FrameLayout womanSexFL = view.findViewById(R.id.womanSexFL);
                        final ImageView manChoseLogo = view.findViewById(R.id.manChoseLogo);
                        final ImageView womanChoseLogo = view.findViewById(R.id.womanChoseLogo);
                        cancel_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        confirm_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                        manSexFL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                manChoseLogo.setVisibility(View.VISIBLE);
                                womanChoseLogo.setVisibility(View.GONE);
                                sex = 1;
                            }
                        });
                        womanSexFL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                manChoseLogo.setVisibility(View.GONE);
                                womanChoseLogo.setVisibility(View.VISIBLE);
                                sex = 2;
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(infoCompleteLL, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sexTv.setText(sex == 1 ? "男" : "女");
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


    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    AppLog.e("=============imgUrl==========" + photoInfo.getPhotoPath());
//                    Tools.showDialog(InfoCompleteActivity.this);
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
                                                imgPath = jsonObject.getString("data");
                                                ToastUtil.shortShowToast("上传成功!");
                                                Tools.dismissWaitDialog();
                                                mHeadImg.setImageURI(TextUtils.isEmpty(imgPath) ? "" : imgPath);
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
        mPhotoChoicePop.showPop(photoFL);
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
                                        imgPath = jsonObject.getString("data");
                                        ToastUtil.shortShowToast("上传成功!");
                                        Tools.dismissWaitDialog();
                                        mHeadImg.setImageURI(TextUtils.isEmpty(imgPath) ? "" : imgPath);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationHelper != null) {
            locationHelper.stop();
        }
    }
}
