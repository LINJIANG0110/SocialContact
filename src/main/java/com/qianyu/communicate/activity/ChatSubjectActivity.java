package com.qianyu.communicate.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.PublishImgAdapter;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.base.MyBaseAdapter;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.utils.Tools;
import com.qianyu.communicate.views.MyRecylerView;

import net.neiquan.applibrary.wight.AlertChooser;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.BitmapUtis;
import org.haitao.common.utils.KeyBoardUtils;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class ChatSubjectActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.head_view)
    RelativeLayout mHeadView;
    @InjectView(R.id.mContentET)
    EditText mContentET;
    @InjectView(R.id.mRecylerView)
    MyRecylerView mRecylerView;
    private PublishImgAdapter publishImgAdapter;
    private String topicType;
    private String picPath = "";
    private PhotoChoicePop mPhotoChoicePop;

    @Override
    public int getRootViewId() {
        return R.layout.activity_chat_subject;
    }

    @Override
    public void back(View view) {
        super.back(view);
        KeyBoardUtils.hideSoftInput(mContentET);
    }

    @Override
    public void setViews() {
        setTitleTv("主题概要");
        setNextTv("保存");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                publishCircle();
                KeyBoardUtils.hideSoftInput(mContentET);
                String content = mContentET.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.shortShowToast("请输入主题概要内容...");
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("subject",content);
                    setResult(1, intent);
                    finish();
                }
            }
        });
        initRecylerView();
    }

    private void publishCircle() {
        String content = mContentET.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            topicType = "1";
        }
        List<String> data = publishImgAdapter.data;
        if (data != null && data.size() > 1) {
            topicType = "2";
            for (int i = 0; i < data.size() - 1; i++) {
                if (i == data.size() - 2) {
                    picPath += data.get(i);
                } else {
                    picPath += data.get(i) + ",";
                }
            }
        }
        if (!TextUtils.isEmpty(content) && (data != null && data.size() > 1)) {
            topicType = "4";
        }
        if (TextUtils.isEmpty(topicType)) {
            ToastUtil.shortShowToast("请先输入文字或者选择照片...");
            return;
        }
//        Tools.showDialog(this);
        User user = MyApplication.getInstance().user;
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
                if(data.size()>=10){
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
                    BitmapUtis.compress(photoInfo.getPhotoPath(), 480, 800, new BitmapUtis.CompressCallback() {
                        @Override
                        public void onsucces(String s) {
//                            NetUtils.getInstance().imageUpload(new File(s), new UploadListener() {
//
//                                @Override
//                                public void onUISuccess(final String res) {
//                                    AppLog.e("==========onUISuccess=======" + res);
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            ToastUtil.shortShowToast("上传成功!");
//                                            Tools.dismissWaitDialog();
//                                            int size = publishImgAdapter.data.size();
//                                            publishImgAdapter.data.add(size - 1, res);
//                                            publishImgAdapter.notifyDataSetChanged();
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
            BitmapUtis.compress(path, 480, 800, new BitmapUtis.CompressCallback() {
                @Override
                public void onsucces(String s) {
//                    NetUtils.getInstance().imageUpload(new File(s), new UploadListener() {
//
//                        @Override
//                        public void onUISuccess(final String res) {
//                            AppLog.e("==========onUISuccess=======" + res);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ToastUtil.shortShowToast("上传成功!");
//                                    Tools.dismissWaitDialog();
//                                    int size = publishImgAdapter.data.size();
//                                    publishImgAdapter.data.add(size - 1, res);
//                                    publishImgAdapter.notifyDataSetChanged();
//
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onUIFailure(Exception e) {
//                            ToastUtil.shortShowToast("上传失败!");
//                            Tools.dismissWaitDialog();
//                            AppLog.e("==========onUIFailure=======" + e.getMessage());
//                        }
//
//                        @Override
//                        public void onUIProgress(Progress progress) {
//
//                        }
//                    });

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
