package com.qianyu.communicate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.adapter.BroadcastTagAdapter;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.ImpressLabel;
import com.qianyu.communicate.entity.RedPackageBean;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CRedPackageActivity extends BaseActivity {
    @InjectView(R.id.back_img)
    ImageView ivBack;
    @InjectView(R.id.et_chengyu)
    EditText etChengyu;
    @InjectView(R.id.et_num)
    EditText etNumber;
    @InjectView(R.id.et_money)
    EditText etMoney;
    @InjectView(R.id.iv_clear)
    ImageView ivClear;
    @InjectView(R.id.tv_refresh)
    TextView tvRefresh;
    @InjectView(R.id.iv_refresh)
    ImageView ivRefresh;
    @InjectView(R.id.btn_submit)
    Button btnSubmit;
    private BroadcastTagAdapter<ImpressLabel> adapter;
    String groupId;
    @Override
    public int getRootViewId() {
        return R.layout.activity_cred_package;
    }

    @Override
    public void setViews() {
        groupId = getIntent().getStringExtra("groupId");
    }

    @Override
    public void initData() {
        getIdioms();
    }

    /**
     * 普通事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(EventBuss event) {

    }
    // 获取随机生成一个成语
    private void getIdioms() {
        NetUtils.getInstance().getIdioms(new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                try {
                    JSONObject mJob = new JSONObject(result);
                    String code = mJob.getString("code");
                    String data = mJob.getString("data");
                    if (code.equals("200")) {
                        etChengyu.setText(data);
                    } else {
                        ToastUtil.shortShowToast(msg);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, null);
    }

    // 验证成语
    private void verifyIdioms(){
        Tools.showDialog(this);
        Log.e("验证",etChengyu.getText().toString());
        NetUtils.getInstance().vertifyIdioms(etChengyu.getText().toString(),new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                try {
                    JSONObject job = new JSONObject(result);
                    String code = job.getString("code");
                    if (code.equals("200")){
                        // 验证成功
                        createRedpackage();
                    }else {
                        Tools.dismissWaitDialog();
                        ToastUtil.shortShowToast(msg);
                    }
                }catch (Exception e){
                    Tools.dismissWaitDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String code, String msg, String result) {
                Tools.dismissWaitDialog();
                ToastUtil.shortShowToast(msg);
            }
        },null);
    }

    private void createRedpackage(){
        // 1 随机正常红包 2平均正常红包 3随机成语接龙红包
        String type = "3";
        NetUtils.getInstance().createRedpackage(groupId, etChengyu.getText().toString(), etMoney.getText().toString(), etNumber.getText().toString(), type, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("创建红包",result);
                Tools.dismissWaitDialog();
                RedPackageBean redpackageBean = (RedPackageBean) model.getModel();
                try {
                    JSONObject job = new JSONObject(result);
                    if (job.getString("code").equals("200")){
                        redpackageBean.setMessageType(7);// 设置7回调
                        EventBuss event = new EventBuss(EventBuss.CREATE_REDPACKE);
                        event.setMessage(redpackageBean);
                        EventBus.getDefault().post(event);
                        finish();
                    }else {
                        ToastUtil.shortShowToast(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
                Tools.dismissWaitDialog();
            }
        }, RedPackageBean.class);
    }
    @OnClick({R.id.iv_clear, R.id.tv_refresh, R.id.iv_refresh, R.id.btn_submit,R.id.back_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                etChengyu.setText("");
                break;
            // 刷新红包
            case R.id.tv_refresh:
                getIdioms();
                break;
            case R.id.iv_refresh:
                getIdioms();
                break;
            case R.id.back_img:
                finish();
                break;
            // 提交
            case R.id.btn_submit:
                if (etChengyu.getText().toString().equals("")) {
                    ToastUtil.shortShowToast("请填写接龙成语");
                    return;
                }
                if (etNumber.getText().toString().equals("")) {
                    ToastUtil.shortShowToast("请填写红包个数");
                    return;
                }
                if (etMoney.getText().toString().equals("")) {
                    ToastUtil.shortShowToast("请填写红包金额");
                    return;
                }
                // 单个红包不能低于1个钻石
                if (Integer.valueOf(etMoney.getText().toString()) < Integer.valueOf(etNumber.getText().toString())) {
                    ToastUtil.shortShowToast("单个红包不能小于1个钻石");
                    return;
                }
                // 验证成语
                verifyIdioms();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
    }
}
