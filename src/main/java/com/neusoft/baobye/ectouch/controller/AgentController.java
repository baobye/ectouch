package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.UserPriceView;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.PriceTotalMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/agent")
public class AgentController {
    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private PriceTotalMapper priceTotalMapper;
    /**
     * 我的代理首页
     * @return
     */
    @RequestMapping("/index")
    @Transactional
    public String index(){
        return "agent/index";
    }

    @RequestMapping("/indexAjax")
    @ResponseBody
    public List indexAjax(HttpServletRequest request,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user  = userMapper.findByUsername(name);
//        if(userId == null || userId == 0){
//            String name = SecurityContextHolder.getContext().getAuthentication().getName();
//            WapUser user  = userMapper.findByUsername(name);
//            userId = user.getUserId();
//            //发奖
//            model.addAttribute("award","award");
//        }
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<WapUser> list = userMapper.findByZsId(user.getUserId(),pageable);
        return list.getContent();
    }

    /**
     * 发奖列表
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/awardList/{userId}")
    @Transactional
    public String awardList(@PathVariable Long userId,Model model){

        List<Object[]> list = priceTotalMapper.findByUserIdAndStatus(userId,0);
        List<UserPriceView> listPrice = new ArrayList<UserPriceView>();
        for(Object[] object : list){
            UserPriceView userPriceView = new UserPriceView((BigInteger)object[0],(String)object[1],(String)object[2],(int)object[3],(double)object[4],(int)object[5],(double)object[6],(int)object[7],(String)object[8],(BigInteger)object[9],(String)object[10],(String)object[11],(String)object[12],(String)object[13]);
            listPrice.add(userPriceView);
        }
        model.addAttribute("list",listPrice);
        return "agent/awardList";
    }

    /**
     * 发奖
     * @param totalId
     * @return
     */
    @RequestMapping("/award/{totalId}/{userId}")
    public String award(@PathVariable("totalId") long totalId,@PathVariable("userId") long userId,@Value("${hhmg.server.award}") String url){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);

        Map<String, String> map = new HashMap<String, String>();
        map.put("TOTAL_ID", ""+totalId);//奖金id
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("响应结果：" + body);
        return "redirect:/agent/awardList/"+userId;
    }
}
