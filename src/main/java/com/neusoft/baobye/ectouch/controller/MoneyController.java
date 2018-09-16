package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.MoneyChange;
import com.neusoft.baobye.ectouch.entity.PrizeTotal;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.MoneyChangeMapper;
import com.neusoft.baobye.ectouch.mapper.PriceTotalMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/money")
public class MoneyController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(MoneyController.class);

    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private MoneyChangeMapper moneyChangeMapper;

    @Autowired
    private PriceTotalMapper priceTotalMapper;


    /**
     * 资金首页
     * 奖金记录
     * @return
     */
    @RequestMapping("/index")
    public String index(){

        return "money/index";
    }
    @RequestMapping("/indexAjax")
    @ResponseBody
    public List indexAjax(Model model,int page,int size){
        page = page - 1 ;//当前页从0 开始
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<PrizeTotal> pageObject = priceTotalMapper.findByUserId(getUserId(),pageable);

        int pages = pageObject.getTotalPages();// 总页数
        int number = pageObject.getNumber();//当前页

        return pageObject.getContent();
    }

    /**
     * 申请记录
     * @return
     */
    @RequestMapping("/record")
    @Transactional
    public String record(){
        return "money/record";
    }
    @RequestMapping("/recordAjax")
    @ResponseBody
    @Transactional
    public List recordAjax(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        //所有变动记录
        Page<MoneyChange> pageObject = moneyChangeMapper.findByUserId(getUserId(),pageable);
        return pageObject.getContent();
    }


    /**
     * 提现
     * @return
     */
    @RequestMapping("withdraw")
    public String withdraw(){
        return "money/withdraw";
    }


    /**
     * 提现提交服务器
     * @return
     */
    @RequestMapping("withdrawSubmit")
    public String withdrawSubmit(@Value("${hhmg.server.money}") String url,MoneyChange moneyChange){

        Map<String, String> map = new HashMap<String, String>();
        map.put("MONEY_TYPE", "1");//1电子币
        map.put("USER_ID", ""+getUserId());//账号
        map.put("REAL_MONEY", ""+moneyChange.getRealMoney());//变动金额
        map.put("REASON_TYPE", "2");//减少
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("响应结果：" + body);
        return "redirect:/";
    }

    /**
     * 充值
     * @return
     */
    @RequestMapping("recharge")
    public String recharge(){

        return "money/recharge";
    }

    /**
     * 充值提交服务器
     * @param url
     * @return
     */
    @RequestMapping("rechargeSubmit")
    public String rechargeSubmit(@Value("${hhmg.server.money}") String url,MoneyChange moneyChange ,Model model){
        Map<String, String> map = new HashMap<String, String>();
        map.put("MONEY_TYPE", "1");//1电子币
        map.put("USER_ID", ""+getUserId());//账号
        map.put("REAL_MONEY", ""+moneyChange.getRealMoney());//变动金额
        map.put("REASON_TYPE", "1");//增加
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("realMoney",moneyChange.getRealMoney());
        System.out.println("响应结果：" + body);
        return "money/rechargeDone";
    }


}
