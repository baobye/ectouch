package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private WapUserMapper userMapper;

    @RequestMapping("/index")
    public String index(){

        return "storage/index";
    }

    /**
     * 发货
     * @return
     */
    @RequestMapping("/delivery")
    public String delivery(){

        return "storage/delivery";
    }
}
