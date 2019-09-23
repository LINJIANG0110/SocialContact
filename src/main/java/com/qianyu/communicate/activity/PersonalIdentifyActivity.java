package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
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
import com.qianyu.communicate.adapter.JobTitleAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.AdeptLabel;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.Tools;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.numberpicker.entity.CityResult;
import net.neiquan.applibrary.numberpicker.view.AlertCityPicker;
import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.applibrary.wight.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class PersonalIdentifyActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.askIndentify)
    TextView askIndentify;
    @InjectView(R.id.choosePhoto)
    TextView choosePhoto;
    @InjectView(R.id.companyImgLL)
    LinearLayout companyImgLL;
    @InjectView(R.id.medicalImgLL)
    LinearLayout medicalImgLL;
    @InjectView(R.id.jobMedicalImgLL)
    LinearLayout jobMedicalImgLL;
    @InjectView(R.id.mPhoneNumber)
    EditText mPhoneNumber;
    @InjectView(R.id.mEMailEt)
    EditText mEMailEt;
    @InjectView(R.id.mRealName)
    EditText mRealName;
    @InjectView(R.id.mIdNumber)
    EditText mIdNumber;
    @InjectView(R.id.mCompanyEt)
    EditText mCompanyEt;
    @InjectView(R.id.mDepartMent)
    EditText mDepartMent;
    @InjectView(R.id.mIntroduction)
    EditText mIntroduction;
    @InjectView(R.id.mSpeciality)
    EditText mSpeciality;
    @InjectView(R.id.mCompanyImg)
    ImageView mCompanyImg;
    @InjectView(R.id.mMedicalImg)
    ImageView mMedicalImg;
    @InjectView(R.id.mJobMedicalImg)
    ImageView mJobMedicalImg;
    @InjectView(R.id.mJobTitleLL)
    LinearLayout mJobTitleLL;
    @InjectView(R.id.mJobTitle)
    EditText mJobTitle;
    @InjectView(R.id.mRootView)
    LinearLayout mRootView;
    @InjectView(R.id.mAddressLL)
    LinearLayout mAddressLL;
    @InjectView(R.id.mAddressTv)
    TextView mAddressTv;
    @InjectView(R.id.mGoodAtLL)
    LinearLayout mGoodAtLL;
    @InjectView(R.id.mGoodAtTv)
    TextView mGoodAtTv;
    private String img1, img2, img3, img4;
    private int type;
    private List<AdeptLabel> impressLabels;
    private String cityName;

    @Override
    public int getRootViewId() {
        return R.layout.activity_personal_indentify;
    }

    @Override
    public void setViews() {
        EventBus.getDefault().register(this);
        setTitleTv("名医认证");
        askIndentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalIdentifyActivity.this, ChatRoomCretaeActivity.class));
                finish();
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
        if (event.getState() == EventBuss.GOOD_TAG) {
            impressLabels = ((List<AdeptLabel>) event.getMessage());
            if (impressLabels != null && impressLabels.size() > 0) {
                String labels = "";
                for (int i = 0; i < impressLabels.size(); i++) {
                    if (i == impressLabels.size() - 1) {
                        labels += impressLabels.get(i).getAdeptName();
                    } else {
                        labels += impressLabels.get(i).getAdeptName() + ",";
                    }
                }
                mGoodAtTv.setText(labels);
            }

        }
    }

    @Override
    public void initData() {
        mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");
    }

    @OnClick({R.id.mAddressLL, R.id.mGoodAtLL, R.id.choosePhoto, R.id.companyImgLL, R.id.medicalImgLL, R.id.jobMedicalImgLL, R.id.askIndentify, R.id.mJobTitleLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mAddressLL:
                new AlertCityPicker.Builder(PersonalIdentifyActivity.this)
                        .setTitle("选择您所在的城市")
                        .setOnCitySelectListener(new AlertCityPicker.OnCitySelectListener() {
                            @Override
                            public void endSelect(CityResult cityResult, boolean isCancel) {
                                cityName = cityResult.getCityName();
                                mAddressTv.setText(cityResult.getProviceName() + cityResult.getCityName() + cityResult.getRegionName());
                            }
                        }).show();
                break;
            case R.id.mGoodAtLL:
                startActivity(new Intent(PersonalIdentifyActivity.this, GoodTagActivity.class));
                break;
            case R.id.choosePhoto:
                type = 1;
                showAlertChooser();
                break;
            case R.id.companyImgLL:
                type = 2;
                showAlertChooser();
                break;
            case R.id.medicalImgLL:
                type = 3;
                showAlertChooser();
                break;
            case R.id.jobMedicalImgLL:
                type = 4;
                showAlertChooser();
                break;
            case R.id.askIndentify:
                KeyBoardUtils.hideSoftInput(mIntroduction);
                updateUser();
                break;
            case R.id.mJobTitleLL:
//                showJobTitlePopWindow();
                break;
        }
    }

    private void updateUser() {
        String phone = mPhoneNumber.getText().toString().trim();
//        if (!RegexValidateUtil.checkCellphone(phone)) {
//            ToastUtil.shortShowToast("请先输入正确的手机号...");
//            return;
//        }
        String eMail = mEMailEt.getText().toString().trim();
//        if (!isEmail(eMail)) {
//            ToastUtil.shortShowToast("请先输入正确的邮箱...");
//            return;
//        }
        String realName = mRealName.getText().toString().trim();
        if (TextUtils.isEmpty(realName)) {
            ToastUtil.shortShowToast("请先输入您的真实姓名...");
            return;
        }
        String idNum = mIdNumber.getText().toString().trim();
//        if (!personIdValidation(idNum)) {
//            ToastUtil.shortShowToast("请先输入正确的身份证号码...");
//            return;
//        }
        String company = mCompanyEt.getText().toString().trim();
//        if (TextUtils.isEmpty(company)) {
//            ToastUtil.shortShowToast("请先输入您的工作单位...");
//            return;
//        }
        String departMent = mDepartMent.getText().toString().trim();
//        if (TextUtils.isEmpty(departMent)) {
//            ToastUtil.shortShowToast("请先输入您的具体科室...");
//            return;
//        }
        String jobTitle = mJobTitle.getText().toString().trim();
//        if (TextUtils.isEmpty(jobTitle)) {
//            ToastUtil.shortShowToast("请先输入您的职称...");
//            return;
//        }
        String introuduction = mIntroduction.getText().toString().trim();
        if (TextUtils.isEmpty(introuduction)) {
            ToastUtil.shortShowToast("请先输入您的简介...");
            return;
        }
        String special = mSpeciality.getText().toString().trim();
//        if (TextUtils.isEmpty(special)) {
//            ToastUtil.shortShowToast("请先输入您的擅长领域...");
//            return;
//        }
//        if (TextUtils.isEmpty(img1)) {
//            ToastUtil.shortShowToast("请先输上传您的手持身份证照片...");
//            return;
//        }
//        if (TextUtils.isEmpty(img2)) {
//            ToastUtil.shortShowToast("请先输上传您的单位证件...");
//            return;
//        }
//        if (TextUtils.isEmpty(img3)) {
//            ToastUtil.shortShowToast("请先输上传您的医师资格证...");
//            return;
//        }
//        if (TextUtils.isEmpty(img4)) {
//            ToastUtil.shortShowToast("请先输上传您的职业医师资格证...");
//            return;
//        }
        String address = mAddressTv.getText().toString().trim();
        if (TextUtils.isEmpty(cityName)) {
            ToastUtil.shortShowToast("请先选择您的所在城市...");
            return;
        }
        String good = mGoodAtTv.getText().toString().trim();
        if (TextUtils.isEmpty(good)) {
            ToastUtil.shortShowToast("请先选中您的擅长领域...");
            return;
        }
        if (!MyApplication.getInstance().isLogin()) {
            return;
        }
        final User user = MyApplication.getInstance().user;
        final String token = user.getToken();
    }

    private void showIdentifyPopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(PersonalIdentifyActivity.this)
                //设置PopupWindow布局
                .setView(R.layout.identify_pop_window)
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
                        ImageView mStatusImg = (ImageView) view.findViewById(R.id.mStatusImg);
                        TextView mStatusTv = (TextView) view.findViewById(R.id.mStatusTv);
                        TextView mDetailTv = (TextView) view.findViewById(R.id.mDetailTv);
                        TextView mEnsureTv = (TextView) view.findViewById(R.id.mEnsureTv);
                        TextView mCancelTv = (TextView) view.findViewById(R.id.mCancelTv);
                        mStatusImg.setImageResource(R.drawable.tijiaoshenhe_export_export);
                        mStatusTv.setText("审核中...");
                        mDetailTv.setText("您提交的认证正在审核，审核期为1-2个工作日，审核结果将通过短信告知");
                        mCancelTv.setVisibility(View.GONE);
                        mEnsureTv.setText("完成");
                        mCancelTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        mEnsureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                                finish();
                            }
                        });
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(true)
                //开始构建
                .create();
        //弹出PopupWindow
        popupWindow.showAtLocation(askIndentify, Gravity.CENTER, 0, 0);
    }

    public boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String regx1 = "[0-9]{17}X";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(regx1) || text.matches(reg1) || text.matches(regex);
    }

    private void showJobTitlePopWindow() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(R.layout.job_title_pop)
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
                        RecyclerView mRecylerView = view.findViewById(R.id.mRecylerView);
                        initRecylerView(mRecylerView);
                        final JobTitleAdapter oderPopAdapter = new JobTitleAdapter(PersonalIdentifyActivity.this, null);
                        mRecylerView.setAdapter(oderPopAdapter);
                        oderPopAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, List data, int position) {
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
        popupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
    }

    private void initRecylerView(RecyclerView mRecylerView) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(layoutManager);
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


    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {

        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    AppLog.e("=============imgUrl==========" + photoInfo.getPhotoPath());
//                    Tools.showDialog(PersonalIdentifyActivity.this);
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
//                                            switch (type) {
//                                                case 1:
//                                                    img1 = res;
//                                                    mHeadImg.setImageURI(img1);
//                                                    break;
//                                                case 2:
//                                                    img2 = res;
//                                                    ImageUtil.loadPicNet(img2, mCompanyImg);
//                                                    break;
//                                                case 3:
//                                                    img3 = res;
//                                                    ImageUtil.loadPicNet(img3, mMedicalImg);
//                                                    break;
//                                                case 4:
//                                                    img4 = res;
//                                                    ImageUtil.loadPicNet(img4, mJobMedicalImg);
//                                                    break;
//                                            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
