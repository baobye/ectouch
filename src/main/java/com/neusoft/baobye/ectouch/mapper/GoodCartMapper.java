package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.GoodCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodCartMapper extends JpaRepository<GoodCart,Long> {

    List<GoodCart> findAllByUserId(long userId);
    void deleteByRecId(Long userId);
    GoodCart findByRecId(Long recId);
    GoodCart findByUserIdAndGoodsId(long userId,long goodsId);
    void deleteByUserId(long userId);
}
