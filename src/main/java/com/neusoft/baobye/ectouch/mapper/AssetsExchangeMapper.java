package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.AssetsExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetsExchangeMapper extends JpaRepository<AssetsExchange,Long> {

    List<AssetsExchange> findByAssetsId(Long assetsId);

}
