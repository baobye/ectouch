package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PrizeTotal implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long totalId;

    private Long userId;

    private double jjbTotal;
    //招募奖
    private double zmj;
    //平推荐
    private double ptj;
    //垄断奖
    private double ldj;

    private double ldjkc;


    private String insertDate;

    private String updateDate;

    private String endDate;

    private int status;

    private int memo;

    private int isDel;

    public Long getTotalId() {
        return totalId;
    }

    public void setTotalId(Long totalId) {
        this.totalId = totalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getJjbTotal() {
        return jjbTotal;
    }

    public void setJjbTotal(double jjbTotal) {
        this.jjbTotal = jjbTotal;
    }

    public double getZmj() {
        return zmj;
    }

    public void setZmj(double zmj) {
        this.zmj = zmj;
    }

    public double getPtj() {
        return ptj;
    }

    public void setPtj(double ptj) {
        this.ptj = ptj;
    }

    public double getLdj() {
        return ldj;
    }

    public void setLdj(double ldj) {
        this.ldj = ldj;
    }

    public double getLdjkc() {
        return ldjkc;
    }

    public void setLdjkc(double ldjkc) {
        this.ldjkc = ldjkc;
    }

    public int getMemo() {
        return memo;
    }

    public void setMemo(int memo) {
        this.memo = memo;
    }
}
