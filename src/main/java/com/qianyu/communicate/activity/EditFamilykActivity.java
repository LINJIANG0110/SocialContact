package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class EditFamilykActivity extends BaseActivity {
    @InjectView(R.id.questContent)
    EditText questContent;
    private FamilyList.ContentBean family;
    private int type;

    @Override
    public int getRootViewId() {
        return R.layout.activity_edit_family;
    }

    @Override
    public void setViews() {
        setTitleTv("修改家族信息");
        setNextTv("保存");
        getNextTv().setTextColor(getResources().getColor(R.color.app_color));
        setNextOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = questContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.shortShowToast("请先输入" + (type == 0 ? "家族名字" : ((type == 1) ? "家族欢迎词" : "家族介绍")) + "...");
                    return;
                }
                if (content.contains("代充") || content.contains("官方")) {
                    ToastUtil.longShowToast("家族名字中不可出现“代充”、“官方”等关键字...");
                    return;
                }
                if (family != null) {
                    Tools.showDialog(EditFamilykActivity.this);
                    NetUtils.getInstance().familyUpdate(family.getGroupId(),type == 0 ? content : "", type == 1 ? content : "", type == 2 ? content : "", "", "", "", -1, new NetCallBack() {
                        @Override
                        public void onSuccess(String result, String msg, ResultModel model) {
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast(msg);
                            EventBuss event = new EventBuss(type==0?EventBuss.FAMILY_NAME:(type == 1 ? EventBuss.FAMILY_WELCOME : EventBuss.FAMILY_INTRODUCE));
                            event.setMessage(content);
                            EventBus.getDefault().post(event);
                            finish();
                        }

                        @Override
                        public void onFail(String code, String msg, String result) {
                            Tools.dismissWaitDialog();
                            ToastUtil.shortShowToast(msg);
                        }
                    }, null);
                }
            }
        });
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            family = ((FamilyList.ContentBean) getIntent().getSerializableExtra("family"));
            type = getIntent().getIntExtra("type", 0);
            questContent.setHint("请先输入" + (type == 0 ? "家族名字" : ((type == 1) ? "家族欢迎词" : "家族介绍")) + "...");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
