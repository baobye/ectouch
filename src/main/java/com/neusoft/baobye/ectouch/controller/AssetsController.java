package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.Assets;
import com.neusoft.baobye.ectouch.entity.GoodInfo;
import com.neusoft.baobye.ectouch.entity.MoneyChange;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.exception.EcException;
import com.neusoft.baobye.ectouch.mapper.AssetsMapper;
import com.neusoft.baobye.ectouch.mapper.GoodInfoMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/yunAssets")
public class AssetsController extends BaseController{
    @Autowired
    private WapUserMapper userMapper;

    @Autowired
    private AssetsMapper mapper;

    @Autowired
    private GoodInfoMapper goodInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

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
        Page pageList = mapper.findByUserId(getUserId(),pageable);

        List<Assets> list = pageList.getContent();
        List<GoodInfo> goodInfos =goodInfoMapper.findAll();
        Map<Long,GoodInfo> map = goodInfos.stream().collect(Collectors.toMap(GoodInfo::getGoodId, a -> a,(k1, k2)->k1));
        for(Assets assets:list){
            assets.setGoodName(map.get(assets.getGoodId()).getGoodName());
        }
        return list;
    }


    /**
     * 充值提交服务器
     * @param url
     * @return
     */
    @GetMapping("out")
    @ResponseBody
    public Object rechargeSubmit(@Value("${hhmg.server}") String url, @Param("id") String id , @Param("sum") String sum , Model model){
        Map<String, String> map = new HashMap<String, String>();
        map.put("ASSETS_ID", id);//库房id
        map.put("NUM", sum);//数量
        String body = null;
        try {
            body = HttpClientUtil.sendPostDataByMap(url+"/wapImpl/assetsOut", map, "utf-8");
            Map<String,String> result  = objectMapper.readValue(body,Map.class);
            if("error".equals(result.get("result"))){
                return result.get("msg");
            }else{
                return "出库成功";
            }
        } catch (IOException e) {
            return "出库失败";
        }
    }
}
