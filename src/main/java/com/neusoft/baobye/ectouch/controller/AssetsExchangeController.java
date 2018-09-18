package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.mapper.AssetsExchangeMapper;
import com.neusoft.baobye.ectouch.mapper.AssetsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/AssetsExchange")
public class AssetsExchangeController {

    @Autowired
    private AssetsExchangeMapper mapper;

    @RequestMapping("/record/{assetsId}")
    public String indexAjax(@PathVariable Long assetsId, HttpServletRequest request,Model model) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC, "insertDate");
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page list = mapper.findByAssetsId(assetsId, pageable);
        model.addAttribute("assets",list);
        return "assets/assetsDetail";
    }
}
