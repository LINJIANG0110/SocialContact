package com.qianyu.communicate.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyu.communicate.R;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class SuggestBackActivity extends BaseActivity {
    @InjectView(R.id.questContent)
    EditText questContent;
    @InjectView(R.id.consultLogo)
    ImageView consultLogo;
    @InjectView(R.id.consultRv)
    RelativeLayout consultRv;
    @InjectView(R.id.reportLogo)
    ImageView reportLogo;
    @InjectView(R.id.reportRv)
    RelativeLayout reportRv;
    @InjectView(R.id.suggestLogo)
    ImageView suggestLogo;
    @InjectView(R.id.suggestRv)
    RelativeLayout suggestRv;
    @InjectView(R.id.mCustomQQ)
    LinearLayout mCustomQQ;
    private String problemType = "1";

    @Override
    public int getRootViewId() {
        return R.layout.activity_suggest_back;
    }

    @Override
    public void setViews() {
        setTitleTv("意见反馈");
        setNextTv("确认");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = questContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.shortShowToast("请先输入您要反馈的内容...");
                    return;
                }
                User user = MyApplication.getInstance().user;
                if (user != null) {
                    NetUtils.getInstance().suggestBack(user.getUserId(), content, "", new NetCallBack() {
                        @Override
                        public void onSuccess(String result, String msg, ResultModel model) {
                            ToastUtil.shortShowToast(msg);
                            finish();
                        }

                        @Override
                        public void onFail(String code, String msg, String result) {
                            ToastUtil.shortShowToast(msg);
                        }
                    }, null);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.consultRv, R.id.reportRv, R.id.suggestRv,R.id.mCustomQQ})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.consultRv:
                problemType = "1";
                changeSelector();
                break;
            case R.id.reportRv:
                problemType = "2";
                changeSelector();
                break;
            case R.id.suggestRv:
                problemType = "3";
                changeSelector();
                break;
            case R.id.mCustomQQ:
                if (checkApkExist(SuggestBackActivity.this, "com.tencent.mobileqq")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2575764746&version=1")));
                } else {
                    Toast.makeText(SuggestBackActivity.this, "本机未安装QQ应用", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void changeSelector() {
        switch (problemType) {
            case "1":
                consultLogo.setImageResource(R.mipmap.wxdl_xuanze);
                reportLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                suggestLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                break;
            case "2":
                consultLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                reportLogo.setImageResource(R.mipmap.wxdl_xuanze);
                suggestLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                break;
            case "3":
                consultLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                reportLogo.setImageResource(R.mipmap.wxdl_xuanzehui);
                suggestLogo.setImageResource(R.mipmap.wxdl_xuanze);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

}
