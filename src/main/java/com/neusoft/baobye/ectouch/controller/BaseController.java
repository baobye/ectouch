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
        UserDetails wapUser = (UserDetails) authentication.getPrincipal();
        return wapUser.getUsername();
    }

    public Long getUserId(){
        WapUser user  = userMapper.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return user.getUserId();
    }
}
