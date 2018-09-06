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

    private Long userId;

    private Long goodId;

    private int goodYunNum;

    /**
     * 商品数量
     */
    private int goodStNum;

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

    public int getGoodYunNum() {
        return goodYunNum;
    }

    public void setGoodYunNum(int goodYunNum) {
        this.goodYunNum = goodYunNum;
    }

    public int getGoodStNum() {
        return goodStNum;
    }

    public void setGoodStNum(int goodStNum) {
        this.goodStNum = goodStNum;
    }
}
