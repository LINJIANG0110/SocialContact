package com.qianyu.communicate.bukaSdk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangxiaowei on 2017/8/27.
 */

public class DocumentInfo implements Serializable{

    private List<?> folders;
    private List<DataBean> data;

    public List<?> getFolders() {
        return folders;
    }

    public void setFolders(List<?> folders) {
        this.folders = folders;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{

        /**
         * fileId : 346
         * fileName : 329994.j pg
         * fileSize : 1190726
         * fileUploadId : 1
         * fileUrl : http://files-storage.oss-cn-beijing.aliyuncs.com/1856ff3ac504ef6ce9484e16239ff44b.jpg
         * fileCreateTime : 1517539858231
         * flagDelete : 0
         * fileMd5 : 1856ff3ac504ef6ce9484e16239ff44b
         * applicationId : 28
         * path : 1
         * target_type :
         * transfer_type : 0
         * transfer_result :
         */

        private int fileId;
        private String fileName;
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
        private boolean isSelected = false;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getFileId() {
            return fileId;
        }

        public void setFileId(int fileId) {
            this.fileId = fileId;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
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
            return "DataBean{" +
                    "fileId=" + fileId +
                    ", fileName='" + fileName + '\'' +
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
                    ", isSelected=" + isSelected +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DocumentInfo{" +
                "folders=" + folders +
                ", data=" + data +
                '}';
    }
}
