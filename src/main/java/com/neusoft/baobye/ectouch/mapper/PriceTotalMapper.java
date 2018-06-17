package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.PrizeTotal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceTotalMapper extends JpaRepository<PrizeTotal,Long> {

    Page<PrizeTotal> findByUserId(long userId, Pageable pageable);

    @Query(value="SELECT A.*,B.wechat,B.tel,B.username,B.name FROM price_total A ,wap_user B WHERE A.USER_ID = B.USER_ID AND A.USER_ID = ?1 AND A.STATUS = ?2 ",nativeQuery = true)
    List<Object[]> findByUserIdAndStatus(long userId, int status);
}
