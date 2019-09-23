package com.qianyu.communicate.bukaSdk;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.Surface;
import android.view.WindowManager;

import com.bkrtc_sdk.StreamStatus;
import com.bkrtc_sdk.VideoCodecInst;
import com.qianyu.communicate.bukaSdk.entity.StreamInfoEntity;

import org.haitao.common.utils.AppLog;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.BuKaCamera;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoRenderer;

import java.util.List;
import java.util.Map;

import tv.buka.sdk.BukaSDK;
import tv.buka.sdk.BukaSDKManager;
import tv.buka.sdk.entity.Session;
import tv.buka.sdk.listener.NetWorkListener;
import tv.buka.sdk.listener.ReceiptListener;
import tv.buka.sdk.utils.HttpUtils;
import tv.buka.sdk.utils.JsonUtils;
import tv.buka.sdk.utils.ResponseUtils;
import tv.buka.sdk.v3.config.Url;

import static com.bkrtc_sdk.bkrtc_impl.GetInstance;

/**
 * Created by tangxiaowei on 2017/9/8.
 */

public class MediaManager {
    private static final String TAG = "MediaManager";
    private static final int WIDTH = 320;
    private static final int HEIGHT = 180;
    private static final int START_BITRATE = 300;
    private static final String PL_NAME = "H264";
    private static final int MAX_BITRATE = 500;
    private static final int MIN_BITRATE = MAX_BITRATE / 2;
    private static final int TARGET_BITRATE = 500;
    private static final int MAX_FRAMERATE = 10;
    private static final int USE_X264 = 0;
    private static final int AUDIO_CODEC_ID = 15;

    //VideoCode
    private static final int H264_CODEC = 1;
    private static final int HW264_CODEC = 2;
    private static final int H265_CODEC = 3;

    //AudioCode
    private static final int AAC_CODEC = 1;
    private static final int OPUS_CODEC = 2;
    private static final int DEFAULT_CODEC = 3;
    private static final int OPUS8K_CODEC = 4;
    private static final int OPUS48K_CODEC = 5;

    private List<Long> mMediaIds;
    private int mMediaIdIndex = 0;
    private int mPublishMediaType = 3;
    private StreamStatus mStatusPublish;
    private StreamStatus mStatusPlay;
    private Map<String, StreamStatus> mStatusPlayMap;
    private BuKaCamera mCamera;
    private String mPublishIp;
    private int mPublishPort;
    private String mPlayIp;
    private int mPlayPort;
    private VideoCodecInst mPublishVcodec;
    private VideoCodecInst mPlayVcodec;
    private String mIdExtend;
    private int mCameraRotation = 1;

    public MediaManager() {
        mStatusPlayMap = new ArrayMap<>();
    }

    private void initPubVidec() {
        //video
        mPublishVcodec = new VideoCodecInst();
        mPublishVcodec.width = WIDTH;
        mPublishVcodec.height = HEIGHT;
        mPublishVcodec.startBitrate = START_BITRATE;
        mPublishVcodec.plName = PL_NAME;
        mPublishVcodec.maxBitrate = MAX_BITRATE;
        mPublishVcodec.minBitrate = MIN_BITRATE;
        mPublishVcodec.targetBitrate = TARGET_BITRATE;
        mPublishVcodec.maxFramerate = MAX_FRAMERATE;
        mPublishVcodec.useX264 = USE_X264;
    }

    private void initPlayVidec(StreamInfoEntity stream) {
        mPlayVcodec = new VideoCodecInst();
        if (stream != null) {
            mPlayVcodec.width = stream.getWidth();
            mPlayVcodec.height = stream.getHeight();
            mPlayVcodec.startBitrate = stream.getStart_bitrate();
            mPlayVcodec.plName = stream.getServer_group();
            mPlayVcodec.maxBitrate = stream.getMax_bitrate();
            mPlayVcodec.minBitrate = stream.getMin_bitrate();
            mPlayVcodec.targetBitrate = stream.getTarget_bitrate();
            mPlayVcodec.maxFramerate = stream.getMax_framerate();
            mPlayVcodec.useX264 = stream.getVideo_codec_code();
        } else {
            //video
            mPlayVcodec.width = WIDTH;
            mPlayVcodec.height = HEIGHT;
            mPlayVcodec.startBitrate = START_BITRATE;
            mPlayVcodec.plName = PL_NAME;
            mPlayVcodec.maxBitrate = MAX_BITRATE;
            mPlayVcodec.minBitrate = MIN_BITRATE;
            mPlayVcodec.targetBitrate = TARGET_BITRATE;
            mPlayVcodec.maxFramerate = MAX_FRAMERATE;
            mPlayVcodec.useX264 = USE_X264;
        }
    }

    private void publishDeleteStatus() {
        if (mStatusPublish != null && mStatusPublish.statusId != null) {
            BukaSDKManager.getStatusManager().deleteStatus(mStatusPublish.statusId);
        }
    }

    public void stopPulish() {
        if (mCamera != null && mStatusPublish != null) {
            mCamera.stopCapture();
            GetInstance().AveStopAudioSend(mStatusPublish.publish_stream_id);
            GetInstance().AveStopVideoCapture(mStatusPublish.publish_stream_id, 0);
            GetInstance().AveStopPublishStream(mStatusPublish.publish_stream_id, 3,
                    mStatusPublish.publish_vid, mStatusPublish.publish_aid);
            publishDeleteStatus();
        }
    }

    /**
     * 当网络断开时，重新发布
     */
    public boolean rePublish() {
        int success = -1;
        if (mStatusPublish != null) {
            success = GetInstance().AveRePublish(mPublishMediaType, mStatusPublish.publish_vid, mStatusPublish.publish_aid, "msg", mPublishIp, mPublishPort);
        }
        return success == 0;
    }

    public void startPlay(StreamInfoEntity stream, String msg, SurfaceViewRenderer surfaceViewRenderer, ReceiptListener<SurfaceViewRenderer> listener) {
        createStatusPlay(stream, msg, listener);

        initPlayVidec(stream);
        boolean hasAudio = false;
        boolean hasVideo = false;
        switch (stream.getVideo_type()) {
            case 1:
                hasAudio = true;
                hasVideo = false;
                break;
            case 2:
                hasAudio = false;
                hasVideo = true;
                break;
            case 3:
                hasAudio = true;
                hasVideo = true;
                break;
            default:
                hasAudio = true;
                hasVideo = true;
                break;
        }

        if (mStatusPlay == null || mStatusPlay.play_stream_id < 0) {
            listener.onError(msg);
            return;
        }

        if (hasVideo) {
            VideoRenderer videoRenderer = new VideoRenderer(surfaceViewRenderer);
            int aveStartRendererVideo = GetInstance().AveStartRendererVideo(mStatusPlay.play_stream_id, videoRenderer.nativeVideoRenderer);
            if (aveStartRendererVideo != 0) {
                AppLog.e(TAG, "AveStartRendererVideo" + aveStartRendererVideo);
                BukaSDK.errorLog("AveStartRendererVideo Error =" + aveStartRendererVideo);
            }
            int aveSetVideoReceiveCodec = GetInstance().AveSetVideoReceiveCodec(mStatusPlay.play_stream_id, 2, mPlayVcodec);
            if (aveSetVideoReceiveCodec != 0) {
                AppLog.e(TAG, "AveSetVideoReceiveCodec" + aveSetVideoReceiveCodec);
                BukaSDK.errorLog("AveSetVideoReceiveCodec Error =" + aveSetVideoReceiveCodec);
            }
        }
        //audio
        if (hasAudio) {
            int aveStartAudioReceive = GetInstance().AveStartAudioReceive(mStatusPlay.play_stream_id);
            if (aveStartAudioReceive != 0) {
                AppLog.e(TAG, "AveStartAudioReceive" + aveStartAudioReceive);
                BukaSDK.errorLog("AveStartAudioReceive Error =" + aveStartAudioReceive);
            }
            int aveStartAudioPlayout = GetInstance().AveStartAudioPlayout(mStatusPlay.play_stream_id);
            if (aveStartAudioPlayout != 0) {
                AppLog.e(TAG, "AveStartAudioPlayout" + aveStartAudioPlayout);
                BukaSDK.errorLog("AveStartAudioPlayout Error =" + aveStartAudioPlayout);
            }
        }

        listener.onSuccess(surfaceViewRenderer);
    }

    private void createStatusPlay(StreamInfoEntity stream, String msg, ReceiptListener<SurfaceViewRenderer> listener) {
        long localAid = getMediaId();
        long localVid = getMediaId();
        if (mRtpPubAddress == null || mRtpPubAddress.size() == 0) {
            return;
        }
        String ip = mRtpSubAddress.get(mServerIpIndex);
        int port = Integer.valueOf(mRtpSubPort.get(mServerIpIndex));
        AppLog.e(TAG, "createStatusPlay " + "vid=" + stream.getVid() + "; aid=" + stream.getAid() + "; ip=" + ip + "; port=" + port + "; localAid=" + localAid + "; localVid=" + localVid);

        if (localAid != 0 && localVid != 0 && localAid != localVid && stream.getVid() != stream.getAid()) {
            if (mStatusPlayMap.containsKey(stream.getVid() + "_" + stream.getAid())) {
                StreamStatus streamStatus = mStatusPlayMap.get(stream.getVid() + "_" + stream.getAid());
                if (streamStatus.play_vid == stream.getVid() && streamStatus.play_aid == stream.getAid()) {
                    listener.onError(msg);
                    return;
                }
            }

            mStatusPlay = GetInstance().AveStartPlayStream(stream.getVideo_type(), localVid, localAid, stream.getVid(), stream.getAid(), 1, 2, stream.getAvg_bitrate(), stream.getWidth(), stream.getHeight(), 15, msg, ip, port);
        } else {
            listener.onError(msg);
            return;
        }

        AppLog.e(TAG, "mStatusPlay=" + mStatusPlay.publish_stream_id + ";" + mStatusPlay.play_stream_id + ";" + mStatusPlay.return_value);
        if (mStatusPlay.return_value != 0) {
            if (mStatusPlay.play_stream_id != -1) {
                GetInstance().AveStopPlayStream(mStatusPlay.play_stream_id, 3, localVid, localAid);
            }
            BukaSDK.errorLog("createStatusPlay onError");
            if (mServerIpIndex < mRtpSubAddress.size() - 1) {
                mServerIpIndex++;
                createStatusPlay(stream, msg, listener);
            } else {
                AppLog.e(TAG, "mStatusPlay ip error");
                mServerIpIndex = 0;
            }
        } else {
            mServerIpIndex = 0;
            mStatusPlay.play_vid = localVid;
            mStatusPlay.play_aid = localAid;
            mPlayIp = ip;
            mPlayPort = port;
            mStatusPlayMap.put(stream.getVid() + "_" + stream.getAid(), mStatusPlay);
        }
    }

    public void stopPlay(StreamInfoEntity stream, SurfaceViewRenderer surfaceViewRenderer) {
        if (mStatusPlayMap != null && mStatusPlayMap.size() > 0 && mStatusPlayMap.containsKey(stream.getVid() + "_" + stream.getAid())) {
            StreamStatus streamStatus = mStatusPlayMap.get(stream.getVid() + "_" + stream.getAid());

            VideoRenderer videoRenderer = new VideoRenderer(surfaceViewRenderer);
            GetInstance().AveStopRendererVideo(streamStatus.play_stream_id, videoRenderer.nativeVideoRenderer);
            GetInstance().AveStopAudioReceive(streamStatus.play_stream_id);
            GetInstance().AveStopAudioPlayout(streamStatus.play_stream_id);
            int success = GetInstance().AveStopPlayStream(streamStatus.play_stream_id, stream.getVideo_type(), streamStatus.play_vid, streamStatus.play_aid);
            AppLog.e(TAG, "stopPlay success=" + success + "play_stream_id=" + streamStatus.play_stream_id + "; streamStatus.vid=" + streamStatus.play_vid + "; streamStatus.aId=" + streamStatus.play_aid);
            if (success == 0 || success == -3) {
                BukaSDKManager.getStatusManager().deleteStatus(streamStatus.statusId);
            }
        }
    }

    /**
     * 停止播放流的音频
     * @param stream
     * @return
     */
    public boolean audioStopPlay(StreamInfoEntity stream) {
        int audioPlay = -1;
        if (mStatusPlayMap != null && mStatusPlayMap.size() > 0
                && mStatusPlayMap.containsKey(getStreamKey(stream.getVid(), stream.getAid()))) {
            StreamStatus play = mStatusPlayMap.get(getStreamKey(stream.getVid(), stream.getAid()));
            audioPlay = GetInstance().AveStopAudioReceive(play.play_stream_id);
            audioPlay += GetInstance().AveStopAudioPlayout(play.play_stream_id);
        }
        return audioPlay == 0;
    }

    /**
     * 播放流的音频
     * @param stream
     * @return
     */
    public boolean audioPlay(StreamInfoEntity stream) {
        int audioPlay = -1;
        if (mStatusPlayMap != null && mStatusPlayMap.size() > 0
                && mStatusPlayMap.containsKey(getStreamKey(stream.getVid(), stream.getAid()))) {
            StreamStatus play = mStatusPlayMap.get(getStreamKey(stream.getVid(), stream.getAid()));
            audioPlay = GetInstance().AveStartAudioReceive(play.play_stream_id);
            audioPlay += GetInstance().AveStartAudioPlayout(play.play_stream_id);
        }
        return audioPlay == 0;
    }

    public boolean isPublish(Long aLong, Long aLong1) {
        return mCamera != null && mStatusPublish != null;
    }

    private long getMediaId() {
        long id = 0;
        if (mMediaIds != null && mMediaIds.size() != 0 && mMediaIdIndex < mMediaIds.size()) {
            id = mMediaIds.get(mMediaIdIndex++);
        }

        if (mMediaIdIndex >= mMediaIds.size() - 2) {
            markId(20, mIdExtend, null);
        }

        return id;
    }

    public void markId(final int count, final String idExtend, final ReceiptListener<List<Long>> receiptListener) {
        this.mIdExtend = idExtend;
        Session session = BukaSDKManager.getConnectManager().getSession();
        if (session == null) {
            receiptListener.onError(idExtend);
            return;
        }
        String token = session.getSession_token();
        if (TextUtils.isEmpty(token)) {
            receiptListener.onError(idExtend);
            return;
        }

        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", "media");
            obj.put("num", count);
            obj.put("index_extend", idExtend);
            obj.put("session_token", BukaSDKManager.getConnectManager().getSession().getSession_token());
        } catch (JSONException var6) {
            var6.printStackTrace();
        }

        HttpUtils.startPostAsyncRequest(Url.getInstance().onlyIndex(), obj.toString(), 1, new NetWorkListener() {
            @Override
            public void requestDidSuccess(String res, int tag) {
                if (!ResponseUtils.isSuccessStr(res)) {
                    BukaSDK.errorLog("MediaManager:获取ID失败");
                    markId(count, idExtend, receiptListener);
                    if (receiptListener != null) {
                        receiptListener.onError(res);
                    }

                } else {
                    mMediaIds = JsonUtils.getBeanList(res, Long.class);
                    mMediaIdIndex = 0;
                    if (receiptListener != null) {
                        receiptListener.onSuccess(JsonUtils.getBeanList(res, Long.class));
                    }

                }
            }

            @Override
            public void requestDidFailed(String o,int tag) {
                BukaSDK.errorLog("MediaManager:获取ID失败");
                markId(count, idExtend, receiptListener);
                if (receiptListener != null) {
                    receiptListener.onError(o);
                }

            }
        });
    }

    public void onDestroy() {
        stopPulish();
    }

    private List<String> mRtpPubAddress;
    private List<String> mRtpPubPort;
    private List<String> mLiveAddress;
    private List<String> mLivePort;
    private List<String> mRtpSubAddress;
    private List<String> mRtpSubPort;
    private int mServerIpIndex = 0;

    private int getRotation(int index) {
        int orientation = 0;
        int rotation = 0;
        WindowManager wm = (WindowManager) BukaSDKManager.getContext().getSystemService(Context.WINDOW_SERVICE);
        switch (wm.getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_90:
                orientation = 90;
                break;
            case Surface.ROTATION_180:
                orientation = 180;
                break;
            case Surface.ROTATION_270:
                orientation = 270;
                break;
            case Surface.ROTATION_0:
            default:
                orientation = 0;
                break;
        }
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(index, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            orientation = 360 - orientation;
        }
        rotation = (info.orientation + orientation) % 360;

        return rotation;
    }

    /**
     * 切换前者摄像头
     *
     * @param context
     * @param surface
     */
    public void faceCamera(int mCamerId, Context context, SurfaceViewRenderer surface) {
        if (mStatusPublish != null && mCamera != null) {
            mCamera.stopCapture();
            //capture
            mCameraRotation = mCamerId;
            mCamera = new BuKaCamera(context, mCamerId);
            int rota = getRotation(mCamerId);
            mCamera.startCapture(surface.getHolder(), WIDTH, HEIGHT, MAX_FRAMERATE);
            mCamera.setPreviewRotation(rota);
            mCamera.SetStreamId(mStatusPublish.publish_stream_id);
        }
    }

    /**
     * 切换后置摄像头
     *
     * @param context
     * @param surface
     */
    public void backCamera(int mCamerId,Context context, SurfaceViewRenderer surface) {
        if (mStatusPublish != null && mCamera != null) {
            mCamera.stopCapture();
            //capture
            mCameraRotation = mCamerId;
            mCamera = new BuKaCamera(context, mCamerId);
            int rota = getRotation(mCamerId);
            mCamera.startCapture(surface.getHolder(), WIDTH, HEIGHT, MAX_FRAMERATE);
            mCamera.setPreviewRotation(rota);
            mCamera.SetStreamId(mStatusPublish.publish_stream_id);
        }
    }

    /**
     * 切换音频到外放
     *
     * @param context
     */
    public void loudSpeaker(Activity context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(true);
        context.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);//控制声音的大小
        audioManager.setMode(AudioManager.STREAM_VOICE_CALL);

    }

    /**
     * 切换音频到耳机或听筒
     *
     * @param context
     */
    public void microSpeaker(Activity context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(false);
        context.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);//控制声音的大小
        audioManager.setMode(AudioManager.STREAM_VOICE_CALL);
    }

    /**
     * 停止推流音频
     *
     * @return
     */
    public boolean audioStopPublish() {
        int audioSend = -1;
        if (mStatusPublish != null) {
            audioSend = GetInstance().AveStopAudioSend(mStatusPublish.publish_stream_id);
        }
        return audioSend == 0;
    }

    /**
     * 恢复推流音频
     *
     * @return
     */
    public boolean audioPublish() {
        int audioSend = -1;
        if (mStatusPublish != null) {
            audioSend = GetInstance().AveStartAudioSend(mStatusPublish.publish_stream_id);
        }
        return audioSend == 0;
    }

    /**
     * 停止推流视频
     *
     * @return 停止成功
     */
    public boolean videoStopPublish() {
        int videoCapture = -1;
        if (mStatusPublish != null) {
            videoCapture = GetInstance().AveStopVideoCapture(mStatusPublish.publish_stream_id, mCameraRotation);
        }
        return videoCapture == 0;
    }

    /**
     * 恢复推流视频
     *
     * @return 恢复成功
     */
    public boolean videoPublish() {
        int videoCapture = -1;
        if (mStatusPublish != null) {
            videoCapture = GetInstance().AveStartVideoCapture(mStatusPublish.publish_stream_id, mCameraRotation, null);
        }

        return videoCapture == 0;
    }

    private String getStreamKey(long vid, long aid) {
        return vid + "_" + aid;
    }
}
