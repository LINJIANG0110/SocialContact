package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.PublishImgAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.GlideImageLoader;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MyRecylerView;

import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;

import net.neiquan.applibrary.wight.AlertChooser;
import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;
import net.neiquan.okhttp.listener.Progress;
import net.neiquan.okhttp.listener.UploadListener;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class PublishCircleActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.head_view)
    RelativeLayout mHeadView;
    @InjectView(R.id.mContentET)
    EditText mContentET;
    @InjectView(R.id.mRecylerView)
    MyRecylerView mRecylerView;
    private PublishImgAdapter publishImgAdapter;
    private String picPath = "";
    private PhotoChoicePop mPhotoChoicePop;
    boolean isLoadding = false;
    @Override
    public int getRootViewId() {
        return R.layout.activity_circle_publish;
    }

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mContentET);
    }

    @Override
    public void setViews() {
        setTitleTv("我的圈子");
        setNextTv("发布");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoadding == false){
                    isLoadding = true;
                    publishCircle();
                }
            }
        });
        initRecylerView();
    }

    private void publishCircle() {
        Log.e("shangchuan","shagnchuan");
        User user = MyApplication.getInstance().user;
        if (user == null) {
            ToastUtil.shortShowToast("请先登录...");
            startActivity(new Intent(this, RegistActivity.class));
            finish();
            isLoadding = false;
            return;
        }
        picPath = "";
        String content = mContentET.getText().toString().trim();
        List<String> data = publishImgAdapter.data;
        if (data != null && data.size() > 1) {
            for (int i = 0; i < data.size() - 1; i++) {
                if (i == data.size() - 2) {
                    picPath += data.get(i);
                } else {
                    picPath += data.get(i) + ",";
                }
            }
        }
        if (TextUtils.isEmpty(content) && TextUtils.isEmpty(picPath)) {
            ToastUtil.shortShowToast("请先输入文字或者上传照片...");
            isLoadding = false;
            return;
        }
        Tools.showDialog(this);
        NetUtils.getInstance().publishCircle(user.getUserId() + "", content, picPath, "", new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                isLoadding = false;
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
                EventBus.getDefault().post(new EventBuss(EventBuss.FRIEND_CIRCLE));
                finish();
            }

            @Override
            public void onFail(String code, String msg, String result) {
                isLoadding = false;
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    private void initRecylerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        publishImgAdapter = new PublishImgAdapter(this, null);
        mRecylerView.setAdapter(publishImgAdapter);
        mRecylerView.setNestedScrollingEnabled(false);
        publishImgAdapter.appendSingle(addImg);
    }

    @Override
    public void initData() {
        publishImgAdapter.setOnItemClickListener(new MyBaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, List data, int position) {
                if (data.size() >= 10) {
                    ToastUtil.shortShowToast("最多只能传九张图片哦...");
                    return;
                }
                if (position == data.size() - 1) {
                    showAlertChooser();
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
            FunctionConfig functionConfig = new FunctionConfig.Builder()
                    .setMutiSelectMaxSize(9)
                    .setEnableRotate(true)
                    .setEnablePreview(true)
                    .build();
            //可设置项，如下
//        setMutiSelect(boolean)//配置是否多选
//        setMutiSelectMaxSize(int maxSize)//配置多选数量
//        setEnableEdit(boolean)//开启编辑功能
//        setEnableCrop(boolean)//开启裁剪功能
//        setEnableRotate(boolean)//开启选择功能
//        setEnableCamera(boolean)//开启相机功能
//        setCropWidth(int width)//裁剪宽度
//        setCropHeight(int height)//裁剪高度
//        setCropSquare(boolean)//裁剪正方形
//        setSelected(List)//添加已选列表,只是在列表中默认呗选中不会过滤图片
//        setFilter(List list)//添加图片过滤，也就是不在GalleryFinal中显示
//        takePhotoFolter(File file)//配置拍照保存目录，不做配置的话默认是/sdcard/DCIM/GalleryFinal/
//        setRotateReplaceSource(boolean)//配置选择图片时是否替换原始图片，默认不替换
//        setCropReplaceSource(boolean)//配置裁剪图片时是否替换原始图片，默认不替换
//        setForceCrop(boolean)//启动强制裁剪功能,一进入编辑页面就开启图片裁剪，不需要用户手动点击裁剪，此功能只针对单选操作
//        setForceCropEdit(boolean)//在开启强制裁剪功能时是否可以对图片进行编辑（也就是是否显示旋转图标和拍照图标）
//        setEnablePreview(boolean)//是否开启预览功能
//            showPhotoChoicePop();
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY,functionConfig, mOnHanlderResultCallback);
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
            if (resultList != null && resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    final PhotoInfo photoInfo = resultList.get(i);
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
//                                                ToastUtil.shortShowToast("上传成功!");
                                                    Tools.dismissWaitDialog();
                                                    int size = publishImgAdapter.data.size();
                                                    publishImgAdapter.data.add(size - 1, imgPath);
                                                    publishImgAdapter.notifyDataSetChanged();
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
        mPhotoChoicePop.showPop(mHeadView);
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
//                                        ToastUtil.shortShowToast("上传成功!");
                                        Tools.dismissWaitDialog();
                                        int size = publishImgAdapter.data.size();
                                        publishImgAdapter.data.add(size - 1, imgPath);
                                        publishImgAdapter.notifyDataSetChanged();
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
}
