package com.qianyu.communicate.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianyu.communicate.R;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.Contants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.AppLog;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private TextView mResultTv;
    private LinearLayout ly_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ly_back = ((LinearLayout) findViewById(R.id.ly_back));
        mResultTv = ((TextView) findViewById(R.id.mResultTv));
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mResultTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AppLog.e("===============000===========");
        api = WXAPIFactory.createWXAPI(this, Contants.WX_APP_ID);
        api.handleIntent(getIntent(), this);
//        EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
//        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        AppLog.e("===============aaa===========");
    }

    @Override
    public void onReq(BaseReq req) {
        AppLog.e("===============111===========" + req.checkArgs());
    }


    @Override
    public void onResp(BaseResp resp) {
        AppLog.e(resp.errStr + "===============222===========" + String.valueOf(resp.errCode));
        mResultTv.setText(resultStr(resp));
        if(TextUtils.equals("0",String.valueOf(resp.errCode))){
            EventBus.getDefault().post(new EventBuss(EventBuss.MINE_TAB));
        }
        finish();
    }

    private String resultStr(BaseResp resp) {
        String msg = "";
        switch (resp.getType()) {
            case 0:
                msg = "支付成功!";
                break;
            case -1:
                msg = "支付失败!";
                break;
            case -2:
                msg = "您已取消支付!";
                break;
            case -3:
                msg = "支付失败!";
                break;
            case -4:
                msg = "授权失败!";
                break;
            case -5:
                msg = "微信不支持!";
                break;
            case 5:
                msg = "支付结束!";
                break;
        }
        return msg;
    }
}