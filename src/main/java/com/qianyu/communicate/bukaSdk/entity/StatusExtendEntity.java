package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/9/18.
 */

public class StatusExtendEntity {

    /**
     * room_sdk_id : 233701
     * session_extend :
     * session_id : 00-16-3e-10-32-dd_slYK4J_1505705380137530673
     * stream_info :
     * user_extend :
     */

    private String room_sdk_id;
    private String session_extend;
    private String session_id;
    private String stream_info;
    private String user_extend;

    public String getRoom_sdk_id() {
        return room_sdk_id;
    }

    public void setRoom_sdk_id(String room_sdk_id) {
        this.room_sdk_id = room_sdk_id;
    }

    public String getSession_extend() {
        return session_extend;
    }

    public void setSession_extend(String session_extend) {
        this.session_extend = session_extend;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getStream_info() {
        return stream_info;
    }

    public void setStream_info(String stream_info) {
        this.stream_info = stream_info;
    }

    public String getUser_extend() {
        return user_extend;
    }

    public void setUser_extend(String user_extend) {
        this.user_extend = user_extend;
    }

    @Override
    public String toString() {
        return "StatusExtendEntity{" +
                "room_sdk_id='" + room_sdk_id + '\'' +
                ", session_extend='" + session_extend + '\'' +
                ", session_id='" + session_id + '\'' +
                ", stream_info='" + stream_info + '\'' +
                ", user_extend='" + user_extend + '\'' +
                '}';
    }
}
