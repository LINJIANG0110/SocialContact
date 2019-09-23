package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/9/18.
 */

public class StreamInfoEntity {

    /**
     * aid : 22211634
     * audio_codec_code : 2
     * avg_bitrate : 400
     * content_type : 2
     * height : 360
     * htmlppt : true
     * ip : 47.94.47.84
     * max_bitrate : 500
     * max_framerate : 15
     * min_bitrate : 300
     * partinter : false
     * server_group : buka
     * start_bitrate : 400
     * target_bitrate : 500
     * temp_live : false
     * vid : 22211635
     * video_codec_code : 1
     * video_type : 3
     * width : 640
     */

    private long aid;
    private int audio_codec_code;
    private int avg_bitrate;
    private int content_type;
    private int height;
    private boolean htmlppt;
    private String ip;
    private int max_bitrate;
    private int max_framerate;
    private int min_bitrate;
    private boolean partinter;
    private String server_group;
    private int start_bitrate;
    private int target_bitrate;
    private boolean temp_live;
    private long vid;
    private int video_codec_code;
    private int video_type;
    private int width;

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public int getAudio_codec_code() {
        return audio_codec_code;
    }

    public void setAudio_codec_code(int audio_codec_code) {
        this.audio_codec_code = audio_codec_code;
    }

    public int getAvg_bitrate() {
        return avg_bitrate;
    }

    public void setAvg_bitrate(int avg_bitrate) {
        this.avg_bitrate = avg_bitrate;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHtmlppt() {
        return htmlppt;
    }

    public void setHtmlppt(boolean htmlppt) {
        this.htmlppt = htmlppt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMax_bitrate() {
        return max_bitrate;
    }

    public void setMax_bitrate(int max_bitrate) {
        this.max_bitrate = max_bitrate;
    }

    public int getMax_framerate() {
        return max_framerate;
    }

    public void setMax_framerate(int max_framerate) {
        this.max_framerate = max_framerate;
    }

    public int getMin_bitrate() {
        return min_bitrate;
    }

    public void setMin_bitrate(int min_bitrate) {
        this.min_bitrate = min_bitrate;
    }

    public boolean isPartinter() {
        return partinter;
    }

    public void setPartinter(boolean partinter) {
        this.partinter = partinter;
    }

    public String getServer_group() {
        return server_group;
    }

    public void setServer_group(String server_group) {
        this.server_group = server_group;
    }

    public int getStart_bitrate() {
        return start_bitrate;
    }

    public void setStart_bitrate(int start_bitrate) {
        this.start_bitrate = start_bitrate;
    }

    public int getTarget_bitrate() {
        return target_bitrate;
    }

    public void setTarget_bitrate(int target_bitrate) {
        this.target_bitrate = target_bitrate;
    }

    public boolean isTemp_live() {
        return temp_live;
    }

    public void setTemp_live(boolean temp_live) {
        this.temp_live = temp_live;
    }

    public long getVid() {
        return vid;
    }

    public void setVid(long vid) {
        this.vid = vid;
    }

    public int getVideo_codec_code() {
        return video_codec_code;
    }

    public void setVideo_codec_code(int video_codec_code) {
        this.video_codec_code = video_codec_code;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "StreamInfoEntity{" +
                "aid=" + aid +
                ", audio_codec_code=" + audio_codec_code +
                ", avg_bitrate=" + avg_bitrate +
                ", content_type=" + content_type +
                ", height=" + height +
                ", htmlppt=" + htmlppt +
                ", ip='" + ip + '\'' +
                ", max_bitrate=" + max_bitrate +
                ", max_framerate=" + max_framerate +
                ", min_bitrate=" + min_bitrate +
                ", partinter=" + partinter +
                ", server_group='" + server_group + '\'' +
                ", start_bitrate=" + start_bitrate +
                ", target_bitrate=" + target_bitrate +
                ", temp_live=" + temp_live +
                ", vid=" + vid +
                ", video_codec_code=" + video_codec_code +
                ", video_type=" + video_type +
                ", width=" + width +
                '}';
    }
}
