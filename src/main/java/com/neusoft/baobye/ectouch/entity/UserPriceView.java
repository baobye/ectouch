package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;

public class UserPriceView   implements Serializable {
    private BigInteger totalId;
    private Double jjbTotal;
    private String wechat;
    private String tel;
    private String userName;
    private String name;
    private String insertDate;
    private BigInteger userId;

    public BigInteger getTotalId() {
        return totalId;
    }

    public void setTotalId(BigInteger totalId) {
        this.totalId = totalId;
    }

    public Double getJjbTotal() {
        return jjbTotal;
    }

    public void setJjbTotal(Double jjbTotal) {
        this.jjbTotal = jjbTotal;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public UserPriceView(BigInteger totalId, Double jjbTotal, String wechat, String tel, String userName, String name, String insertDate, BigInteger userId) {
        this.totalId = totalId;
        this.jjbTotal = jjbTotal;
        this.wechat = wechat;
        this.tel = tel;
        this.userName = userName;
        this.name = name;
        this.insertDate = insertDate;
        this.userId = userId;
    }
}
