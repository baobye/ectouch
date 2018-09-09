package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.EchatEntity;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/echarts")
@Controller
public class EchartsController extends BaseController{

    @RequestMapping("/all")
    @ResponseBody
    public EchatEntity getAll(@Value("${hhmg.server}") String url){
        Map<String, String> map = new HashMap<String, String>();
        map.put("USER_ID", ""+getUserId());//账号
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url+"/wapImpl/getEcharts", map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EchatEntity entity = new EchatEntity();
        entity.setName(Arrays.asList("星期一","星期二"));
        entity.setValue(Arrays.asList("22","333"));
        entity.setTitle("接口测试一");
        entity.setLegend(Arrays.asList("注册人数"));
        return entity;
    }
}
