package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "wap_user")
public class WapUser implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    private Long userId;

    private String code;

    private String tel;

    private String wechat;

    private String name;

    private double dzb;

    private double jifen;

    private String idCard;

    private String username;

    private String password;

    private String insertDate;

    private String updateDate;

    private Long sqOperId;

    private String sqDate;

    private String bankCode;

    private String bankName;

    private String token;

    private int level;

    private int status;

    private Long ztId;

    private Long zsId;

    private Long teamId;

    private int isDel;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDzb() {
        return dzb;
    }

    public void setDzb(double dzb) {
        this.dzb = dzb;
    }

    public double getJifen() {
        return jifen;
    }

    public void setJifen(double jifen) {
        this.jifen = jifen;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getSqOperId() {
        return sqOperId;
    }

    public void setSqOperId(Long sqOperId) {
        this.sqOperId = sqOperId;
    }

    public String getSqDate() {
        return sqDate;
    }

    public void setSqDate(String sqDate) {
        this.sqDate = sqDate;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getZtId() {
        return ztId;
    }

    public void setZtId(Long ztId) {
        this.ztId = ztId;
    }

    public Long getZsId() {
        return zsId;
    }

    public void setZsId(Long zsId) {
        this.zsId = zsId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
