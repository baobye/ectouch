package com.neusoft.baobye.ectouch.service;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WapUserService {
    @Autowired
    private WapUserMapper mapper;

    public WapUser findByUsername(String username){
        return  mapper.findByUsername(username);
    }
}
