package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderInfoMapper extends JpaRepository<OrderInfo, Long> {

    /**
     * 待收货列表
     *
     * @param userId
     * @param status
     * @param pageable
     * @return
     */
    Page<OrderInfo> findByUserIdAndStatus(long userId, int status, Pageable pageable);

    OrderInfo findByOrderId(long orderId);

    /**
     * 待发货
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM ORDER_INFO WHERE USER_ID IN (SELECT USER_ID FROM WAP_USER WHERE ZS_ID =  ?1) AND STATUS = 1", nativeQuery = true)
    Page<OrderInfo> nativeQuery(long userId, Pageable pageable);
    
    @Query(value = "SELECT * FROM ORDER_INFO WHERE USER_ID =  ?1", nativeQuery = true)
    Page<OrderInfo> nativeAllOrderQuery(long userId, Pageable pageable);
}
