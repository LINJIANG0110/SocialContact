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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.FamilyOver;
import com.qianyu.communicate.entity.MediaList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.TimeUtils;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;

import net.neiquan.applibrary.utils.ImageUtil;
import net.neiquan.applibrary.utils.PixelUtil;
import net.neiquan.applibrary.wight.AlertChooser;

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

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class ProductionEditActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.mTimeTv)
    TextView mTimeTv;
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
    private String freeType = "2";
    private int mediaId = 5;
    private MediaList mediaList;
    private FamilyOver familyOver;

    @Override
    public int getRootViewId() {
        return R.layout.activity_production_edit;
    }

    @Override
    public void setViews() {
        setTitleTv("编辑信息");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        Spanned spanned = Html.fromHtml("<font color='#545454'><u>" + TimeUtils.getTime(System.currentTimeMillis()) + "</u></font>");
        mTimeTv.setText(spanned);
//        Spanned spanned1 = Html.fromHtml("<font color='#fd5522'><u>9.00</u></font>");
//        mMoneyNum.setText(spanned1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = PixelUtil.getScreenWidth(this) - PixelUtil.dp2px(this, 40);
        layoutParams.height = layoutParams.width * 9 / 16;
        layoutParams.topMargin = layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.rightMargin = 20;
        layoutParams.gravity = Gravity.CENTER;
        showLiveImg.setLayoutParams(layoutParams);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            mediaList = ((MediaList) getIntent().getSerializableExtra("mediaList"));
            familyOver = ((FamilyOver) getIntent().getSerializableExtra("familyOver"));
            if (mediaList != null) {
                mediaId = mediaList.getMediaId();
                imgPath = mediaList.getMediaPic();
                ImageUtil.loadPicNet(imgPath, showLiveImg);
                mNameEt.setText(TextUtils.isEmpty(mediaList.getMediaName()) ? "" : mediaList.getMediaName());
                freeType = mediaList.getFreeType();
                changeMoneyBg();
                if (!TextUtils.isEmpty(freeType)) {
                    if (TextUtils.equals("2", freeType)) {
                        mMoneyNumLL.setVisibility(View.VISIBLE);
                    } else {
                        mMoneyNumLL.setVisibility(View.GONE);
                    }
                }
                mMoneyNum.setText(mediaList.getPrice() + "");
            }
            if (familyOver != null) {
                FamilyOver.FamilyBean family = familyOver.getFamily();
                if (family != null) {
                    mediaId = family.getFid();
                    imgPath = family.getFamilyPath();
                    ImageUtil.loadPicNet(imgPath, showLiveImg);
                    mNameEt.setText(TextUtils.isEmpty(family.getFamilyName()) ? "" : family.getFamilyName());
                    freeType = family.getFreeType();
                    changeMoneyBg();
                    if (!TextUtils.isEmpty(freeType)) {
                        if (TextUtils.equals("2", freeType)) {
                            mMoneyNumLL.setVisibility(View.VISIBLE);
                        } else {
                            mMoneyNumLL.setVisibility(View.GONE);
                        }
                    }
                    mMoneyNum.setText(family.getPrice() + "");
                }
            }
        }
    }

    @OnClick({R.id.startLive, R.id.showLiveImg, R.id.mAudioLL, R.id.mVideoLL, R.id.mFreeLL, R.id.mMoneyLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startLive:
                if (mediaList != null) {
                    startLive();
                }
                if (familyOver != null) {
                    startLive_();
                }
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
        }

    }

    private void startLive() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(ProductionEditActivity.this, RegistActivity.class));
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
        String price = mMoneyNum.getText().toString().trim();
        double mPrice = 0;
        if (!TextUtils.isEmpty(price)) {
            mPrice = Double.parseDouble(price);
        }
        if (mPrice == 0 && TextUtils.equals("2", freeType)) {
            ToastUtil.shortShowToast("请先输入价格...");
            return;
        }
//        Tools.showDialog(this);
        //mediaId 需要调整
    }

    private void startLive_() {
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(ProductionEditActivity.this, RegistActivity.class));
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
        String price = mMoneyNum.getText().toString().trim();
        double mPrice = 0;
        if (!TextUtils.isEmpty(price)) {
            mPrice = Double.parseDouble(price);
        }
        if (mPrice == 0 && TextUtils.equals("2", freeType)) {
            ToastUtil.shortShowToast("请先输入价格...");
            return;
        }
//        Tools.showDialog(this);
        //mediaPath 资源路径，需调整
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
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
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
//                    Tools.showDialog(ProductionEditActivity.this);
                    BitmapUtis.compress(photoInfo.getPhotoPath(), 480, 800, new BitmapUtis.CompressCallback() {
                        @Override
                        public void onsucces(String s) {
//                            NetUtils.getInstance().imageUpload(new File(s), new UploadListener() {
//                                @Override
//                                public void onUISuccess(final String res) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            AppLog.e("==========onUISuccess=======" + res);
//                                            ToastUtil.shortShowToast("上传成功!");
//                                            Tools.dismissWaitDialog();
//                                            imgPath = res;
//                                            ImageUtil.loadPicNet(imgPath, showLiveImg);
//
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onUIFailure(Exception e) {
//                                    ToastUtil.shortShowToast("上传失败!");
//                                    Tools.dismissWaitDialog();
//                                    AppLog.e("==========onUIFailure=======" + e.getMessage());
//                                }
//
//                                @Override
//                                public void onUIProgress(Progress progress) {
//
//                                }
//                            });
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
