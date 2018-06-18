package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Assets {

    @Id
    private Long assetsId;

    /**
     * 0物理仓库 1云仓库
     */
    private int type;

    private Long userId;

    private Long goodId;

    /**
     * 商品数量
     */
    private int goodNum;

    /**
     * 冻结数量
     */
    private int goodNumDj;

    /**
     * 插入日期
     */
    private String insertDate;

    /**
     * 修改日期
     */
    private String updateDate;

    /**
     * 生产日期
     */
    private String startDate;

    /**
     * 过期日期
     */
    private String endDate;

    /**
     * 报警日期
     */
    private String alarmDate;

    /**
     * 仓库具体信息对应的表ID  一般对应区域
     */
    private Long roomId;

    /**
     * 对应的出入库申请记录ID
     */
    private Long storageId;

    @OneToMany(mappedBy = "goodId")
    private Set<GoodInfo> goodInfoSet = new HashSet<GoodInfo>();


    public Long getAssetsId() {
        return assetsId;
    }

    public void setAssetsId(Long assetsId) {
        this.assetsId = assetsId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getGoodNumDj() {
        return goodNumDj;
    }

    public void setGoodNumDj(int goodNumDj) {
        this.goodNumDj = goodNumDj;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Set<GoodInfo> getGoodInfoSet() {
        return goodInfoSet;
    }

    public void setGoodInfoSet(Set<GoodInfo> goodInfoSet) {
        this.goodInfoSet = goodInfoSet;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }
}
