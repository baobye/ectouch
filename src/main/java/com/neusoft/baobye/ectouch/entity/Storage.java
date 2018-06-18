package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Storage {

    @Id
    private Long storageId;

    private int type;

    private int reasonType;

    private Long orderId;

    private Long applySysId;

    private String applyDate;

    private int handleSysId;

    private String handleDate;

    private int assetsType;

    private int status;

    private int isDel;

    @OneToMany(mappedBy = "storageId")
    private Set<StorageGoods> storageGoods = new HashSet<StorageGoods>();

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReasonType() {
        return reasonType;
    }

    public void setReasonType(int reasonType) {
        this.reasonType = reasonType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getApplySysId() {
        return applySysId;
    }

    public void setApplySysId(Long applySysId) {
        this.applySysId = applySysId;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getHandleSysId() {
        return handleSysId;
    }

    public void setHandleSysId(int handleSysId) {
        this.handleSysId = handleSysId;
    }

    public String getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
    }

    public int getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(int assetsType) {
        this.assetsType = assetsType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Set<StorageGoods> getStorageGoods() {
        return storageGoods;
    }

    public void setStorageGoods(Set<StorageGoods> storageGoods) {
        this.storageGoods = storageGoods;
    }
}
