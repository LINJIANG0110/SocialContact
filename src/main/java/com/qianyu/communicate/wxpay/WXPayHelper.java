package com.qianyu.communicate.wxpay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.qianyu.communicate.utils.Contants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.qianyu.communicate.utils.Tools.isAppInstalled;


/**
 * Created by werewolf on 16/5/3.
 */
public class WXPayHelper {

    private StringBuffer sb;
    private PayReq req;
    private IWXAPI msgApi = null;
    private String TAG = "WXPayHelper";
    Map<String, String> resultunifiedorder;
    private Activity context = null;
    public static WXPayCallBack wxCallBack = null;

    private WXPayHelper(Activity context, WXPayCallBack callBack) {
        this.wxCallBack = callBack;
        this.context = context;
        msgApi = WXAPIFactory.createWXAPI(context, Contants.WX_APP_ID, true);
        req = new PayReq();
        sb = new StringBuffer();
    }

    public interface WXPayCallBack {

        void success();

        void falure(String message);
    }

    public static synchronized WXPayHelper getInstance(Activity context, WXPayCallBack callBack) {
        return new WXPayHelper(context, callBack);
    }

    public void startPay(WxPay wxPay) {
        if (!isAppInstalled(context, "com.tencent.mm")) {
            ToastUtil.shortShowToast("请先安装微信客户端");
            return;
        }
        new GetPrepayIdTask(context, wxPay).execute();
    }

    public void directPay_(WxPay payInfo) {
        if (!isAppInstalled(context, "com.tencent.mm")) {
            ToastUtil.shortShowToast("请先安装微信客户端");
            return;
        }
        req.appId = payInfo.getAppid();
        req.partnerId = payInfo.getMch_id();
        req.prepayId = payInfo.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = payInfo.getNonce_str();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//        req.sign = genAppSign(signParams);
        req.sign = payInfo.getSign();
        sb.append("sign\n" + req.sign + "\n\n");
        Log.e("orion", signParams.toString());
        AppLog.e("=======checkArgs=======" + req.checkArgs());
        msgApi.sendReq(req);
    }

    public void directPay(WxPay payInfo) {
        if (!isAppInstalled(context, "com.tencent.mm")) {
            ToastUtil.shortShowToast("请先安装微信客户端");
            return;
        }
//        req.appId = payInfo.getAppid();
//        req.partnerId = payInfo.getPartnerid();
//        req.prepayId = payInfo.getPrepayid();
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = payInfo.getNoncestr();
//        req.timeStamp = payInfo.getTimestamp() + "";
////        req.timeStamp = String.valueOf(genTimeStamp());
//        req.sign = payInfo.getSign();
//        msgApi.registerApp(Contants.WX_APP_ID);
//        msgApi.sendReq(req);

        req.appId = payInfo.getAppid();
        req.partnerId = payInfo.getMch_id();
        req.prepayId = payInfo.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        msgApi.sendReq(req);
    }


    private void genPayReq(WxPay wxPay) {
        req.appId = wxPay.getAppid();
        req.partnerId = wxPay.getMch_id();
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        AppLog.e("=======sign111=======" + req.sign);
        sb.append("sign\n" + req.sign + "\n\n");

//        show.setText(sb.toString());

        Log.e("orion", signParams.toString());
        msgApi.sendReq(req);

    }


    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");//api密钥API_Key
        sb.append(Contants.WX_API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }


    private String genProductArgs(WxPay wxPay) {
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", wxPay.getAppid()));
            packageParams.add(new BasicNameValuePair("body", "body"));
            packageParams.add(new BasicNameValuePair("mch_id", wxPay.getMch_id()));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://www.weiluezh.com/"));
            packageParams.add(new BasicNameValuePair("out_trade_no", "12345"));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", 1 + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));


            String sign = genPackageSign(packageParams);
            AppLog.e("=======sign222=======" + sign);
            packageParams.add(new BasicNameValuePair("sign", sign));


            String xmlstring = toXml(packageParams);


            return new String(xmlstring.toString().getBytes(), "ISO8859-1");

        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }


    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion-xml", sb.toString());
        return sb.toString();
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


//    private String genOutTradNo() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }


    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Contants.WX_APP_SECRET);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion-packageSign", packageSign);

        return packageSign;
    }


    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;
        private Context context;
        private WxPay wxPay;

        public GetPrepayIdTask(Context context, WxPay wxPay) {
            this.context = context;
            this.wxPay = wxPay;
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", "loading...");
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            AppLog.e(result + "==========result=======" + result.toString());
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;
            genPayReq(wxPay);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs(wxPay);
            byte[] buf = Util.httpPost(url, entity);
            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }


    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }

}
