package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.Storage;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.StorageMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    @ResponseBody
    public List enter(@PathVariable("type") Integer type ,@PathVariable("assetsType") Integer assetsType,Model model,HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"applyDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        Page<Storage> list = storageMapper.findByApplySysIdAndType(user.getUserId(),type,pageable);
        return list.getContent();
    }

    /**
     * 出库页面
     * @return
     */
    @RequestMapping("leave/type/{type}/assetsType/{assetsType}")
    @ResponseBody
    public List leave(@PathVariable("type") Integer type ,@PathVariable("assetsType") Integer assetsType,Model model,HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"applyDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
        Page<Storage> list = storageMapper.findByApplySysIdAndType(user.getUserId(),type,pageable);
        return list.getContent();
    }
}
