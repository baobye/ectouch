package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MoneyChange {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long moneyId;
    private int type;
    private int moneyType;
    private int reasonType;
    private Long userId;
    private Long toUserId;
    private Double oneMoney;
    private Double realMoney;
    private Double sumMoney;
    private Double sxf;
    private String insertDate;
    private String upateDate;
    private int status;
    private String memo;
    private int isDel;

    public MoneyChange() {
    }

    public Long getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Long moneyId) {
        this.moneyId = moneyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public int getReasonType() {
        return reasonType;
    }

    public void setReasonType(int reasonType) {
        this.reasonType = reasonType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public double getOneMoney() {
        return oneMoney;
    }

    public void setOneMoney(double oneMoney) {
        this.oneMoney = oneMoney;
    }

    public double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(double realMoney) {
        this.realMoney = realMoney;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getSxf() {
        return sxf;
    }

    public void setSxf(double sxf) {
        this.sxf = sxf;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpateDate() {
        return upateDate;
    }

    public void setUpateDate(String upateDate) {
        this.upateDate = upateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
