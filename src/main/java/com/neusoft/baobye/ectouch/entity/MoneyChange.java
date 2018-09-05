package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MoneyChange {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long moneyId;
    private Integer type;
    private Integer moneyType;
    private Integer reasonType;
    private Long userId;
    private Long toUserId;
    private Double oneMoney;
    private Double realMoney;
    private Double sumMoney;
    private Double sxf;
    private String insertDate;
    private String upateDate;
    private Integer status;
    private String memo;
    private Integer isDel;

    public MoneyChange() {
    }

    public Long getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Long moneyId) {
        this.moneyId = moneyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    public Integer getReasonType() {
        return reasonType;
    }

    public void setReasonType(Integer reasonType) {
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

    public Double getOneMoney() {
        return oneMoney;
    }

    public void setOneMoney(Double oneMoney) {
        this.oneMoney = oneMoney;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public Double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(Double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Double getSxf() {
        return sxf;
    }

    public void setSxf(Double sxf) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
