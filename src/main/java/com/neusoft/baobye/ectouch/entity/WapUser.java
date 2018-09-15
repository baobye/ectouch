package com.neusoft.baobye.ectouch.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "wap_user")
public class WapUser implements UserDetails,Serializable {
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
    @Transient
    private boolean enabled;
    @Transient
    private Collection<? extends GrantedAuthority> authorities;

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

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public WapUser() {
    }

    public WapUser(Long userId, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public WapUser(Long userId, String code, String tel, String wechat, String name, double dzb, double jifen, String idCard, String username, String password, String insertDate, String updateDate, Long sqOperId, int level, int status, Long ztId, Long zsId, Long teamId, int isDel, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.code = code;
        this.tel = tel;
        this.wechat = wechat;
        this.name = name;
        this.dzb = dzb;
        this.jifen = jifen;
        this.idCard = idCard;
        this.username = username;
        this.password = password;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
        this.sqOperId = sqOperId;
        this.level = level;
        this.status = status;
        this.ztId = ztId;
        this.zsId = zsId;
        this.teamId = teamId;
        this.isDel = isDel;
        this.enabled = enabled;
        this.authorities = authorities;
    }
}
