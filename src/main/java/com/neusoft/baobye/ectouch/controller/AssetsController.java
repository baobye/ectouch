package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.AssetsMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/assets")
public class AssetsController {
    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private AssetsMapper mapper;

    /**
     * 库房明细
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        WapUser user  = userMapper.findByUsername(name);
        List list = mapper.findByUserId(new Long("1529202711126"));

        model.addAttribute("list",list);
        return "assets/index";
    }
}
