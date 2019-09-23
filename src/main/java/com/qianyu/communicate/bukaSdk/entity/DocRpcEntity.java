package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/8/23.
 *
 * rpc中message的bean对象
 */

public class DocRpcEntity {

    /**
     * count : 7
     * id : {7c6e2942-0443-40c8-9e64-1f6fc75e94f3}
     * mode : 0
     * page : 1
     * ppt : true
     * step : 0
     * url : http://s2.common.buka.tv/pdf-4edb6e8dfd5bffed0bf38f7ab0b1a05/pdf/html/1/index.html?v=6&ptv=4
     * view : 1
     */

    private int count;
    private String id;
    private int mode;
    private int page;
    private boolean ppt;
    private boolean white;
    private int step;
    private String url;
    private int view;

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isPpt() {
        return ppt;
    }

    public void setPpt(boolean ppt) {
        this.ppt = ppt;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
