package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.EcsRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcsRegionMapper extends JpaRepository<EcsRegion,Integer> {
    List<EcsRegion> findByParentId(int parentId);
    EcsRegion findByRegionId(int regionId);
}
