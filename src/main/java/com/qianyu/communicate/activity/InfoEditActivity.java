package com.qianyu.communicate.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hys.utils.AppUtils;
import com.hys.utils.DensityUtils;
import com.hys.utils.ImageUtils;
import com.hys.utils.InitCacheFileUtils;
import com.hys.utils.SdcardUtils;
import com.hys.utils.ToastUtils;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.PostArticleImgAdapter;
import com.qianyu.communicate.adapter.TagAdapter_;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.ImpressLabel;
import com.qianyu.communicate.entity.PersonalInfo;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.entity.UserDetail;
import com.qianyu.communicate.entity.WorkTag;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.image.ImagePreviewActivity;
import com.qianyu.communicate.utils.LocationHelper;
import com.qianyu.communicate.utils.MyCallBack;
import com.qianyu.communicate.utils.OnRecyclerItemClickListener;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;

import net.neiquan.applibrary.flowtag.FlowTagLayout;
import net.neiquan.applibrary.flowtag.OnTagSelectListener;
import net.neiquan.applibrary.numberpicker.view.AlertDatePicker;
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
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class InfoEditActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, PostArticleImgAdapter.ItemClickListener {
    @InjectView(R.id.tv_photos)
    TextView tvPhotosHint;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mHeadRv)
    RelativeLayout mHeadRv;
    @InjectView(R.id.nickTv)
    TextView nickTv;
    @InjectView(R.id.mNickName)
    EditText mNickName;
    @InjectView(R.id.mTallET)
    EditText mTallET;
    @InjectView(R.id.mWeightET)
    EditText mWeightET;
    @InjectView(R.id.mAgeTv)
    TextView mAgeTv;
    @InjectView(R.id.loveStateTv)
    TextView loveStateTv;
    @InjectView(R.id.mAgeRv)
    RelativeLayout mAgeRv;
    @InjectView(R.id.jobTv)
    TextView jobTv;
    @InjectView(R.id.mJobTv)
    TextView mJobTv;
    @InjectView(R.id.mJobRv)
    RelativeLayout mJobRv;
    @InjectView(R.id.mTagRv)
    RelativeLayout mTagRv;
    @InjectView(R.id.mSignEt)
    EditText mSignEt;
    @InjectView(R.id.mHobbyEt)
    EditText mHobbyEt;
    @InjectView(R.id.mTagFlowLayout)
    FlowTagLayout mTagFlowLayout;
    @InjectView(R.id.sexTv)
    TextView sexTv;
    @InjectView(R.id.sexLL)
    LinearLayout sexLL;
    @InjectView(R.id.loveStateLL)
    LinearLayout loveStateLL;
    private TagAdapter_<String> adapter;
    private LocationHelper locationHelper;
    private PhotoChoicePop mPhotoChoicePop;
    private String imgPath = "";
    private String address = "";
    private int sex = 0;
    private int userState = 0;
    private long proId = 0;
    private String label = "";
    // 照片墙逻辑有点复杂
    private String examinePic = "";// 照片正常顺序
    private String unExaminePic = "";// 新上传的图片

    // 照片墙
    private ArrayList<String> netUrlData = new ArrayList<>();
    // 仿微信发朋友圈选图及拖动排序删除
    @InjectView(R.id.rcv_img)
    RecyclerView rcvImg;
    @InjectView(R.id.tv)
    TextView tv;//删除区域提示
    public static final String FILE_DIR_NAME = "com.kuyue.wechatpublishimagesdrag";//应用缓存地址
    public static final String FILE_IMG_NAME = "images";//放置图片缓存
    public static final int IMAGE_SIZE = 4;//可添加图片最大数
    private static final int REQUEST_IMAGE = 1002;
    private ArrayList<String> originImages;//原始图片
    private ArrayList<String> dragImages;//压缩长宽后图片
    private Context mContext;
    private PostArticleImgAdapter postArticleImgAdapter;
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mNickName);
        KeyBoardUtils.hideSoftInput(mSignEt);
    }

    @Override
    public int getRootViewId() {
        return R.layout.activity_info_edit;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("编辑资料");
        setNextTv("保存");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String photosStr = "";
                if (null != dragImages && dragImages.size() > 1) {
                    for (int i = 0; i < dragImages.size() - 1; i++) {
                        if (photosStr.equals("")) {
                            photosStr = dragImages.get(i);
                        } else {
                            photosStr = photosStr + "," + dragImages.get(i);
                        }
                    }
                }
                // 正常顺序路径集合
                examinePic = photosStr;// 照片正常顺序
                // 打印照片墙入参
                KeyBoardUtils.hideSoftInput(mNickName);
                KeyBoardUtils.hideSoftInput(mSignEt);
                // 没有新增的则不传
                updatePhotos();
            }
        });

        locationHelper = LocationHelper.getInstance();
        if (locationHelper != null) {
            locationHelper.setCallBack(new LocationHelper.LocationCallBack() {
                @Override
                public void callBack(String addr, String streetnumber, double lat, double lng, String district, String street, String city, String province) {
                    address = city;
                }
            });
            locationHelper.start();
        }
    }

    private void updatePhotos() {
        if (null == examinePic) {
            examinePic = "";
        }
        if (null == unExaminePic) {
            unExaminePic = "";
        }
        // 先走上传照片墙在走更新用户信息
        User user = MyApplication.getInstance().user;
        NetUtils.getInstance().updatePhotos(user.getUserId(), examinePic, unExaminePic, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("照片墙返回结果", result);
                // 更新成功后更新用户信息
                updateUser();
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {
        if (event.getState() == EventBuss.IMPRESS_TAG) {
            label = (String) event.getMessage();
            if (!TextUtils.isEmpty(label)) {
                List<String> userLabels = new ArrayList<>();
                String[] strings = label.split(",");
                for (int i = 0; i < strings.length; i++) {
                    userLabels.add(strings[i]);
                }
                adapter.clearAndAddAll(userLabels);
            }
        } else if (event.getState() == EventBuss.WORK_TAG) {
            WorkTag workTag = ((WorkTag) event.getMessage());
            mJobTv.setText(workTag.getProName());
            proId = workTag.getProId();
        }
    }

    @Override
    public void initData() {
        initTagRecylerView();
        loadDatas();
    }

    private void loadDatas() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(InfoEditActivity.this, RegistActivity.class));
        } else {
            NetUtils.getInstance().mineInfo(user.getUserId(), new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    Log.e("用户信息", result);
                    PersonalInfo personalInfo = (PersonalInfo) model.getModel();
                    if (personalInfo != null) {
                        List<String> mList = new ArrayList<>();
                        // 判空
                        if (null != personalInfo.getExaminePic() && !"".equals(personalInfo.getExaminePic())) {
                            examinePic = personalInfo.getExaminePic();
                            if (examinePic.contains(",")) {
                                String tempStr[] = examinePic.split(",");
                                for (int i = 0; i < tempStr.length; i++) {
                                    mList.add(tempStr[i]);
                                }
                            } else {
                                mList.add(examinePic);
                            }
                        }
                        if (null != personalInfo.getUnExaminePic()) {
                            unExaminePic = personalInfo.getUnExaminePic();
                        }
                        initPhotos(mList);
                        initRcv();
                        imgPath = personalInfo.getHeadUrl();
                        mHeadImg.setImageURI(TextUtils.isEmpty(personalInfo.getHeadUrl()) ? "" : personalInfo.getHeadUrl());
                        mNickName.setText(TextUtils.isEmpty(personalInfo.getNickName()) ? "" : personalInfo.getNickName());
                        sex = personalInfo.getSex();
                        sexTv.setText(personalInfo.getSex() == 1 ? "男" : "女");
                        mAgeTv.setText(personalInfo.getAge() + "");
                        if (personalInfo.getHeight() > 0)
                            mTallET.setText(personalInfo.getHeight() + "");
                        if (personalInfo.getWeight() > 0)
                            mWeightET.setText(personalInfo.getWeight() + "");
                        userState = personalInfo.getUserState();
                        loveStateTv.setText(userState == 0 ? "未设置" : (personalInfo.getUserState() == 1 ? "单身" : "热恋中"));
                        proId = personalInfo.getProfessionId();
                        mJobTv.setText(TextUtils.isEmpty(personalInfo.getProfessionName()) ? "" : personalInfo.getProfessionName());
                        mSignEt.setText(TextUtils.isEmpty(personalInfo.getDetails()) ? "" : personalInfo.getDetails());
                        label = personalInfo.getLabel();
                        if (!TextUtils.isEmpty(label)) {
                            List<String> userLabels = new ArrayList<>();
                            String[] strings = label.split(",");
                            for (int i = 0; i < strings.length; i++) {
                                userLabels.add(strings[i]);
                            }
                            adapter.clearAndAddAll(userLabels);
                        }
                    }
                }

                @Override
                public void onFail(String code, String msg, String result) {

                }
            }, PersonalInfo.class);
        }
    }

    private void updateUser() {
        String nickName = mNickName.getText().toString().trim();
        if (nickName.contains("代充") || nickName.contains("官方")) {
            ToastUtil.longShowToast("昵称中不可出现“代充”、“官方”等关键字...");
            return;
        }
        int age = Integer.parseInt(mAgeTv.getText().toString().trim());
        String hStr = mTallET.getText().toString().trim();
        double height = TextUtils.isEmpty(hStr) ? 0 : Double.parseDouble(hStr);
        String wStr = mWeightET.getText().toString().trim();
        double weight = TextUtils.isEmpty(wStr) ? 0 : Double.parseDouble(wStr);
        String signature = mSignEt.getText().toString().trim();
        if (MyApplication.getInstance().isLogin()) {
            User user = MyApplication.getInstance().user;
            NetUtils.getInstance().updateInfo(user.getUserId(), Tools.stringFilter(nickName), imgPath, age, sex, height, weight, userState, proId, label, signature, new NetCallBack() {
                @Override
                public void onSuccess(String result, String msg, ResultModel model) {
                    ToastUtil.shortShowToast(msg);
                    EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
                    finish();
                }

                @Override
                public void onFail(String code, String msg, String result) {
                    ToastUtil.shortShowToast(msg);
                }
            }, null);
        }
    }

    private void initTagRecylerView() {
        List<String> impressLabels = new ArrayList<>();
        impressLabels.add("+添加印象");
        mTagFlowLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        adapter = new TagAdapter_<String>(this);
        mTagFlowLayout.setAdapter(adapter);
        adapter.clearAndAddAll(impressLabels);
        mTagFlowLayout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                startActivity(new Intent(InfoEditActivity.this, ImpressTagActivity.class));
            }
        });
    }

    @OnClick({R.id.mHeadRv,R.id.mHeadImg, R.id.mAgeRv, R.id.mJobRv, R.id.mTagRv, R.id.sexLL, R.id.loveStateLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mHeadImg:
            case R.id.mHeadRv:
                showAlertChooser();
                break;
            case R.id.mAgeRv:
                new AlertDatePicker.Builder(InfoEditActivity.this)
                        .setTitle("请选择您的出生日期")
                        .setCancelable(true)
                        .setDateSelectListener(new AlertDatePicker.OnDateSelectListener() {
                            @Override
                            public void endSelect(Date selectDate, String selectDateStr, boolean isCancel) {
                                mAgeTv.setText(TimeUtils.getAgeFromBirthTime(selectDateStr) + "");
                            }
                        }).show();
                break;
            case R.id.mJobRv:
                Intent intent = new Intent(InfoEditActivity.this, WorkTagActivity.class);
                startActivity(intent);
                break;
            case R.id.mTagRv:
                startActivity(new Intent(InfoEditActivity.this, ImpressTagActivity.class));
                break;
            case R.id.sexLL:
                KeyBoardUtils.hideSoftInput(mNickName);
                KeyBoardUtils.hideSoftInput(mSignEt);
                showSexPopWindow();
                break;
            case R.id.loveStateLL:
                new AlertChooser.Builder(this).setTitle("恋爱状态").addItem("单身", new AlertChooser.OnItemClickListener() {
                    @Override
                    public void OnItemClick(AlertChooser chooser) {
                        userState = 1;
                        loveStateTv.setText("单身");
                        chooser.dismiss();
                    }
                }).addItem("热恋中", new AlertChooser.OnItemClickListener() {
                    @Override
                    public void OnItemClick(AlertChooser chooser) {
                        userState = 2;
                        loveStateTv.setText("热恋中");
                        chooser.dismiss();
                    }
                }).setNegativeItem("取消", new AlertChooser.OnItemClickListener() {
                    @Override
                    public void OnItemClick(AlertChooser chooser) {
                        chooser.dismiss();
                    }
                }).show();
                break;
        }
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
        popupWindow.showAtLocation(sexLL, Gravity.BOTTOM, 0, 0);
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
        EasyPermissions.requestPermissions(this, "请添加打开相册及存储权限", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
            showPhotoChoicePop();
        } else {
            EasyPermissions.requestPermissions(this, "请添加打开相册及存储权限", STORAGE_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(CAMERA_PERMISSION)
    public void requestCameraStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
        } else {
            EasyPermissions.requestPermissions(this, "请添加打开相册及存储权限", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    Tools.showDialog(InfoEditActivity.this);
                    BitmapUtis.compress(photoInfo.getPhotoPath(), 600, 600, new BitmapUtis.CompressCallback() {
                        @Override
                        public void onsucces(String s) {
                            NetUtils.getInstance().fileUpload(new File(s), new UploadListener() {

                                @Override
                                public void onUISuccess(final String res) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(res);
                                                imgPath = jsonObject.getString("data");
                                                mHeadImg.setImageURI(TextUtils.isEmpty(imgPath) ? "" : imgPath);
                                                ToastUtil.shortShowToast("上传成功!");
                                                Tools.dismissWaitDialog();
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
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject(res);
                                        imgPath = jsonObject.getString("data");
                                        mHeadImg.setImageURI(TextUtils.isEmpty(imgPath) ? "" : imgPath);
                                        ToastUtil.shortShowToast("上传成功!");
                                        Tools.dismissWaitDialog();
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
        EventBus.getDefault().unregister(this);
        if (locationHelper != null) {
            locationHelper.stop();
        }
        // 选图
        myHandler.removeCallbacksAndMessages(null);
    }

    /***********************以下为图库选片**********************************/
    private void initPhotos(List<String> paramList) {
        originImages = new ArrayList<>();
        if (paramList.size() > 0) {
            originImages.addAll(paramList);
        }
        mContext = getApplicationContext();
        try {
            InitCacheFileUtils.initImgDir(FILE_DIR_NAME, FILE_IMG_NAME);//清除图片缓存
        }catch (Exception e){
            e.printStackTrace();
        }
        //添加按钮图片资源
        String plusPath = getString(R.string.glide_plus_icon_string) + AppUtils.getPackageInfo(mContext).packageName + "/mipmap/" + R.mipmap.mine_btn_plus;
        dragImages = new ArrayList<>();
        originImages.add(plusPath);//添加按键，超过9张时在adapter中隐藏
        netUrlData.addAll(originImages);// 显示的uri数组-与dragImages交互
        dragImages.addAll(originImages);
        new Thread(new MyRunnable(dragImages, originImages, dragImages, myHandler, false)).start();//开启线程，在新线程中去压缩图片
    }

    private MyCallBack myCallBack;

    private void initRcv() {

        postArticleImgAdapter = new PostArticleImgAdapter(mContext, dragImages);
        postArticleImgAdapter.setItemClickListener(this);
        rcvImg.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        rcvImg.setAdapter(postArticleImgAdapter);
        myCallBack = new MyCallBack(postArticleImgAdapter, dragImages, originImages);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(rcvImg);//绑定RecyclerView

        //事件监听
        rcvImg.addOnItemTouchListener(new OnRecyclerItemClickListener(rcvImg) {

            @Override
            public void onItemClick(final RecyclerView.ViewHolder vh) {
                if (originImages.get(vh.getAdapterPosition()).contains(getString(R.string.glide_plus_icon_string))) {//打开相册
                    checkPermission();
                    MultiImageSelector.create()
                            .showCamera(true)
                            .count(IMAGE_SIZE - originImages.size() + 1)
                            .multi()
                            .start(InfoEditActivity.this, REQUEST_IMAGE);
                } else {
                    new AlertChooser.Builder(InfoEditActivity.this).setTitle("选择操作").addItem("删除", new AlertChooser.OnItemClickListener() {
                        @Override
                        public void OnItemClick(AlertChooser chooser) {
                            //如果item不是最后一个，则执行拖拽
                            if (vh.getLayoutPosition() != dragImages.size() - 1) {
                                // vh.itemView.setVisibility(View.INVISIBLE);//先设置不可见，如果不设置的话，会看到viewHolder返回到原位置时才消失，因为remove会在viewHolder动画执行完成后才将viewHolder删除
                                originImages.remove(vh.getAdapterPosition());
                                int mToPosition = vh.getAdapterPosition();
                                dragImages.remove(mToPosition);
                                postArticleImgAdapter.notifyItemRemoved(mToPosition);
                                postArticleImgAdapter.notifyDataSetChanged();
                                myCallBack.dragListener.clearView(mToPosition);
                            }
                            chooser.dismiss();
                        }
                    }).addItem("查看大图", new AlertChooser.OnItemClickListener() {
                        @Override
                        public void OnItemClick(AlertChooser chooser) {
                            priview(vh.getAdapterPosition());
                            chooser.dismiss();
                        }
                    }).setNegativeItem("取消", new AlertChooser.OnItemClickListener() {
                        @Override
                        public void OnItemClick(AlertChooser chooser) {
                            chooser.dismiss();
                        }
                    }).show();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != dragImages.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        // 刷新位置互换接口
        myCallBack.setOnChangeInterface(new MyCallBack.OnChangeInterface()

        {
            @Override
            public void onChange(int fromPosition, int toPosition) {
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(netUrlData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(netUrlData, i, i - 1);
                    }
                }
            }
        });
        myCallBack.setDragListener(new MyCallBack.DragListener()

        {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tv.setBackgroundResource(R.color.holo_red_dark);
                    tv.setText(getResources().getString(R.string.post_delete_tv_s));
                } else {
                    tv.setText(getResources().getString(R.string.post_delete_tv_d));
                    tv.setBackgroundResource(R.color.holo_red_light);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
//                    tv.setVisibility(View.VISIBLE);
                } else {
                    tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView(int delPosition) {
                updateDel(delPosition);
            }
        });
    }

    private void checkPermission() {
        EasyPermissions.requestPermissions(this, "请添加打开相册及存储权限", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    // 点击回调
    public void rvItemOnclick(int position, final RecyclerView.ViewHolder vh) {

    }

    // 删除刷新UI
    private void updateDel(int delPosition) {
        // 如果未审核图片不为空则判断是否有删除的项目-有则在其中也将之删除
        if (!TextUtils.isEmpty(unExaminePic)) {
            // 要删除的url
            String delUrl = netUrlData.get(delPosition);// 要删除的图片
            if (unExaminePic.contains(",")) {
                String unExstr[] = unExaminePic.split(",");
                List<String> tempList = new ArrayList<>();
                for (int i = 0; i < unExstr.length; i++) {
                    tempList.add(unExstr[i]);
                }
                if (tempList.contains(delUrl)) {
                    for (int j = 0; j < tempList.size(); j++) {
                        if (tempList.get(j).equals(delUrl)) {
                            tempList.remove(j);
                            break;
                        }
                    }
                    String tempUnex = "";
                    // 重新组合未审核照片
                    if (tempList.size() > 0) {
                        for (int k = 0; k < tempList.size(); k++) {
                            if (tempUnex.equals("")) {
                                tempUnex = tempList.get(k);
                            } else {
                                tempUnex = tempUnex + "," + tempList.get(k);
                            }
                        }
                    }
                    unExaminePic = tempUnex;
                }
            } else {
                if (delUrl == unExaminePic) {
                    unExaminePic = "";// 赋值为空
                }
            }
        }
        // dragImages删除后同时删除netUrlData中的position
        if (netUrlData.size() > delPosition) {
            netUrlData.remove(delPosition);
        }
        refreshLayout(true, null, null);
    }

    // 查看大图
    private void priview(int pos) {
        if (null != netUrlData && netUrlData.size() > 0) {
            final ArrayList<ImageItem> mImageList = new ArrayList<>();
            for (int i = 0; i < netUrlData.size(); i++) {
                ImageItem item = new ImageItem();
                item.setName("");
                item.setTime(0);
                item.setPath(netUrlData.get(i));
                mImageList.add(item);
            }
            Intent intent3 = new Intent();
            intent3.putExtra(AndroidImagePicker.KEY_PIC_SELECTED_POSITION, pos);
            intent3.setClass(InfoEditActivity.this, ImagePreviewActivity.class);
            intent3.putParcelableArrayListExtra(ImagePreviewActivity.IMAGEURL, mImageList);
            startActivity(intent3);
        }
    }
    //------------------图片相关-----------------------------

    // 照片墙选后回调结果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {//从相册选择完图片
            //压缩图片
            new Thread(new MyRunnable(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT),
                    originImages, dragImages, myHandler, true)).start();
        }
    }

    /**
     * 另起线程压缩图片
     */
    static class MyRunnable implements Runnable {
        ArrayList<String> images;
        ArrayList<String> originImages;
        ArrayList<String> dragImages;
        Handler handler;
        boolean add;//是否为添加图片

        public MyRunnable(ArrayList<String> images, ArrayList<String> originImages, ArrayList<String> dragImages, Handler handler, boolean add) {
            this.images = images;
            this.originImages = originImages;
            this.dragImages = dragImages;
            this.handler = handler;
            this.add = add;
        }

        @Override
        public void run() {
            SdcardUtils sdcardUtils = new SdcardUtils();
            String filePath;
            Bitmap newBitmap;
            int addIndex = originImages.size() - 1;
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).contains(MyApplication.getInstance().getString(R.string.glide_plus_icon_string))) {//说明是添加图片按钮
                    continue;
                }
                //压缩
                newBitmap = ImageUtils.compressScaleByWH(images.get(i),
                        DensityUtils.dp2px(MyApplication.getInstance().getContext(), 100),
                        DensityUtils.dp2px(MyApplication.getInstance().getContext(), 100));
                //文件地址
                filePath = sdcardUtils.getSDPATH() + FILE_DIR_NAME + "/"
                        + FILE_IMG_NAME + "/" + String.format("img_%d.jpg", System.currentTimeMillis());
                //保存图片
                ImageUtils.save(newBitmap, filePath, Bitmap.CompressFormat.JPEG, true);
                //设置值
                if (!add) {
                    images.set(i, filePath);
                } else {//添加图片，要更新
                    dragImages.add(addIndex, filePath);
                    originImages.add(addIndex++, filePath);
                }
            }
            Message message = new Message();
            message.what = 1;
            message.obj = images;
            handler.sendMessage(message);
        }
    }

    //递归下载图片-这里addList为新增的
    private void uploadFile(final int pos, final InfoEditActivity activity, final List<String> addList) {
        final int position = pos;
        if (addList.size() > position) {
            NetUtils.getInstance().fileUpload(new File(addList.get(position)), new UploadListener() {
                @Override
                public void onUISuccess(final String res) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                String imgPath = jsonObject.getString("data");
                                netUrlData.add(netUrlData.size() - 1, imgPath);
                                if (unExaminePic.equals("")) {
                                    unExaminePic = imgPath;
                                } else {
                                    unExaminePic = unExaminePic + "," + imgPath;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // 递归判断
                            if (position < addList.size()) {
                                int pos = position + 1;
                                // 继续循环
                                uploadFile(pos, activity, addList);
                            }
                        }
                    });
                }

                @Override
                public void onUIFailure(Exception e) {
                    Tools.dismissWaitDialog();
                    // 上传失败需要刷新，因为dragImages与netUrlData数据不同，需要同步
                    originImages.clear();
                    originImages.addAll(netUrlData);
                    dragImages.clear();
                    dragImages.addAll(netUrlData);
                    activity.postArticleImgAdapter.setmDatas(dragImages);
                }

                @Override
                public void onUIProgress(Progress progress) {

                }
            });
        } else {
            Tools.dismissWaitDialog();
            dragImages.clear();
            dragImages.addAll(netUrlData);
            activity.postArticleImgAdapter.setmDatas(dragImages);
        }

    }

    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            InfoEditActivity activity = (InfoEditActivity) reference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        List<String> addList = (List<String>) msg.obj;
                        // 这里改为uploadfile以后刷新
                        activity.refreshLayout(false, activity, addList);
                        break;
                }
            }
        }

    }

    /**
     * 刷新地理位置等布局
     */
    private void refreshLayout(boolean isDelete, InfoEditActivity activity, List<String> addList) {
        // 非删除时（新增）上传刷新列表
        if (!isDelete && dragImages.size() > 1) {
            Tools.showDialog(InfoEditActivity.this);
            uploadFile(0, activity, addList);
        }
    }

}
