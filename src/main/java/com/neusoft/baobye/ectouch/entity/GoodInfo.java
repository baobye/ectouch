package com.neusoft.baobye.ectouch.entity;

import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GoodInfo {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long goodId;
    private String goodName;
    private String tCode;
    private Long sumSales;
    private Integer fTypeId;
    private Integer sTypeId;
    private String insertDate;
    private String updateDate;
    private String operId;
    private String unit;
    private Integer isDel;
//    花冠A 花朵B 花瓣C 花蕾D 花芽5 花粉6
    private Double aPrice;
    private Double bPrice;
    private Double cPrice;
    private Double dPrice;
    private Double ePrice;
    private Double fPrice;

    /**
     * 一对多 与  图片表的parentid做关联
     */
    @OneToMany(mappedBy = "parentid")
    @Where(clause="type=1")
    private Set<TbPictures> pictures = new HashSet<TbPictures>();



    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public Long getSumSales() {
        return sumSales;
    }

    public void setSumSales(Long sumSales) {
        this.sumSales = sumSales;
    }

    public Integer getfTypeId() {
        return fTypeId;
    }

    public void setfTypeId(Integer fTypeId) {
        this.fTypeId = fTypeId;
    }

    public Integer getsTypeId() {
        return sTypeId;
    }

    public void setsTypeId(Integer sTypeId) {
        this.sTypeId = sTypeId;
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

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Double getaPrice() {
        return aPrice;
    }

    public void setaPrice(Double aPrice) {
        this.aPrice = aPrice;
    }

    public Double getbPrice() {
        return bPrice;
    }

    public void setbPrice(Double bPrice) {
        this.bPrice = bPrice;
    }

    public Double getcPrice() {
        return cPrice;
    }

    public void setcPrice(Double cPrice) {
        this.cPrice = cPrice;
    }

    public Double getdPrice() {
        return dPrice;
    }

    public void setdPrice(Double dPrice) {
        this.dPrice = dPrice;
    }

    public Double getePrice() {
        return ePrice;
    }

    public void setePrice(Double ePrice) {
        this.ePrice = ePrice;
    }

    public Double getfPrice() {
        return fPrice;
    }

    public void setfPrice(Double fPrice) {
        this.fPrice = fPrice;
    }

    public Set<TbPictures> getPictures() {
        return pictures;
    }

    public void setPictures(Set<TbPictures> pictures) {
        this.pictures = pictures;
    }
}
