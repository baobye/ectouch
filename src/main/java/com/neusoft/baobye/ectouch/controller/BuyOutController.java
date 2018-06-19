package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.GoodCart;
import com.neusoft.baobye.ectouch.entity.MoneyChange;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HighPreciseComputor;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/buyOut")
public class BuyOutController {
    @Autowired
    private WapUserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 买断用户列表
     * @param url
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(@Value("${hhmg.server.buyOutList}") String url , Model model){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        Map<String, String> map = new HashMap<String, String>();
        map.put("USER_ID", ""+user.getUserId());//账号
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
            try {
                map = objectMapper.readValue(body,Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("list","");
        System.out.println("响应结果：" + body);
        return "buyout/index";
    }
}
