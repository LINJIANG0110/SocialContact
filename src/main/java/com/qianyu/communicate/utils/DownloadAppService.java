package com.qianyu.communicate.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;


import com.qianyu.communicate.R;
import com.qianyu.communicate.activity.MainActivity;
import com.qianyu.communicate.appliction.MyApplication;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadAppService extends Service {

    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private boolean canceled;
    /* 下载包安装路径 */
    private static String savePath;
    public static String SERVICRINTENTURL = "serriviceinentApkurl";
    public static String SERVACESHARENAME = "DownloadAppServiceIntent";
    public static String BROOAWCASTSTOPSERVICE = "browcaststopervice";// 关闭下载服务
    public static String serviceName = "net.neiquan.service.DownloadAppService";
    public static String downAppFinish = "downappfinish";
    private static String saveFileName;
    private String apkurl;
    private int backActivity;// 返回acticity的标志
    private Notification mNotification;
    /**
     * 下载apk
     *
     * @param url
     */
    private Thread mDownLoadThread;
    private CallbackResult callback;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    stopSelf();
                    MyApplication.getInstance().downApp = false;
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    installApk();
                    break;
                case 2:
                    stopSelf();
                    MyApplication.getInstance().downApp = false;
                    // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                case 1:
                    int rate = msg.arg1;
                    MyApplication.getInstance().downApp = true;
                    if (rate < 100) {
                        RemoteViews contentview = mNotification.contentView;
                        contentview.setTextViewText(R.id.downloadapk_progress_tv, rate + "%");
                        contentview.setProgressBar(R.id.downloadapk_pb, 100, rate, false);
                    } else {
                        System.out.println("下载完毕!!!!!!!!!!!");
                        // 下载完毕后变换通知形式

                        Intent intent = new Intent(DownloadAppService.this, MainActivity.class);
                        // 告知已完成
                        intent.putExtra("completed", "yes");
                        // 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                        PendingIntent contentIntent = PendingIntent.getActivity(DownloadAppService.this, 0, intent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DownloadAppService.this);
                        mBuilder.setContent(null)
                                .setContentIntent(contentIntent)
                                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                                .setTicker("下载完成")
                                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                                .setOngoing(true)
                                .setSmallIcon(R.mipmap.logo);
                        mNotification = mBuilder.build();
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        stopSelf();// 停掉服务自身
                    }
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
            }
        }
    };

    public void cancelNotification() {
        onStart(null, 0);
        mHandler.sendEmptyMessage(2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        AppLog.e("==========onBind==========");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLog.e("downloadappservice onDestroy");
        MyApplication.getInstance().downApp = false;
        // 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
        // 取消通知
        mNotificationManager.cancel(NOTIFY_ID);
        stopSelf();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("downloadservice onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        System.out.println("downloadservice onRebind");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.e("==========onCreate==========");
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);

    }

    /**
     * 创建通知
     */
    @SuppressWarnings("deprecation")
    private void setUpNotification() {

        int icon = R.mipmap.logo;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_downloadapk);
        contentView.setTextViewText(R.id.downloadapk_name, getResources().getString(R.string.app_name) + "正在下载...");
        // 指定个性化视图
        mNotification.contentView = contentView;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 指定内容意图
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ToastUtil.shortShowToast("正在下载...");
        AppLog.e("开始下载000");
        saveFileName = FileUtils.getAppExternalStorageFile() + MyApplication.getInstance().APP_FILE_NAME;
        savePath = FileUtils.getAppExternalStorageFile().getAbsolutePath() + "/" + MyApplication.getInstance().APP_FILE_NAME;
        backActivity = intent.getIntExtra(SERVACESHARENAME, 0);
        apkurl = intent.getStringExtra(SERVICRINTENTURL);

        setUpNotification();
        AppLog.e("开始下载111");
        mDownLoadThread = new Thread(new Runnable() {
            @Override
            public void run() {


                AppLog.e("开始下载222");
                int lastRate = 0;
                int curProgress = 0;
                try {
                    URL url = new URL(apkurl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File ApkFile = new File(saveFileName);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        curProgress = (int) (((float) count / length) * 100);
                        // 更新进度
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.arg1 = curProgress;
                        if (curProgress >= lastRate + 1) {
                            mHandler.sendMessage(msg);
                            lastRate = curProgress;
                            if (callback != null)
                                callback.OnBackResult(curProgress);
                        }
                        if (numread <= 0) {
                            // 下载完成通知安装
                            mHandler.sendEmptyMessage(0);
                            // 下载完了，cancelled也要设置
                            canceled = true;
                            break;
                        }

                        fos.write(buf, 0, numread);
                    } while (!canceled);// 点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        mDownLoadThread.start();


        return START_NOT_STICKY;
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        DownloadAppService.this.startActivity(i);

    }

}
