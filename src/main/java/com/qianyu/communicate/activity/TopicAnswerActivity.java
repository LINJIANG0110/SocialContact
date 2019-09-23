package com.qianyu.communicate.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codingending.popuplayout.PopupLayout;
import com.hys.utils.ToastUtils;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.bukaSdk.popwindows.PhotoChoicePop;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

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
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;
import jp.wasabeef.richeditor.RichEditor;

/**
 * 话题回答
 */
public class TopicAnswerActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @InjectView(R.id.tv_title)
    TextView titleTv;
    @InjectView(R.id.back_img)
    ImageView ivBack;
    @InjectView(R.id.editor)
    RichEditor mEditor;
    @InjectView(R.id.preview)
    TextView mPreview;
    @InjectView(R.id.titleEt)
    EditText etTitle;
    String topicId;
    private PhotoChoicePop mPhotoChoicePop;
    private final int REQUEST_CODE_CAMERA = 1000;
    private static final int STORAGE_PERMISSION = 10001;
    public static final int CAMERA_PERMISSION = 10002;
    String styleStr = " style=\"line-height:26px;letter-spacing:0.5px;color:#4a4a4a;font-size:16px\"";// 这里动态设置行间距及字体间距

    @Override
    public int getRootViewId() {
        return R.layout.activity_topic_answer;
    }

    @Override
    public void setViews() {
        topicId = getIntent().getStringExtra("topicId");
        String topicTitle = getIntent().getStringExtra("topicTitle");
        titleTv.setText(topicTitle);
        EventBus.getDefault().register(this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPreview.getText().toString().equals("")) {
                    finish();
                } else {
                    setPopupLayout();
                }
            }
        });
    }

    private void setPopupLayout() {
        View view = View.inflate(this, R.layout.popuplayout_signout, null);
        final PopupLayout popupLayout = PopupLayout.init(this, view);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvTrue = (TextView) view.findViewById(R.id.tv_true);
        tvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLayout.dismiss();
                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLayout.dismiss();
            }
        });
        popupLayout.setUseRadius(true);
        //从底部弹出
        popupLayout.show(PopupLayout.POSITION_CENTER);
        //默认从底部弹出
        popupLayout.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {

    }

    @Override
    public void initData() {
        initEdit();
    }

    // 发表评论
    private void setEvalue() {
        Tools.showDialog(this);
        NetUtils.getInstance().saveContent(topicId, mPreview.getText().toString(), etTitle.getText().toString(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("话题回答返回结果", result);
                Tools.dismissWaitDialog();
                try {
                    JSONObject job = new JSONObject(result);
                    if (job.optInt("code") == 200) {
                        // 这里通知需要刷新的页面刷新
                        EventBuss event = new EventBuss(EventBuss.TOPIC_ANSWER);
                        event.setMessage("");
                        EventBus.getDefault().post(event);
                        ToastUtils.getInstance().show(TopicAnswerActivity.this, "保存成功");
                        finish();
                    } else {
                        ToastUtils.getInstance().show(TopicAnswerActivity.this, job.optString("msg"));
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Log.e("话题回答返回结果", result);
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPreview.getText().toString().equals("")) {
                finish();
            } else {
                setPopupLayout();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initEdit() {
        setTitleTv("回答");
        setNextTv("发布");
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (etTitle.getText().toString().equals("")) {
//                    ToastUtils.getInstance().show(TopicAnswerActivity.this, "请输入标题");
//                    return;
//                }
                if (mPreview.getText().toString().equals("")) {
                    ToastUtils.getInstance().show(TopicAnswerActivity.this, "请填写回答内容");
                    return;
                }
                // Log.e("回答内容", styleStr + mPreview.getText().toString());

                setEvalue();
            }
        });
//        mEditor = new RichEditor(TopicAnswerActivity.this,R.styleable.PullToZoomView);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(16);
        mEditor.setEditorFontColor(Color.rgb(74, 74, 74));
        mEditor.setPadding(16, 10, 16, 10);
        mEditor.setPlaceholder("点击添写回答内容");

        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.e("text", text);
//                mEditor.setHtml("<div" + styleStr + ">" + text + "</div>");
                mPreview.setText("<div" + styleStr + ">" + text + "</div>");
            }
        });

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });
        // 插入图片
        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里走选择相册并设置可裁剪
                selectPicPopuwindow();
//                mEditor.insertImage(imageurl,"dachshund");
//                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
//                        "dachshund");
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }

    private void selectPicPopuwindow() {
        EasyPermissions.requestPermissions(this, "请添加打开相册及存储权限", CAMERA_PERMISSION, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        View view = View.inflate(this, R.layout.populayout_select_photo, null);
        final PopupLayout popupLayout = PopupLayout.init(this, view);
        popupLayout.setHeight(LinearLayout.LayoutParams.MATCH_PARENT, true);
        TextView tvXj = (TextView) view.findViewById(R.id.tv_xj);
        TextView tvXc = (TextView) view.findViewById(R.id.tv_xc);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        popupLayout.setUseRadius(false);
        //从底部弹出
        popupLayout.show(PopupLayout.POSITION_BOTTOM);
        //默认从底部弹出
        popupLayout.show();
        tvXc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestExternalStorage();
                popupLayout.dismiss();
            }
        });
        tvXj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraStorage();
                popupLayout.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLayout.dismiss();
            }
        });
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

    private void showPhotoChoicePop() {
        if (mPhotoChoicePop == null) {
            mPhotoChoicePop = new PhotoChoicePop(this, photoCallBack);
        }
        mPhotoChoicePop.showPop(etTitle);
    }

    private GalleryFinal.OnHandlerResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHandlerResultCallback() {
        @Override
        public void onHandlerSuccess(int requestCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                final PhotoInfo photoInfo = resultList.get(0);
                if (photoInfo != null) {
                    AppLog.e("=============imgUrl==========" + photoInfo.getPhotoPath());
                    Tools.showDialog(TopicAnswerActivity.this);
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
                                                String imgPath = jsonObject.getString("data");
                                                Log.e("相机回调", imgPath + "相机");
                                                String altStr = "dachshund\" style=\"max-width:100%;margin-top:13px;margin-bottom:13px";// 宽高缩小百分之五十
                                                mEditor.insertImage(imgPath, altStr);// 插入编辑器图片
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

    /**
     * 图片选择器
     */
    private PhotoChoicePop.CallBackPop photoCallBack = new PhotoChoicePop.CallBackPop() {
        @Override
        public void close(String path) {
            BitmapUtis.compress(path, 340, 680, new BitmapUtis.CompressCallback() {
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
                                        String imgPath = jsonObject.getString("data");
                                        Log.e("相册回调", imgPath + "相册");
                                        String altStr = "dachshund\" style=\"max-width:100%;margin-top:13px;margin-bottom:13px";// 宽高缩小百分之五十
                                        mEditor.insertImage(imgPath, altStr);// 插入编辑器图片
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
        ButterKnife.inject(this);
    }
}
