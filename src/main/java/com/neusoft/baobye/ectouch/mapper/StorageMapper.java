package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageMapper extends JpaRepository<Storage,Long> {

    /**
     *
     * @param applySysId 申请人
     * @param type   1  入库申请  2 出库申请
     * @param assetsType  0实体库房 1云库房
     * @return
     */
    List<Storage> findByApplySysIdAndTypeAndAssetsType(Long applySysId,int type,int assetsType );
}
