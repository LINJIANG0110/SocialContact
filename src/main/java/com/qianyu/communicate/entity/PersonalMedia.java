package com.qianyu.communicate.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class PersonalMedia {

    /**
     * countMedia : 5
     */

    private int countMedia;
    private List<MediaList> mediaList;

    public List<MediaList> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaList> mediaList) {
        this.mediaList = mediaList;
    }

    public int getCountMedia() {
        return countMedia;
    }

    public void setCountMedia(int countMedia) {
        this.countMedia = countMedia;
    }
}
