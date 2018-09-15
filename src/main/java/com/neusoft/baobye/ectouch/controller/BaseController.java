package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseController {
    @Autowired
    private WapUserMapper userMapper;
    public String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WapUser user = (WapUser) authentication.getPrincipal();
        return user.getUsername();
    }

    public Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WapUser user = (WapUser) authentication.getPrincipal();
        return user.getUserId();
    }
}
