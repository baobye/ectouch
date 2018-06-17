package com.neusoft.baobye.ectouch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EcsRegion {
    private static final long serialVersionUID = 1L;
    @Id
    private int regionId;

    private int parentId;

    private String regionName;

    private int regionType;

    private int agencyId;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getRegionType() {
        return regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
}
