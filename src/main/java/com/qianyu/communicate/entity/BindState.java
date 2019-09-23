package com.qianyu.communicate.entity;

/**
 * Created by JavaDog on 2019-5-20.
 */

public class BindState {

    /**
     * bindingWB : 0
     * bindingWX : 1
     * bindingQQ : 0
     */

    private int bindingWB;
    private int bindingWX;
    private int bindingQQ;

    public int getBindingWB() {
        return bindingWB;
    }

    public void setBindingWB(int bindingWB) {
        this.bindingWB = bindingWB;
    }

    public int getBindingWX() {
        return bindingWX;
    }

    public void setBindingWX(int bindingWX) {
        this.bindingWX = bindingWX;
    }

    public int getBindingQQ() {
        return bindingQQ;
    }

    public void setBindingQQ(int bindingQQ) {
        this.bindingQQ = bindingQQ;
    }
}
