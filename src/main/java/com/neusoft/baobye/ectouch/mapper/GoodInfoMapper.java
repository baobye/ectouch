package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.GoodInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodInfoMapper extends JpaRepository<GoodInfo,Long> {
    GoodInfo findByGoodId(Long goodId);
    Page<GoodInfo> findAll(Pageable pageable);
}
