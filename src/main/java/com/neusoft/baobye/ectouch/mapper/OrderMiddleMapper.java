package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.OrderMiddle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMiddleMapper extends JpaRepository<OrderMiddle,Long> {

    @Query(value = "SELECT p.path,o.* FROM hhmg.order_middle o left join hhmg.tb_pictures p on o.good_id = p.parentid where p.type = 1 and o.order_id=  ?1 ",nativeQuery = true)
    List<OrderMiddle> findByOrderId(long orderId);
}
