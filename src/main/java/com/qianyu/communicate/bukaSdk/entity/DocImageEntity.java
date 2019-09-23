package com.qianyu.communicate.bukaSdk.entity;


import java.io.Serializable;

/**
 * @说明: //文档列表转换完成后的列表对象
 * @作者: hwk
 * @创建日期: 2017/9/10 19:33
 */
public class DocImageEntity implements Serializable{
    /**
     * fileId : 138
     * fileSize : 4833144
     * fileUploadId : 1
     * fileUrl : http://files-storage.oss-cn-beijing.aliyuncs.com/445cb59ce15840f534d94ff1b53848e4.pptx
     * fileCreateTime : 1516883366404
     * flagDelete : 0
     * fileMd5 : 445cb59ce15840f534d94ff1b53848e4
     * applicationId : 28
     * path : 1
     * target_type :
     * transfer_type : 0
     * transfer_result :
     */
    private String url;
    private String type;
    private String fileName;
    private String flag_transfer;//0表示没动画，1表示有动画
    private String md5;
    private int fileId;
    private int fileSize;
    private int fileUploadId;
    private String fileUrl;
    private long fileCreateTime;
    private String flagDelete;
    private String fileMd5;
    private int applicationId;
    private String path;
    private String target_type;
    private int transfer_type;
    private String transfer_result;
    private boolean isSelected=false;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFlag_transfer() {
        return flag_transfer;
    }

    public void setFlag_transfer(String flag_transfer) {
        this.flag_transfer = flag_transfer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileUploadId() {
        return fileUploadId;
    }

    public void setFileUploadId(int fileUploadId) {
        this.fileUploadId = fileUploadId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public long getFileCreateTime() {
        return fileCreateTime;
    }

    public void setFileCreateTime(long fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
    }

    public String getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(String flagDelete) {
        this.flagDelete = flagDelete;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public int getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(int transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getTransfer_result() {
        return transfer_result;
    }

    public void setTransfer_result(String transfer_result) {
        this.transfer_result = transfer_result;
    }

    @Override
    public String toString() {
        return "DocImageEntity{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", fileName='" + fileName + '\'' +
                ", flag_transfer='" + flag_transfer + '\'' +
                ", md5='" + md5 + '\'' +
                ", isSelected=" + isSelected +
                ", fileId=" + fileId +
                ", fileSize=" + fileSize +
                ", fileUploadId=" + fileUploadId +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileCreateTime=" + fileCreateTime +
                ", flagDelete='" + flagDelete + '\'' +
                ", fileMd5='" + fileMd5 + '\'' +
                ", applicationId=" + applicationId +
                ", path='" + path + '\'' +
                ", target_type='" + target_type + '\'' +
                ", transfer_type=" + transfer_type +
                ", transfer_result='" + transfer_result + '\'' +
                '}';
    }
}
