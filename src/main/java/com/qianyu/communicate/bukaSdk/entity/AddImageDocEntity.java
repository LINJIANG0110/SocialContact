package com.qianyu.communicate.bukaSdk.entity;


/**
 * @说明: //图片添加到文档库成功后返回
 * @作者: hwk
 * @创建日期: 2017/9/11 19:33
 */
public class AddImageDocEntity {

    /**
     * document_upload_type : 1
     * room_id : 436496
     * document_name : android-img-1507715428585.png
     * document_create_time : 1507715430322
     * document_function_id : 1
     * document_id : 6853
     * document_url : http://s3.common.buka.tv/307328caf7349e6f29ab17e4ba395c75.png
     * document_type : white
     */

    private int document_upload_type;
    private int room_id;
    private String document_name;
    private long document_create_time;
    private int document_function_id;
    private int document_id;
    private String document_url;
    private String document_type;

    public int getDocument_upload_type() {
        return document_upload_type;
    }

    public void setDocument_upload_type(int document_upload_type) {
        this.document_upload_type = document_upload_type;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDocument_name() {
        return document_name;
    }

    public void setDocument_name(String document_name) {
        this.document_name = document_name;
    }

    public long getDocument_create_time() {
        return document_create_time;
    }

    public void setDocument_create_time(long document_create_time) {
        this.document_create_time = document_create_time;
    }

    public int getDocument_function_id() {
        return document_function_id;
    }

    public void setDocument_function_id(int document_function_id) {
        this.document_function_id = document_function_id;
    }

    public int getDocument_id() {
        return document_id;
    }

    public void setDocument_id(int document_id) {
        this.document_id = document_id;
    }

    public String getDocument_url() {
        return document_url;
    }

    public void setDocument_url(String document_url) {
        this.document_url = document_url;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
}
