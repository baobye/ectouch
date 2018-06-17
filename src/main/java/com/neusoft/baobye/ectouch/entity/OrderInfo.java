package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderInfo {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long orderId;

    private String orderCode;

    private Long userId;

    private int status;

    private double goodPriceTotal;

    private double orderPriceTotal;

    private double logisticsFee ;

    private String logisticsCode;

    private Long addId;

    private String userMark;

    private String insertDate;

    private String payDate;

    private String sentDate;

    private int isDel;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getGoodPriceTotal() {
        return goodPriceTotal;
    }

    public void setGoodPriceTotal(double goodPriceTotal) {
        this.goodPriceTotal = goodPriceTotal;
    }

    public double getOrderPriceTotal() {
        return orderPriceTotal;
    }

    public void setOrderPriceTotal(double orderPriceTotal) {
        this.orderPriceTotal = orderPriceTotal;
    }

    public double getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(double logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public Long getAddId() {
        return addId;
    }

    public void setAddId(Long addId) {
        this.addId = addId;
    }

    public String getUserMark() {
        return userMark;
    }

    public void setUserMark(String userMark) {
        this.userMark = userMark;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
