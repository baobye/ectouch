package com.neusoft.baobye.ectouch.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class UserPriceView implements Serializable {

    private BigInteger totalId;

    private String endDate;

    private String insertDate;

    private int isDel;

    private double oneMoney;

    private  int status;

    private double sumMoney;

    private int type;

    private String updateDate;

    private BigInteger userId;

    private String wechat;


    private String tel;


    private String username;


    private String name;

    public BigInteger getTotalId() {
        return totalId;
    }

    public void setTotalId(BigInteger totalId) {
        this.totalId = totalId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public double getOneMoney() {
        return oneMoney;
    }

    public void setOneMoney(double oneMoney) {
        this.oneMoney = oneMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserPriceView(BigInteger totalId, String endDate, String insertDate, int isDel, double oneMoney, int status, double sumMoney, int type, String updateDate, BigInteger userId, String wechat, String tel, String username, String name) {
        this.totalId = totalId;
        this.endDate = endDate;
        this.insertDate = insertDate;
        this.isDel = isDel;
        this.oneMoney = oneMoney;
        this.status = status;
        this.sumMoney = sumMoney;
        this.type = type;
        this.updateDate = updateDate;
        this.userId = userId;
        this.wechat = wechat;
        this.tel = tel;
        this.username = username;
        this.name = name;
    }
}
