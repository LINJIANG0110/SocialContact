package com.qianyu.communicate.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.qianyu.communicate.alipay.util.OrderInfoUtil2_0;

import org.haitao.common.utils.AppLog;

import java.util.Map;

/**
 * Created by JavaDog on 2018-2-26.
 */

public class AliPayUtil {

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2019041063859369";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088431985566720";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA_PRIVATE ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChaTuatYgzHcmQTUyrrvO/4gSVC+W3c2oqhhzumo3V/5c3T8iiRo901hTpOn9ZUIZq9Xfo3EjLZlNut/dKj7Xpp5/k+j7Ti0yygKkGINItisAg2E3C4McrDnXsLgoLbZDskLKIBWbWYhPg9ytp55p9l7nFRLQXWL7+0eK4zD+2K06udntyFKHjCPF7T/pK2zwf5hV6X4894EnO3zNpSty95N5/SCz7iJ9DDIC7EfsdYGcAI28S+fewpzAWvHks0AimoKwTJ+PWifo48GWjewcNQtAoRk8uws1bspIftfvKKCc/DQ4JelfNJmQ31ppMpBpoZF7ko3ebsvjPCxcM5kypAgMBAAECggEBAJ913/2qg6DEN8JIq4sc7w/NEWvR8iK3cdYK4oBSzfbxZ4msV3EBtm3dpZD4SqGI7RKz6L9zCLNxJtHGCxdtPHFWOZ+0HedNwfrDIri8Kgf2AHMyXjg9amY+Bf/LR/8cQkWDeezpMTxwbmUcqpYYycZz44Jgqm46LY5++bZJilPDiW8es/qPRVsz9SgOFdle44GyLK6D2+JpB4TvU38J0Ep5hPEgtJaHloHa0lLUnmZWk5emeS1GuB7w/7RdDnySyGjnv446USJsCK23uHclQJej5+O5Z/I1S8+MP/RiWOlojWm9FPf2zjgpqJu+JdPco2WZknDm0buXYobGJCwgN2UCgYEAzZBMtlju9VsAuWnxwTEJOCVUYum8vELaDddTafnX2fCx68Mf7gh6QsPdebiGyIScPifv5iHkwllDqCj4f+ngcxyRk99HWEr4Lad3IEQ877N+rT0k0JwYAApOGJmPNdAGthRTMteDuUW85vSxsCWvo8j5ypq83PjeVQwtCRhu24MCgYEAyQOnujAsku94g67/Bw69CUdcSipeStQQ8iJzgv9xny4YiLPjC2mUZ6ImvjwG3n3wSkVBbgw8NXMP6u5N4ILUvZ0yEMwExBmTaHBWfLZelQEfv3QroTgavCeEBX8CZ03ZqIuLhE5x2Rpt3wfU5FLv5T//yUYku/luGdB5bhhBo2MCgYAhR4VD2C4Hhs4dNmuvLTMi5dg9kz289M28g+m7NHmfjPohAxv+O10fxfnrJNadS+rHnUuXZFFWrYDojol8m+58dFmVKQax4qE/Mud0T9fVrqeJgGafZ0Xza7es/99RhPYxMCUwC/gFXKbydRb3P4Kp73WROW/uDlRgTHJWEKCR5QKBgBEgjpn+oM2kOdB+iJxBVToFKofnNEGL8CWxacYvna7CbrS3HxFJ3as21bY/xxYOBPtPC1QlFrw3IZQS2e/XNekyC6ICwCU1SKoNaFEX+NcZUWsuqskBzsVZWJZfTAUhTPpfaXPMe0bUSahHMoyBxI7z9QdhQqABkLACY1ZOGsmhAoGBAMztuXfv5eU7oPsBZT7sxLFFuCRIEW77CFKGbeWIzFooM+BGB76/z3GVfrrVMTZZGrSvwtdBy4appkAWduOYXNZ93bnUfjDVkD+twkkV4uqVhZ6mKUmw49yfLDwvA2eF5S7uiM3pLeWBK830OlEs6b3HI21lzu8iovGutY21DmQ5";
    public static final String RSA2_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            AppLog.e("=========mHandler=========" + msg.what);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                        aliPayCallBack.success();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                        aliPayCallBack.falure("支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(activity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(activity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private Activity activity;
    private AliPayCallBack aliPayCallBack;

    public AliPayUtil(Activity activity,AliPayCallBack aliPayCallBack) {
        this.activity = activity;
        this.aliPayCallBack = aliPayCallBack;
    }

    public interface AliPayCallBack {

        void success();

        void falure(String message);
    }

    /**
     * 支付宝支付业务
     */
    public void payV2_() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            activity.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void payV2(final String orderInfo) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            activity.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
//        final String orderInfo = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018022602276135&biz_content=%7B%22goods_type%22%3A%220%22%2C%22out_trade_no%22%3A%2220180301110345080099%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%A5%9E%E7%A5%BA%E9%93%AD%E5%8C%BB%E8%91%AB%E8%8A%A6%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F39.107.76.164%3A8090%2Fmaimai%2FpaymentController%2FqueryPaymentInfo&sign=W8ZLIyYcnxdO8PQKAVBWKRjmmK2g7mhRQbRvSzU7OSxh7YH200d9bIo1jqQDxE80DOW9BC0Scf7p%2Bix%2BmXoSUlnOvGHTCsiBhCSvMM4zS2QBUW3oISkZlwSQPam%2BFzCIDqEIY%2Bb0a73RUYMOX0sXgextbOayjAgNFEvfw%2BC4U52kqFaTca7v76TfyWRWeSlgw%2BKlZgOQ%2Btua%2FOTe56BeWLJG5cjHPEPvljSX7iabk99TAVqMSMzNmlzSjb35eJANDhZH4wdzL3Pf%2FFnocoV6a2BMWLpM2yir%2FREiCNKod6XhsEcsfVzLIWViQFF7L4%2B5V4rAnELH%2FZnN9g8UV4trtg%3D%3D&sign_type=RSA2&timestamp=2018-03-01+11%3A04%3A01&version=1.0";

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     */
    public void h5Pay() {
        Intent intent = new Intent(activity, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
         *
         * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
         * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
         * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
         * 进行测试。
         *
         * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
         * 可以参考它实现自定义的 URL 拦截逻辑。
         */
        String url = "http://m.taobao.com";
        extras.putString("url", url);
        intent.putExtras(extras);
        activity.startActivity(intent);
    }

}
