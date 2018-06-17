package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.MoneyChange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyChangeMapper extends JpaRepository<MoneyChange,Long> {

    Page<MoneyChange> findByUserId(long userId, Pageable pageable);


}
