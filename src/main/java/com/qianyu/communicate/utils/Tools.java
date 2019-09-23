package com.qianyu.communicate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.qianyu.communicate.activity.FamilyRoomActivity;
import com.qianyu.communicate.appliction.MyApplication;
import com.qianyu.communicate.bukaSdk.entity.UserBean;
import com.qianyu.communicate.entity.EnterGroup;
import com.qianyu.communicate.entity.FamilyList;
import com.qianyu.communicate.entity.User;
import com.qianyu.communicate.http.NetUtils;

import net.neiquan.okhttp.NetCallBack;
import net.neiquan.okhttp.ResultModel;

import org.haitao.common.utils.ToastUtil;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Tools {
    private static Context context;
    private static Dialog ad;
    private static TextView tv;

    /**
     * 初始化注入Context对象
     */
    public static void setContext(Context context) {
        Tools.context = context;
    }

    /**
     * @param context
     * @param view
     * @param x           显示的x坐标
     * @param y           显示的y坐标
     * @param width       dialog的宽度
     * @param height      dialog的高度
     * @param animation   显示和消失的动画效果
     * @param cancleAble  是否可以cancle掉
     * @param isAnimation
     * @return
     */
    private static Dialog showDialog(Context context, View view, int x, int y, int width, int height, int animation, boolean cancleAble, boolean isAnimation) {
        Dialog ad;
        if (Build.VERSION.SDK_INT >= 11) {
            ad = new AlertDialog.Builder(context, net.neiquan.applibrary.R.style.alert_dialog).create();
        } else {
            ad = new Dialog(context, net.neiquan.applibrary.R.style.alert_dialog);
        }
        ad.setCancelable(cancleAble);
        LayoutParams lp = ad.getWindow().getAttributes();
        lp.x = x;
        lp.y = y;
        ad.show();
        ad.setContentView(view);
        ad.getWindow().setLayout(width, height);
    /*	if (isAnimation) {
            if (animation != 0)
				ad.getWindow().setWindowAnimations(animation);
			else
				ad.getWindow().setWindowAnimations(R.style.head_in_out);
		}*/
        ad.getWindow().clearFlags(LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return ad;
    }

    /**
     * 弹出提示用户等待的dialog
     */
    public static Dialog showDialog(final Context context) {
        showWaitDialog(context, "请稍等", true);
        return ad;
    }

    /**
     * 弹出提示用户等待的dialog
     */
    public static void showWaitDialog(final Context context, String str) {
        showWaitDialog(context, str, true);
    }

    /**
     * 弹出提示用户等待的dialog
     */
    public static void showWaitDialog(final Context context, String str, boolean canCancle) {
        if (ad == null || !ad.isShowing()) {
            View vv = View.inflate(context, net.neiquan.applibrary.R.layout.dialog_wait, null);
            tv = vv.findViewById(net.neiquan.applibrary.R.id.tv);
            if (!Tools.isEmptyStr(str)) {
                tv.setText(str);
            }
            ad = showDialog(context, vv, 0, 0, (int) (Tools.getsx() / 1.8), (int) (Tools.getsy() / 6.5), 0, canCancle, false);
        } else {
            if (!Tools.isEmptyStr(str)) {
                tv.setText(str);
            }
            ad.setCancelable(canCancle);
        }
    }

    public static boolean isEmptyStr(String str) {
        return (str == null || str.trim().length() < 1);
    }

    public static int getsx() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getsy() {
        int px = Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        return context.getResources().getDisplayMetrics().heightPixels - px;
    }

    /**
     * 取消等待的dialog
     */
    public static void dismissWaitDialog() {
        if (ad != null) {
            ad.dismiss();
        }
    }

    /**
     * 获取视频缩略图  可能为null 用前先判断
     *
     * @param videoPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static Context getContext() {
        return MyApplication.context;
    }

    /**
     * 获取资源文件
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param stringArrayId 传递在xml中定义字符串数组id
     * @return 通过id获取到的字符串数组
     */
    public static String[] getStringArray(int stringArrayId) {
        return getResources().getStringArray(stringArrayId);
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                Runnable r = (Runnable) msg.obj;
                r.run();
            }
        }

    };

    /**
     * 主线程回到使用
     */
    public static void runOnUI(Runnable run) {
        Message me = handler.obtainMessage();
        me.obj = run;
        handler.sendMessage(me);
    }

    /**
     * 启动一个新的Activity
     *
     * @param from 起始Activity
     * @param to   跳转Activity
     */
    public static void goActivity(Context from, Class<? extends Activity> to, String[] keys, String[] values) {
        Intent in = new Intent(from, to);
        if (keys != null && values != null)
            for (int i = 0; i < keys.length; i++)
                in.putExtra(keys[i], values[i]);
        from.startActivity(in);
    }

    /**
     * 获取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    private static Runnable run;
    private static int delaytime;

    public static void startTask(Runnable _run, int _delaytime) {
        run = _run;
        delaytime = _delaytime;
        handler2.postDelayed(runnable, 0);
        // 开始Timer
    }

    public static void finishTask() {
        handler2.removeCallbacks(runnable);           //停止Timer
        run = null;// 提醒回收
    }

    private static Handler handler2 = new Handler();

    private static Runnable runnable = new Runnable() {

        public void run() {
            if (run != null) {
                run.run();
            }
            handler2.postDelayed(this, delaytime);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
        }
    };

    /**
     * 根据包名判断是否安装该应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
            // problem happened
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    private Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                     int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static boolean isAppInstalled(Activity ac, String uri) {
        PackageManager pm = ac.getPackageManager();
        boolean installed = false;
        try {
//            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            pm.getPackageInfo(uri, PackageManager.MATCH_DEFAULT_ONLY);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    // 邀请话题回答自定义消息
    public static void setTopic(String phone, String mUrl, String topicTitle, String topicId) {
        Log.e("phone----",phone);
        //发送话题回答到聊天
        EMMessage message = EMMessage.createTxtSendMessage("您收到一个话题回答邀请!", phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_TOPIC_URL, mUrl);
        message.setAttribute(EaseConstant.MESSAGE_TOPIC_NAME, topicTitle);
        message.setAttribute(EaseConstant.MESSAGE_TOPIC_ID, topicId);
        message.setAttribute(EaseConstant.MESSAGE_TOPIC_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 21);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void groupInvite(String phone, String headUrl, String nickName, long groupId) {
        //发送入群邀请到聊天
        EMMessage message = EMMessage.createTxtSendMessage("您收到一个入群邀请!", phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_GROUP_URL, headUrl);
        message.setAttribute(EaseConstant.MESSAGE_GROUP_NAME, nickName);
        message.setAttribute(EaseConstant.MESSAGE_GROUP_ID, groupId);
        message.setAttribute(EaseConstant.MESSAGE_GROUP_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 14);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendGift(String phone, int giftKind, String giftMoney, String giftName, String giftUrl, String giftNum, String giftExperience, String giftRich, String giftCharm) {
        //发送礼物到聊天
        EMMessage message = EMMessage.createTxtSendMessage("您收到一份礼物!", phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_GIFT_KIND, giftKind);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_MONEY, giftMoney);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_NAME, giftName);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_URL, giftUrl);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_NUM, giftNum);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_EXPERIENCE, giftExperience);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_RICH, giftRich);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_CHARM, giftCharm);
        message.setAttribute(EaseConstant.MESSAGE_GIFT_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 15);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void skillSuccess(String phone, String sender, String receiver, String skillName, String skillEffect,
                                    long senderId, int senderLevel, long receiverId, int receiverLevel) {
        //使用技能成功
        EMMessage message = EMMessage.createTxtSendMessage("使用技能成功!", phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_SKILL_SENDER, sender);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_RECEIVER, receiver);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_NAME, skillName);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_EFFECT, skillEffect);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_ID_SENDER, senderId);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_LEVEL_SENDER, senderLevel);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_ID_RECEIVER, receiverId);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_LEVEL_RECEIVER, receiverLevel);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_SUCCESS_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 16);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void skillFail(String phone, String sender, String receiver, String skillName, String skillEffect,
                                 long senderId, int senderLevel, long receiverId, int receiverLevel) {
        //使用技能失败
        EMMessage message = EMMessage.createTxtSendMessage("使用技能失败!", phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_SKILL_SENDER, sender);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_RECEIVER, receiver);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_NAME, skillName);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_EFFECT, skillEffect);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_ID_SENDER, senderId);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_LEVEL_SENDER, senderLevel);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_ID_RECEIVER, receiverId);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_LEVEL_RECEIVER, receiverLevel);
        message.setAttribute(EaseConstant.MESSAGE_SKILL_FAIL_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 17);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void guardSuccess(String phone, String guardResult) {
        //押镖成功
        EMMessage message = EMMessage.createTxtSendMessage(guardResult, phone);
        if (message == null) {
            return;
        }
//        message.setAttribute(EaseConstant.MESSAGE_GUARD_NAME, guardName);
//        message.setAttribute(EaseConstant.MESSAGE_GUARDED_NAME, guardedName);
        message.setAttribute(EaseConstant.MESSAGE_GUARD_RESULT, guardResult);
        message.setAttribute(EaseConstant.MESSAGE_GUARD_SUCCESS_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 18);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void guardFail(String phone, String guardResult) {
        //押镖失败
        EMMessage message = EMMessage.createTxtSendMessage(guardResult, phone);
        if (message == null) {
            return;
        }
//        message.setAttribute(EaseConstant.MESSAGE_GUARD_NAME, guardName);
//        message.setAttribute(EaseConstant.MESSAGE_GUARDED_NAME, guardedName);
        message.setAttribute(EaseConstant.MESSAGE_GUARD_RESULT, guardResult);
        message.setAttribute(EaseConstant.MESSAGE_GUARD_FAIL_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 19);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void agreeFriend(String phone) {
        //好友申请通过
        EMMessage message = EMMessage.createTxtSendMessage("我通过了你的好友验证请求,现在我们可以开始聊天了。", phone);
        if (message == null) {
            return;
        }
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void groupAtMe(String content, String phone, long groupId) {
        //家族@我
        EMMessage message = EMMessage.createTxtSendMessage(content, phone);
        if (message == null) {
            return;
        }
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_AT_GROUPID, groupId); //群ID
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_AT_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        message.setAttribute("HX_MESSAGE_EXT_TYPE", 20);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    //谁看过我
    public static void lookAtMe(String text) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation("13777777777", EMConversation.EMConversationType.Chat, true);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        message.setFrom("13777777777");
        message.setTo(EMClient.getInstance().getCurrentUser());
        message.setDirection(EMMessage.Direct.RECEIVE);
        message.setUnread(true);
        message.setAcked(false);
        message.setStatus(EMMessage.Status.SUCCESS);
        message.addBody(new EMTextMessageBody(text));
        message.setAttribute(EaseConstant.MESSAGE_LOOK_ME_TEXT, text); //谁看过我
        message.setAttribute(EaseConstant.MESSAGE_LOOK_ME_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        conversation.appendMessage(message);
    }

    //聊天室消息
    public static void familyRoom(String phone, String url, String name, String text, long gId) {
        //13111111111到13666666666位家族和五个关注群
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(phone, EMConversation.EMConversationType.Chat, true);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        message.setFrom(phone);
        message.setTo(EMClient.getInstance().getCurrentUser());
        message.setDirection(EMMessage.Direct.RECEIVE);
        message.setUnread(true);
        message.setAcked(false);
        message.setStatus(EMMessage.Status.SUCCESS);
        message.addBody(new EMTextMessageBody("聊天室消息"));
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_URL, url); //聊天室图片
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_NAME, name); //聊天室名字
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_TEXT, text); //聊天室文字
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_ID, gId); //聊天室ID
        message.setAttribute(EaseConstant.MESSAGE_FAMILY_ROOM_TYPE, true); //这里是为了区分是否是富文本消息(发送富文本消息对应的value一定要为true)
        conversation.appendMessage(message);
    }

    public static void enterFamily(final Activity activity, long groupId, final boolean boss) {
        final FamilyList.ContentBean contentBean = new FamilyList.ContentBean();
        contentBean.setGroupId(groupId);
        NetUtils.getInstance().enterGroup(contentBean.getGroupId(), new NetCallBack() {
            @Override
            public void onSuccess(String result, String msg, ResultModel model) {
                EnterGroup enterGroup = (EnterGroup) model.getModel();
                UserBean userBean = new UserBean();
                User user = MyApplication.getInstance().user;
                if (user != null) {
                    userBean.setUserId(user.getUserId());
                    userBean.setLevel(user.getLevel());
                    userBean.setPhone(user.getPhone());
                    userBean.setNickName(user.getNickName());
                    userBean.setHeadUrl(user.getHeadUrl());
                    userBean.setSex(user.getSex());
                    userBean.setAddress(user.getAddress());
                    userBean.setAge(user.getAge());
                    userBean.setUserNum(user.getUserNum());
                }
                contentBean.setGroupName(enterGroup.getGroupName());
                contentBean.setHeadUrl(enterGroup.getHeadUrl());
                userBean.setGroupId(contentBean.getGroupId() + "");
                userBean.setUserflag(enterGroup.getUserflag());
                userBean.setUserType(enterGroup.getUserType());
                userBean.setConfusionNum(enterGroup.getConfusionNum());
                userBean.setTichutime(enterGroup.getTichutime());
                userBean.setJinyantime(enterGroup.getJinyantime());
                userBean.setTichunInfo(enterGroup.getTichunInfo());
                userBean.setJinYanInfo(enterGroup.getJinYanInfo());
                userBean.setDesignationUrl(enterGroup.getDesignationUrl());
                userBean.setMountName(enterGroup.getMountName());
                userBean.setMountPicPath(enterGroup.getMountPicPath());
                userBean.setExpand(enterGroup.getExpand());
                Intent intent = new Intent(activity, FamilyRoomActivity.class);
                intent.putExtra("userBean", userBean);
                intent.putExtra("family", contentBean);
                intent.putExtra("enterGroup", enterGroup);
                intent.putExtra("boss", boss);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivityForResult(intent, 100);
            }

            @Override
            public void onFail(String code, String msg, String result) {
                ToastUtil.shortShowToast(msg);
            }
        }, EnterGroup.class);
    }

    public static SpannableString matcherSearchTitle(int color1, int color2, String text, String keyword1, String keyword2) {
        String string = text.toLowerCase();
        SpannableString ss = new SpannableString(text);

        String key1 = keyword1.toLowerCase();
        Pattern pattern1 = Pattern.compile(key1);
        Matcher matcher1 = pattern1.matcher(string);
        while (matcher1.find()) {
            int start = matcher1.start();
            int end = matcher1.end();
            ss.setSpan(new ForegroundColorSpan(color1), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        String key2 = keyword2.toLowerCase();
        Pattern pattern2 = Pattern.compile(key2);
        Matcher matcher2 = pattern2.matcher(string);
        while (matcher2.find()) {
            int start = matcher2.start();
            int end = matcher2.end();
            ss.setSpan(new ForegroundColorSpan(color2), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[/\\:*?<>|,，\"\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");

    }

    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean checkAlertWindowsPermission(Context context) {
        try {
            Object object = context.getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = 24;
            arrayOfObject1[1] = Binder.getCallingUid();
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1));
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }

    public static void setSystemUIVisible(Activity activity, boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(width > height ? scaleWidth : scaleHeight, width > height ? scaleWidth : scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static Bitmap blurBitmap(Context context, Bitmap bmp) {
        Bitmap result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation in = Allocation.createFromBitmap(rs, bmp);
        Allocation out = Allocation.createFromBitmap(rs, result);
        blur.setRadius(1f);
        blur.setInput(in);
        blur.forEach(out);
        out.copyTo(result);
        rs.destroy();
        return result;
    }

}
