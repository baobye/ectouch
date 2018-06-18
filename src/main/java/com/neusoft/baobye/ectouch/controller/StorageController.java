package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.Storage;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.StorageMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private StorageMapper storageMapper;

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

    /**
     * 入库页面
     * @return
     */
    @RequestMapping("enter/type/{type}/assetsType/{assetsType}")
    public String enter(@PathVariable("type") Integer type ,@PathVariable("assetsType") Integer assetsType,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        List<Storage> list = storageMapper.findByApplySysIdAndTypeAndAssetsType(user.getUserId(),type,assetsType);
        model.addAttribute("list",list);
        return "storage/enterStorage";
    }

    /**
     * 出库页面
     * @return
     */
    @RequestMapping("leave/type/{type}/assetsType/{assetsType}")
    public String leave(@PathVariable("type") Integer type ,@PathVariable("assetsType") Integer assetsType,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        List<Storage> list = storageMapper.findByApplySysIdAndTypeAndAssetsType(user.getUserId(),type,assetsType);
        model.addAttribute("list",list);
        return "storage/leaveStorage";
    }
}
