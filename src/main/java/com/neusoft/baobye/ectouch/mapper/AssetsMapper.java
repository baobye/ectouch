package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.Assets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetsMapper extends JpaRepository<Assets,Long> {

    List<Assets> findByUserId(Long userId);
}
