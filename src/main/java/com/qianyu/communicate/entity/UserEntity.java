package com.qianyu.communicate.entity;

import java.io.Serializable;

/**
 * Created by JavaDog on 2019-3-15.
 */

public class UserEntity implements Serializable{

    private int otherType;
    private String otherToken;
    private String code;
    private String phone;
    private String nickName;
    private String headUrl;
    private int age=0;
    private int sex=0;
    private String birthDay;
    private String address;

    public int getOtherType() {
        return otherType;
    }

    public void setOtherType(int otherType) {
        this.otherType = otherType;
    }

    public String getOtherToken() {
        return otherToken;
    }

    public void setOtherToken(String otherToken) {
        this.otherToken = otherToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
