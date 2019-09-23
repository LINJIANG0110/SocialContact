package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2018-3-1.
 */

public class AliPay {

    /**
     * body : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018022602276135&biz_content=%7B%22goods_type%22%3A%220%22%2C%22out_trade_no%22%3A%22201803011518030800101%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%A5%9E%E7%A5%BA%E9%93%AD%E5%8C%BB%E8%91%AB%E8%8A%A6%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F39.107.76.164%3A8090%2Fmaimai%2FpaymentController%2FqueryPaymentInfo&sign=QvPLTTGHaJZrKJP0zCyhIAPIRcQI8ICeaPbXw9BQmFQem%2Bu96k4x5AjcHCe8oPfBmNG3a6bvYvj2j1PU34FF4SJkI6hFUCDXmjhe6WgP2dvNdvtRjSi1FxD6IXdmBitn0u1dFoJnETfSkFiUC2Is0Neyi0ekhzWL9aP0jkmcjE65rD%2FPvooWzSgMYhcg1YMWp48YBD4GCKiMQBTcTjb%2FLtKsLpaQur1G1rwijedBn0bGEXuDQtwR1%2B%2FbytgKZewo3zGECvPpmzmEmDYHhMOjSpkWWGXKvHkrlQhgta67gkLMH8r9p4hTfaxr4bgbgVTKEh5QNcrqsmrnHdjRagAM5Q%3D%3D&sign_type=RSA2&timestamp=2018-03-01+15%3A18%3A03&version=1.0
     * success : true
     */

    private String body;
    private boolean success;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
