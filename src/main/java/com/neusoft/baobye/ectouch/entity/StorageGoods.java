package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StorageGoods {

    @Id
    private Long stoGId;

    private Long storageId;

    private Long goodId;

    private int num;

    private double price;

    public Long getStoGId() {
        return stoGId;
    }

    public void setStoGId(Long stoGId) {
        this.stoGId = stoGId;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
