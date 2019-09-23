package com.qianyu.communicate.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.entity.EventRecord;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.event.EventBuss;
import com.qianyu.communicate.utils.SpUtil;
import com.qianyu.communicate.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.haitao.common.logger.Logger;
import org.haitao.common.utils.AppLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import tv.buka.sdk.utils.JsonUtils;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            AppLog.e(printBundle(bundle));
            AppLog.e("==============aaa==================" + bundle.getString(JPushInterface.EXTRA_ALERT));
            AppLog.e(bundle.getString(JPushInterface.EXTRA_MESSAGE) + "==============0000==================" + bundle.getString(JPushInterface.EXTRA_EXTRA));
//			EventBus.getDefault().post(new EventBuss(EventBuss.REFRESH_ORDER));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                AppLog.e("[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                if (!TextUtils.isEmpty(message)) {
                    AppLog.e(message);
                    JSONObject jsonObject = new JSONObject(message);
                    String eventType = jsonObject.getString("eventType");
                    // one只有技能 我的消息监听one里的 group只有解救 家族boss 世界只有一个礼物
                    String pushType = jsonObject.getString("pushType");//个人事件推送
                    String sendUserId = jsonObject.getString("sendUserId");
                    User user = MyApplication.getInstance().user;
                    long userId = user.getUserId();
//                    AppLog.e("====eventType==",eventType);
//                    AppLog.e("====pushType==",pushType);
                    EventBuss event;
                    switch (eventType) {
                        case "SKILL":// 技能相关
                            // 1.技能里面只判断pushType=one才显示（指定某个人显示）2.判断sendUserId等于自己也显示
                            if (pushType != null && pushType.equals("one") || sendUserId.equals(userId+"")){
                                event = new EventBuss(EventBuss.PUSH_SKILL);
                                event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                                EventBus.getDefault().post(event);
                            }
                            break;
                        case "DYNAMIC"://动态
//                            String headUrl = jsonObject.getString("headUrl");
//                            if (!TextUtils.isEmpty(headUrl)) {
                            event = new EventBuss(EventBuss.CIRCLE_MSG);
//                                event.setMessage(headUrl);
                            EventBus.getDefault().post(event);
//                            }
                            break;
                        case "GIFT":// 礼物
                            event = new EventBuss(EventBuss.PUSH_GIFT);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                        case "FAMILYBOSS"://家族Boss
                            // 家族boss里面只判断pushType=group才显示（指定某个家族的人显示）
                            if (pushType != null && pushType.equals("group") || sendUserId.equals(userId+"")){
                                event = new EventBuss(EventBuss.FAMILY_BOSS);
                                event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                                EventBus.getDefault().post(event);
                            }
                            break;
                        case "FAMILYCALL"://家族召唤
                            event = new EventBuss(EventBuss.FAMILY_RECRUIT);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                        case "FAMILYRECRUIT"://世界招募
                            event = new EventBuss(EventBuss.WORLD_RECRUIT);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                        case "SENDMSG"://世界发言
                            event = new EventBuss(EventBuss.WORLD_MSG);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                        case "APPLYFRIEND"://好友申请
                            event = new EventBuss(EventBuss.FRIEND_REQUEST);
                            EventBus.getDefault().post(event);
                            break;
                        case "APPLYGROUP"://家族申请
                            event = new EventBuss(EventBuss.FAMILY_ENTER);
                            EventBus.getDefault().post(event);
                            break;
                        case "CDART"://押镖劫镖(私信)
                            EventRecord.ContentBean contentBean = JsonUtils.getBean(message, EventRecord.ContentBean.class);
                            if (contentBean.getAccPhone() != 0) {
                                if (contentBean.getIsOk() == 1) {
                                    Tools.guardSuccess("" + contentBean.getAccPhone(), contentBean.getEventMsg());
                                } else {
                                    Tools.guardFail("" + contentBean.getAccPhone(), contentBean.getEventMsg());
                                }
                            }
                            break;
                        case "SAVE"://押镖解救(私信)
                            EventRecord.ContentBean contentBean1 = JsonUtils.getBean(message, EventRecord.ContentBean.class);
                            if (contentBean1.getAccPhone() != 0) {
                                if (contentBean1.getIsOk() == 1) {
                                    Tools.guardSuccess("" + contentBean1.getAccPhone(), contentBean1.getEventMsg());
                                } else {
                                    Tools.guardFail("" + contentBean1.getAccPhone(), contentBean1.getEventMsg());
                                }
                            }
                            break;
                        case "DCDART"://押镖劫镖(H5)
                            event = new EventBuss(EventBuss.GURAD_CDART);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                        case "DSAVE"://押镖解救(H5)
                            // 押票解救里面只判断pushType=group才显示（指定某个家族的人显示）
                            if (pushType != null && pushType.equals("group") || sendUserId.equals(userId+"")){
                                event = new EventBuss(EventBuss.GURAD_SAVE);
                                event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                                EventBus.getDefault().post(event);
                            }
                            break;
                        case "LOOKME"://谁查看了我
                            EventRecord.ContentBean bean = JsonUtils.getBean(message, EventRecord.ContentBean.class);
                            if (bean != null && !TextUtils.isEmpty(bean.getMsg())) {
                                Tools.lookAtMe(bean.getMsg());
                            }
                            EventBus.getDefault().post(new EventBuss(EventBuss.HOME_MSG_REFRESH));
                            break;
                        case "ACTIVITYREWARD"://押镖结算
                            event = new EventBuss(EventBuss.GUARD_RESULT);
                            event.setMessage(JsonUtils.getBean(message, EventRecord.ContentBean.class));
                            EventBus.getDefault().post(event);
                            break;
                    }
                    if (!TextUtils.isEmpty(pushType) &&!TextUtils.isEmpty(eventType)&& TextUtils.equals("one", pushType)&&
                            (TextUtils.equals("GIFT", eventType)||TextUtils.equals("SKILL", eventType) ||TextUtils.equals("CDART", eventType)
                            ||TextUtils.equals("SAVE", eventType)||TextUtils.equals("DSAVE", eventType)||TextUtils.equals("DCDART", eventType))) {
                        EventBus.getDefault().post(new EventBuss(EventBuss.MY_EVENT));
                    }
                }
                AppLog.e("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                AppLog.e(bundle.getString(JPushInterface.EXTRA_MESSAGE) + "==============111==================" + bundle.getString(JPushInterface.EXTRA_EXTRA));
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                AppLog.e("==============222==================" + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //打开自定义的Activity
                Intent i = new Intent(context, MainActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                AppLog.e("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                AppLog.e("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                AppLog.e("[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    AppLog.e("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    AppLog.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
    }
}
