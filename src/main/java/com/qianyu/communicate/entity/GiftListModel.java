package com.qianyu.communicate.entity;

/**
 * Created by Administrator on 2016/11/17.
 */

public class GiftListModel {
    private String image="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511271301814&di=2b542764d69ffd6425ac445fa7bf90ac&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fn0%2Fjfs%2Ft277%2F296%2F1179312606%2F378234%2Fc47b9387%2F543242bdNd0823674.jpg";//图片地址
    private String money="520钻石";//钻石
    private boolean isSelected;//

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "GiftListModel:"+isSelected;
    }
}
