package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.OrderMiddle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMiddleMapper extends JpaRepository<OrderMiddle,Long> {

    List<OrderMiddle> findByOrderId(long orderId);
}
