package com.qianyu.communicate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.qianyu.communicate.R;
import com.qianyu.communicate.base.BaseActivity;
import com.qianyu.communicate.entity.HbResultBean;
import com.qianyu.communicate.entity.RedPackageBean;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.http.NetUtils;
import com.qianyu.communicate.utils.Tools;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.utils.ToastUtil;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 系统红包-第一次进群的用户系统发红包
 */
public class SystemRedpackageDialog extends BaseActivity {
    @InjectView(R.id.rely_start)
    RelativeLayout relyStart;
    String groupId;
    @Override
    public int getRootViewId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
//        setFinishOnTouchOutside(false);//点击空白处，窗体不关闭
        return R.layout.activity_system_redpackage_dialog;
    }

    @Override
    public void setViews() {
        groupId = getIntent().getStringExtra("groupId");
        Log.e("接受的id",groupId);
        relyStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点开
                createSysRedpackage();
            }
        });
    }

    private void createSysRedpackage(){
        // 1 随机正常红包 2平均正常红包 3随机成语接龙红包
//        String type = "3";
        NetUtils.getInstance().createSysRedpackage(groupId, new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                Log.e("创建红包",result);
                Tools.dismissWaitDialog();
                RedPackageBean redpackageBean = (RedPackageBean) model.getModel();
                try {
                    JSONObject job = new JSONObject(result);
                    if (job.getString("code").equals("200")){
                        redpackageBean.setMessageType(9);// 设置9回调表示系统红包
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

    private String toNumber(float number) {
        String str = "";
        if (number <= 0) {
            str = "";
        } else if (number < 10000) {
            str = number + "";
        } else {
            double d = (double) number;
            //将数字转换成万
            double num = d / 10000;
            BigDecimal b = new BigDecimal(num);
            //四舍五入保留小数点后一位
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            str = f1 + "";
        }
        return str;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}