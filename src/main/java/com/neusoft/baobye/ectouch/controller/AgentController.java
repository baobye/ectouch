package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.UserPriceView;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.PriceTotalMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
public class AgentController extends BaseController{
    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private PriceTotalMapper priceTotalMapper;
    /**
     * 我的代理首页
     * @return
     */
    @RequestMapping("/index/{userId}")
    @Transactional
    public String index(@PathVariable  Long userId,Model model){
        model.addAttribute("userId",getUserId());
        if(getUserId() != userId && userId != null && userId != 0){
            model.addAttribute("userId",userId);
        }
        return "agent/index";
    }

    /**
     * 利用传过来的userId加载数据
     * @param userId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/indexAjax/{userId}")
    @ResponseBody
    public List indexAjax(@PathVariable Long userId,HttpServletRequest request,Model model){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<WapUser> list = userMapper.findByZsId(userId,pageable);
        return list.getContent();
    }
    @GetMapping(value = {"/awardListIndex"})
    @Transactional
    public String getAwardList(@PathVariable(required = false) Long userId,Model model){
        model.addAttribute("userId",getUserId());
        return "agent/awardList";
    }

    /**
     * 发奖列表
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/awardList/{userId}")
    @ResponseBody
    @Transactional
    public List<UserPriceView> awardList(@PathVariable Long userId,Model model,HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insert_date");
        PageRequest pageable = PageRequest.of(page,size,sort);
        List<Object[]> list = priceTotalMapper.findByUserIdAndStatus(userId,1,pageable);
        List<UserPriceView> listPrice = new ArrayList<UserPriceView>();
        for(Object[] object : list){
            UserPriceView userPriceView = new UserPriceView((BigInteger)object[0],(Double) object[1],(String)object[2],(String)object[3],(String)object[4],(String)object[5],(String)object[6],(BigInteger)object[6]);
            listPrice.add(userPriceView);
        }
        return listPrice;
    }

    /**
     * 发奖
     * @param totalId
     * @return
     */
    @RequestMapping("/award/{totalId}/{userId}")
    public String award(@PathVariable("totalId") long totalId,@PathVariable("userId") long userId,@Value("${hhmg.server.award}") String url){
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
