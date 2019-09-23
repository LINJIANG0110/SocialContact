package com.qianyu.communicate.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.callback.DeleteCallback;
import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.AuthenticationType;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSData;
import com.alibaba.sdk.android.oss.storage.OSSFile;
import com.alibaba.sdk.android.oss.util.OSSToolKit;

import org.haitao.common.utils.AppLog;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author wang
 * @version V1.0
 *          上传图片的基本步骤
 *          第一步 到自己服务器获取 bucketName、时间、签名 (不能写成单利的)
 *          第二部 上传阿里云
 * @Description:this class is user for 阿里云 工具类
 * @date 2015-10-10 下午4:00:14
 */


public class AliyunUploadUtils {

    private final String videoRootPath = "http://meifaxuetang.oss-cn-beijing.aliyuncs.com/";

    private final String imageRootPath = "http://meifaxuetang.oss-cn-beijing.aliyuncs.com/";
    // http://meifaxuetang.oss-cn-shanghai.aliyuncs.com/

    static final String accessKey = "LTAIBPhVINdtcEPv"; // 测试代码没有考虑AK/SK的安全性
    static final String screctKey = "5E4cZisfZN6VAVA4r9QDFWhyt390Ws";
    //换   节点
    static final String HOST_HANGZHOU = "oss-cn-hangzhou.aliyuncs.com";//节点  目前只有杭州 北京两个节点
    static final String HOST_BEIJING = "oss-cn-beijing.aliyuncs.com";  //节点  目前只有杭州 北京两个节点
    /**
     * 空间
     */
    public String bucketVideoName = "meifaxuetang";
    /**
     * 空间
     */
    public String bucketImageName = "meifaxuetang"; //neiquan-image-hz

    public String token;

    private OSSService ossService;

    private OSSBucket bucket;

    //	public AliyunUploadUtils(Context context) {
//		ossService = OSSServiceProvider.getService();
//		init(context);
//	}
    private static AliyunUploadUtils single = null;

    private AliyunUploadUtils(Context context) {
        ossService = OSSServiceProvider.getService();
        init(context);
    }

    //静态工厂方法
    public static AliyunUploadUtils getInstance(Context context) {
        if (single == null) {
            single = new AliyunUploadUtils(context);
        }
        return single;
    }

    /**
     * 上传图片到阿里云  异步
     *
     * @return
     */
    public void uploadPhoto(String photoPath, final AliUpLoadCallBack aliUpLoadCallBack) {
        ossService.setGlobalDefaultHostId(HOST_BEIJING); // 设置region host 即 endpoint  图片是 北京的节点    视频是杭州的节点
        uploadFile(photoPath, ".png", imageRootPath, bucketImageName, aliUpLoadCallBack);
    }

    /**
     * 上传视频到阿里云
     */
    public void uploadVideo(String videoPath, final AliUpLoadCallBack aliUpLoadCallBack) {
        ossService.setGlobalDefaultHostId(HOST_BEIJING); // 设置region host 即 endpoint  图片是 北京的节点    视频是杭州的节点
        uploadFile(videoPath, ".mp4", videoRootPath, bucketVideoName, aliUpLoadCallBack);
    }

    /**
     * 上传文件
     *
     * @param filePath          文件路径
     * @param extension         扩展名
     * @param rootPath          服务器根目录
     * @param bucketName        bucket
     * @param aliUpLoadCallBack 回调
     */
    private void uploadFile(String filePath, String extension, final String rootPath, final String bucketName, final AliUpLoadCallBack aliUpLoadCallBack) {
        AppLog.e("ossService.getOssBucket");
        bucket = ossService.getOssBucket(bucketName);

        OSSFile bigfFile = ossService.getOssFile(bucket, getRandomName(extension));
        try {
            if (extension.equals(".mp4")) {
                bigfFile.setUploadFilePath(filePath, "video/mpeg");
            } else {
                bigfFile.setUploadFilePath(filePath, "image/jpeg");
            }
            bigfFile.ResumableUploadInBackground(new SaveCallback() {

                @Override
                public void onSuccess(final String objectKey) {
                    if (aliUpLoadCallBack != null) {
                        Tools.runOnUI(new Runnable() {

                            @Override
                            public void run() {
                                aliUpLoadCallBack.onSuccess(rootPath, bucketName, objectKey);
                            }
                        });
                    }
                }


                @Override
                public void onProgress(final String objectKey, final int byteCount, final int totalSize) {
                    if (aliUpLoadCallBack != null) {
                        Tools.runOnUI(new Runnable() {

                            @Override
                            public void run() {

                                aliUpLoadCallBack.onProgress(objectKey, byteCount, totalSize);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(final String objectKey, final OSSException ossException) {
                    if (aliUpLoadCallBack != null) {
                        Tools.runOnUI(new Runnable() {

                            @Override
                            public void run() {

                                aliUpLoadCallBack.onFailure(objectKey, ossException);
                            }
                        });
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除阿里云 图片 异步
     *
     * @return
     */
    public void deletePhoto(String photoPath, final AliUpLoadCallBack aliUpLoadCallBack) {
        deleteFile(photoPath, imageRootPath, bucketImageName, aliUpLoadCallBack);
    }

    /**
     * 删除阿里云视频
     */
    public void deleteVideo(String videoPath, final AliUpLoadCallBack aliUpLoadCallBack) {
        deleteFile(videoPath, videoRootPath, bucketVideoName, aliUpLoadCallBack);
    }


    /**
     * 删除文件
     *
     * @param filepath
     * @param aliUpLoadCallBack
     */
    private void deleteFile(String filepath, final String rootPath, final String bucketName, final AliUpLoadCallBack aliUpLoadCallBack) {
        OSSData data = ossService.getOssData(bucket, filepath);
        data.deleteInBackground(new DeleteCallback() {

            @Override
            public void onSuccess(String objectKey) {
                AppLog.e("异步删除成功-------------");
                if (aliUpLoadCallBack != null) {
                    aliUpLoadCallBack.onSuccess(rootPath, bucketName, objectKey);
                }
            }

            @Override
            public void onFailure(String objectKey, OSSException ossException) {
                AppLog.e("异步删除失败-------------");
                if (aliUpLoadCallBack != null) {
                    aliUpLoadCallBack.onFailure(objectKey, ossException);
                }
            }
        });
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // 初始化设置
        ossService.setApplicationContext(context);
        // 这个地方是个坑 一开始写的 neiquan-video.oss-cn-beijing.aliyuncs.com
        ossService.setGlobalDefaultHostId(HOST_BEIJING); // 设置region host 即 endpoint  图片是 北京的节点    视频是杭州的节点
        ossService.setGlobalDefaultACL(AccessControlList.PUBLIC_READ); // 默认为private neiquan-video-hz.oss-cn-hangzhou.aliyuncs.com
        ossService.setAuthenticationType(AuthenticationType.ORIGIN_AKSK); // 设置加签类型为原始AK/SK加签
        // 每次都会掉用这个方法
        ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() { // 设置全局默认加签器
            @Override
            public String generateToken(String httpMethod, String md5, String type, String date,
                                        String ossHeaders, String resource) {
                String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date + "\n" + ossHeaders + resource;

                String url = resource.split("\\?")[0].replaceAll("/" + bucketVideoName + "/", "").replaceAll("/" + bucketImageName + "/", "");
                if (url.endsWith(".png")) {
                    url = imageRootPath + url;
                } else {
                    url = videoRootPath + url;
                }
                AppLog.e("url===" + url);
                String sigin = OSSToolKit.generateToken(accessKey, screctKey, content);
                AppLog.e("=====sigin====" + sigin);
                return sigin;
            }
        });
        ossService.setCustomStandardTimeWithEpochSec(System.currentTimeMillis() / 1000);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectTimeout(15 * 1000); // 设置全局网络连接超时时间，默认30s
        conf.setSocketTimeout(15 * 1000); // 设置全局socket超时时间，默认30s
        conf.setMaxConnections(50); // 设置全局最大并发网络链接数, 默认50
        ossService.setClientConfiguration(conf);
    }

    /**
     * 时间戳毫秒加 加uuid  //随机数20-10
     * 生成随机名字
     */
    @SuppressLint("SimpleDateFormat")
    private String getRandomName(String exten) {
        String uuid = java.util.UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date());
        if (".mp4".equals(exten)) {
            return date + "_video_" + uuid + exten;
        }
        return date + "_img_" + uuid + exten;
    }

}
