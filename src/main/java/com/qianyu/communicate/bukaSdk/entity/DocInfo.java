package com.qianyu.communicate.bukaSdk.entity;

import java.util.List;

/**
 * Created by tangxiaowei on 2017/8/27.
 */

public class DocInfo {

    /**
     * file_transfer_total_size : 171
     * file_transfer_result : ["index.html?v=9"]
     * file_transfer_download_url : http://files-storage.oss-cn-beijing.aliyuncs.com/6196c0914e2ab9fa730a07bce412f979.pptx
     * file_transfer_from : ppt
     * file_transfer_root : http://bk2.document.buka.tv/pptx-6196c0914e2ab9fa730a07bce412f979/ppt/html/1/
     * file_transfer_flag_refresh : 0
     * file_transfer_size : 8815450
     * file_transfer_id : 1518182
     * file_transfer_to : html
     * file_transfer_progress : 1
     * file_transfer_flag : 2
     */

    private String file_transfer_total_size;
    private String file_transfer_download_url;
    private String file_transfer_from;
    private String file_transfer_root;
    private String file_transfer_flag_refresh;
    private String file_transfer_size;
    private String file_transfer_id;
    private String file_transfer_to;
    private String file_transfer_progress;
    private String file_transfer_flag;
    private List<String> file_transfer_result;

    public String getFile_transfer_total_size() {
        return file_transfer_total_size;
    }

    public void setFile_transfer_total_size(String file_transfer_total_size) {
        this.file_transfer_total_size = file_transfer_total_size;
    }

    public String getFile_transfer_download_url() {
        return file_transfer_download_url;
    }

    public void setFile_transfer_download_url(String file_transfer_download_url) {
        this.file_transfer_download_url = file_transfer_download_url;
    }

    public String getFile_transfer_from() {
        return file_transfer_from;
    }

    public void setFile_transfer_from(String file_transfer_from) {
        this.file_transfer_from = file_transfer_from;
    }

    public String getFile_transfer_root() {
        return file_transfer_root;
    }

    public void setFile_transfer_root(String file_transfer_root) {
        this.file_transfer_root = file_transfer_root;
    }

    public String getFile_transfer_flag_refresh() {
        return file_transfer_flag_refresh;
    }

    public void setFile_transfer_flag_refresh(String file_transfer_flag_refresh) {
        this.file_transfer_flag_refresh = file_transfer_flag_refresh;
    }

    public String getFile_transfer_size() {
        return file_transfer_size;
    }

    public void setFile_transfer_size(String file_transfer_size) {
        this.file_transfer_size = file_transfer_size;
    }

    public String getFile_transfer_id() {
        return file_transfer_id;
    }

    public void setFile_transfer_id(String file_transfer_id) {
        this.file_transfer_id = file_transfer_id;
    }

    public String getFile_transfer_to() {
        return file_transfer_to;
    }

    public void setFile_transfer_to(String file_transfer_to) {
        this.file_transfer_to = file_transfer_to;
    }

    public String getFile_transfer_progress() {
        return file_transfer_progress;
    }

    public void setFile_transfer_progress(String file_transfer_progress) {
        this.file_transfer_progress = file_transfer_progress;
    }

    public String getFile_transfer_flag() {
        return file_transfer_flag;
    }

    public void setFile_transfer_flag(String file_transfer_flag) {
        this.file_transfer_flag = file_transfer_flag;
    }

    public List<String> getFile_transfer_result() {
        return file_transfer_result;
    }

    public void setFile_transfer_result(List<String> file_transfer_result) {
        this.file_transfer_result = file_transfer_result;
    }
}
