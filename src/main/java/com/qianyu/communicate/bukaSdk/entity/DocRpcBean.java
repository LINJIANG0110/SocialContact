package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/8/25.
 */

public class DocRpcBean {
    private int mode;
    private String url;
    private int page;
    private int step;
    private boolean ppt;
    private int count;
    private int view;
    private String id;

    public boolean isPpt() {
        return ppt;
    }

    public void setPpt(boolean ppt) {
        this.ppt = ppt;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
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

    @Override
    public String toString() {
        return "DocRpcBean{" +
                "mode=" + mode +
                ", url='" + url + '\'' +
                ", page=" + page +
                ", step=" + step +
                ", ppt=" + ppt +
                ", count=" + count +
                ", view=" + view +
                ", id='" + id + '\'' +
                '}';
    }
}
