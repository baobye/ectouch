package com.neusoft.baobye.ectouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.util.HttpClientUtil;
import com.neusoft.baobye.ectouch.util.PicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/share")
@Controller
public class ShareController {

    @Autowired
    private WapUserMapper userMapper;

    /**
     * 我的分享
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping("/index")
    public String share(){
        return "share/index";
    }

    @RequestMapping("/indexAjax")
    @ResponseBody
    public List<WapUser> shareAjax(HttpServletRequest request){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(page,size,sort);
        Page<WapUser> pageList = userMapper.findByZtIdAndStatus(user.getUserId(),1,pageable);
        return pageList.getContent();
    }

    /**
     * 生成分享二维码
     * @param url
     * @param server
     * @param model
     * @return
     */
    @GetMapping("/barcode")
    public String barcode(@Value("${hhmg.server.creatQRCode}") String url, @Value("${hhmg.server}") String server, Model model){
        String body = null;
        Map<String, String> map = new HashMap<String, String>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        try {
            map.put("USER_ID", ""+user.getUserId());//账号
            body = HttpClientUtil.sendPostDataByMap(url, map, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            map = objectMapper.readValue(body,Map.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        String imgurl = server+"/"+map.get("url");
        model.addAttribute("imgurl",imgurl);
        System.out.println("响应结果：" + imgurl);
        return "share/barcode";
    }

    /**
     * 授权证书
     * letter of authorization
     * @return
     */
    @RequestMapping("/loa")
    public String loa(@Value("${hhmg.server}") String url,@Value("${hhmg.server.tempPath}") String tempPath, @Value("${hhmg.server.LOAImgPath}") String LOAImgPath,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        WapUser user = userMapper.findByUsername(name);
        String imgName = PicUtil.makeLOA(tempPath,LOAImgPath,user);
        model.addAttribute("imgUrl",url+"/"+"uploadFiles/twoDimensionCode/"+imgName);
        return "share/loa";
    }
}
