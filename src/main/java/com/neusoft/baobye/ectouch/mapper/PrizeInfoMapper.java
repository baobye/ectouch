package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.PrizeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrizeInfoMapper extends JpaRepository<PrizeInfo,Long> {
    Page<PrizeInfo> findByUserId(long userId, Pageable pageable);
}
