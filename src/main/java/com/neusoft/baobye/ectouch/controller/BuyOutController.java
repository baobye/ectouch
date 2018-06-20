package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
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
        List<WapUser> listWap = new ArrayList<WapUser>();
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");

            try {
                Map rmap = objectMapper.readValue(body,Map.class);
                List<Map> userList =  (List<Map>)rmap.get("userList");
//                List<Map> beanList = objectMapper.readValue(userList, new TypeReference<List<Map>>() {});
//                System.out.println(beanList.size());
                if(userList != null){
                    for(Map m : userList){
                        WapUser wapUser = new WapUser();
                        wapUser.setTel((String)m.get("TEL"));
                        wapUser.setWechat((String)m.get("WECHAT"));
                        wapUser.setUserId((Long)m.get("USER_ID"));
                        wapUser.setLevel((Integer)m.get("LEVEL"));
                        wapUser.setName((String)m.get("NAME"));
                        wapUser.setCode((String)m.get("CODE"));
                        wapUser.setUsername((String)m.get("USERNAME"));
                        wapUser.setZtId((Long)m.get("ZT_ID"));
                        wapUser.setZsId((Long)m.get("ZS_ID"));
                        wapUser.setIdCard((String)m.get("ID_CARD"));
                        listWap.add(wapUser);
                    }
                }
                System.out.println(111);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("list",listWap);
        System.out.println("响应结果：" + body);
        return "buyout/index";
    }


    /**
     * 买断
     * @param userId
     * @param url
     * @param model
     * @return
     */
    @RequestMapping("/buyOut/{userId}")
    public String buyOut(@PathVariable Long userId, @Value("${hhmg.server.buyOut}") String url , Model model){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        Map<String, String> map = new HashMap<String, String>();
        map.put("OUT_USER_ID", ""+userId);//被买断人
        map.put("MD_USER_ID", ""+user.getUserId());//买断人
        String body = null;
        List<WapUser> listWap = new ArrayList<WapUser>();
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("list",listWap);
        System.out.println("响应结果：" + body);
        return "redirect:/buyOut/index";
    }
}
