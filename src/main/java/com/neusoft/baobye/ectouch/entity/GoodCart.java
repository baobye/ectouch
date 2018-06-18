package com.neusoft.baobye.ectouch.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GoodCart {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long recId;
    private Long userId;
    private Long goodsId;
    private String goodsName;
    private double goodsPrice;
    private int goodsNumber;

    private String path;

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long rec_id) {
        this.recId = recId;
    }

    public Long getUserId(Long userId) {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
