package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.Assets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetsMapper extends JpaRepository<Assets,Long> {

    Page<Assets> findByUserId(Long userId, Pageable pageable);
}
