package com.neusoft.baobye.ectouch.mapper;

import com.neusoft.baobye.ectouch.entity.WapUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WapUserMapper extends JpaRepository<WapUser,Long> {

     WapUser findByUsername(String username);
     WapUser findByUserId(long userId);
     WapUser findByZtIdOrTel(long ztId,String tel);
     WapUser findByTel(String tel);


     Page<WapUser> findByZsId(long zsId, Pageable pageable);

     //待激活的会员
     List<WapUser> findByZtIdAndStatus(long ztId, int status);

}
