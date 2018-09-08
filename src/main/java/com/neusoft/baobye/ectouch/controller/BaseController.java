package com.neusoft.baobye.ectouch.controller;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
