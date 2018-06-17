package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    @Autowired
    private WapUserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @RequestMapping("/register/{userId}")
    public String register(@PathVariable long userId, Model model){
        if(userId >= 0 ){
            WapUser user  = userMapper.findByUserId(userId);
            model.addAttribute("user",user);
        }
        return "register";
    }

    /**
     * 注册用户
     * @param wapUser
     * @return
     */
    @RequestMapping("/register/saveUser")
    @ResponseBody
    public Map saveUser(@ModelAttribute WapUser wapUser, Model model){
        wapUser.setUserId(System.currentTimeMillis());
        wapUser.setUsername(wapUser.getTel());
        wapUser.setCode("HHML"+System.currentTimeMillis());
        String passwd = new SimpleHash("SHA-1", wapUser.getUsername(), wapUser.getPassword()).toString();	//密码加密
        wapUser.setPassword(passwd);
        //判断直推人是否存在
        WapUser zt = null;
        Map map = new HashMap();
        try {
            zt = userMapper.findByTel(""+wapUser.getZtId());
            if(zt == null){
                model.addAttribute("message","推荐人不存在");
                model.addAttribute("buttonValue","重新注册");
                model.addAttribute("contentUrl","/register");
                map.put("error","0");
                map.put("message","推荐人不存在");
                return map;
            }
        } catch (Exception e) {
            model.addAttribute("message","推荐人不存在");
            model.addAttribute("buttonValue","重新注册");
            model.addAttribute("contentUrl","/register");
            map.put("error","0");
            map.put("message","推荐人不存在");
            return map;
        }

        try {
            WapUser wapUser1 = userMapper.findByTel(wapUser.getTel());
            if(wapUser1 != null){
                model.addAttribute("message","此手机号已经注册过会员");
                model.addAttribute("buttonValue","重新注册");
                if(zt != null ){
                    model.addAttribute("contentUrl","/register/"+zt.getUserId() );
                }else{
                    model.addAttribute("contentUrl","/register");
                }
                map.put("error","0");
                map.put("message","此手机号已经注册过会员");
                return map;
            }
        } catch (Exception e) {
            model.addAttribute("message","此手机号已经注册过会员");
            model.addAttribute("buttonValue","重新注册");
            if(wapUser.getZtId() > 0){
                model.addAttribute("contentUrl","/register/"+wapUser.getZsId() );
            }else{
                model.addAttribute("contentUrl","/register");
            }
            map.put("error","0");
            map.put("message","此手机号已经注册过会员");
            return map;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS");
        wapUser.setInsertDate(dateFormat.format(new Date()));
        wapUser.setStatus(1);//注册为待激活状态。
        wapUser.setLevel(6);//默认花粉
        wapUser.setZtId(zt.getUserId());//设置直推id
        WapUser save = userMapper.save(wapUser);
        if(save != null){
            map.put("error","1");
            map.put("message","注册成功等待上级激活");
            return map;
        }
        map.put("error","0");
        map.put("message","注册失败请重试");
        return map;
    }
    @RequestMapping("/register/activate/{userId}")
    @Transactional
    public String activate(Model model,@PathVariable long userId){
        WapUser  user = userMapper.findByUserId(userId);
        user.setStatus(2);
        userMapper.save(user);
        return "redirect:/share/index";
    }

}
