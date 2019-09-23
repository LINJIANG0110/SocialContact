package com.qianyu.communicate.bukaSdk.entity;

/**
 * Created by tangxiaowei on 2017/8/23.
 */

public class DocPagerEntity {
    private int step;    //第几步
    private int stepCount; //当前页总步数
    private int slide;  //当前第几页
    private int nextSlideIndex; //下一页页数
    private int previousSlideIndex; //上一页页数
    private int firstSlideIndex; //第一页页数
    private int lastSlideIndex;

    public int getLastSlideIndex() {
        return lastSlideIndex;
    }

    public void setLastSlideIndex(int lastSlideIndex) {
        this.lastSlideIndex = lastSlideIndex;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getSlide() {
        return slide;
    }

    public void setSlide(int slide) {
        this.slide = slide;
    }

    public int getNextSlideIndex() {
        return nextSlideIndex;
    }

    public void setNextSlideIndex(int nextSlideIndex) {
        this.nextSlideIndex = nextSlideIndex;
    }

    public int getPreviousSlideIndex() {
        return previousSlideIndex;
    }

    public void setPreviousSlideIndex(int previousSlideIndex) {
        this.previousSlideIndex = previousSlideIndex;
    }

    public int getFirstSlideIndex() {
        return firstSlideIndex;
    }

    public void setFirstSlideIndex(int firstSlideIndex) {
        this.firstSlideIndex = firstSlideIndex;
    }

    @Override
    public String toString() {
        return "DocPagerEntity{" +
                "step=" + step +
                ", stepCount=" + stepCount +
                ", slide=" + slide +
                ", nextSlideIndex=" + nextSlideIndex +
                ", previousSlideIndex=" + previousSlideIndex +
                ", firstSlideIndex=" + firstSlideIndex +
                ", lastSlideIndex=" + lastSlideIndex +
                '}';
    }
}
