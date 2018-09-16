package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.AssetsMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/yunAssets")
public class AssetsController extends BaseController{
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
        return "assets/index";
    }

    /**
     * 库房明细
     * @param request
     * @return
     */
    @RequestMapping("/indexAjax")
    @ResponseBody
    public List indexAjax(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page list = mapper.findByUserId(getUserId(),pageable);
        return list.getContent();
    }
}
