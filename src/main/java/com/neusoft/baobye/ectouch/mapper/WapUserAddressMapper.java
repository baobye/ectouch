package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.WapUserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WapUserAddressMapper extends JpaRepository<WapUserAddress,Long> {
    List<WapUserAddress> findByUserId(long userId);

    List<WapUserAddress> findByUserIdAndDefaultAdd(long userId ,int defaultAdd);
    WapUserAddress findByAddId(long addId);
    @Modifying
    public void deleteWapUserAddressByAddId(long addId);
}
