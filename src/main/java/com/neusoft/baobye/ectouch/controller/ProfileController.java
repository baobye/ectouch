package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.EcsRegion;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.entity.WapUserAddress;
import com.neusoft.baobye.ectouch.exception.EcException;
import com.neusoft.baobye.ectouch.mapper.EcsRegionMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserAddressMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController extends BaseController{
    @Autowired
    private WapUserMapper userMapper;
    @Autowired
    private WapUserMapper mapper;
    @Autowired
    private EcsRegionMapper regionMapper;
    @Autowired
    private WapUserAddressMapper addressMapper;

    /**
     * 修改个人信息
     * @param model
     * @return
     */
    @RequestMapping("/profile/index")
    public String index(Model model){
        model.addAttribute("user",getUser());
        return "profile/index";
    }


    /**
     * 修改个人信息
     * @param wapUser
     * @return
     */
    @RequestMapping("/profile/updateUser")
    @ResponseBody
    public Map saveUser(@ModelAttribute WapUser wapUser){
        WapUser user = getUser();
        user.setWechat(wapUser.getWechat());
        user.setBankCode(wapUser.getBankCode());
        user.setBankName(wapUser.getBankName());
        Map map = new HashMap();
        WapUser save = userMapper.save(user);
        if(save != null){
            map.put("error","1");
        }else{
            map.put("error","0");
        }
//        return "redirect:/profile/index";
        return map;
    }

    /**
     * 修改密码
     * @param model
     * @return
     */
    @GetMapping("/profile/password")
    public String password(Model model){
        model.addAttribute("user",getUser());
        return "profile/editPassword";
    }

    /**
     * 修改密码提交
     * @param wapUser
     * @param model
     * @param new_password
     * @param old_password
     * @return
     */
    @PostMapping("/profile/password")
    public String password(@ModelAttribute WapUser wapUser,Model model,String new_password,String old_password){
        WapUser user = userMapper.findByUserId(wapUser.getUserId());
        //先判断原密码对不对
        String passwd = new SimpleHash("SHA-1", user.getUsername(), old_password).toString();	//密码加密
        if(!user.getPassword().equals(passwd)){
            throw new EcException("修改密码","旧密码错误","/profile/password");
        }
        //还要给新密码加密  用户名 + 密码
        user.setPassword(new SimpleHash("SHA-1", user.getUsername(), new_password).toString());
        userMapper.save(user);
        return "redirect:/";
    }

    /**
     * 地址列表
     * @param model
     * @return
     */
    @GetMapping("/profile/addressList/{type}")
    public String addressList(@PathVariable String type, HttpServletRequest request,Model model){

        List<WapUserAddress> list = addressMapper.findByUserId(getUserId());
        List<WapUserAddress>  l = new ArrayList();
        for (WapUserAddress address : list){
            try {
                int id1 = address.getProvinceId();
                int id2 = address.getCityId();
                int id3 = address.getAreaId();
                String add =
                        regionMapper.findByRegionId(6) == null ? "":regionMapper.findByRegionId(6).getRegionName()+
                        regionMapper.findByRegionId(id2) == null ? "" :regionMapper.findByRegionId(id2).getRegionName()+
                        regionMapper.findByRegionId(id3) == null ? "" :regionMapper.findByRegionId(id3).getRegionName() + address.getAddress();
                address.setAddress(add);
                l.add(address);
            }catch (Exception e){
                e.printStackTrace();;
            }
        }
        model.addAttribute("list",l);
        model.addAttribute("type",type);
        return "profile/addressList";
    }

    /**
     * 修改地址
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/profile/update{id}/{type}")
    public String updateAdd(@PathVariable("id") String id,@PathVariable String type,Model model){

        WapUserAddress address = addressMapper.findByAddId(Long.parseLong(id));
        //还需要获得 城市 地区的 list
        List<EcsRegion> cityList = regionMapper.findByParentId(address.getProvinceId());
        List<EcsRegion> areaList = regionMapper.findByParentId(address.getCityId());
        model.addAttribute("address",address);
        model.addAttribute("cityList",cityList);
        model.addAttribute("areaList",areaList);
        model.addAttribute("type",type);
        return "profile/updateAddress";
    }

    /**
     * 打开新增地址界面
     * type=1 购物车过来的
     * @return
     */
    @GetMapping("/profile/addAddress/{type}")
    public String addGetAddress(@PathVariable String type,Model model){
        model.addAttribute("type",type);
        if("0".equals(type)) {
            model.addAttribute("button","新增收货地址");
        }else if("1".equals(type)){
            model.addAttribute("button","配送到这里");
        }
        return "profile/addAddress";
    }

    /**
     * 新增和修改地址
     * @param address
     * @return
     */
    @PostMapping("/profile/addAddress/{type}")
    public String addPostAddress(@PathVariable String type ,WapUserAddress address){
        //新增
        if(address.getAddId() == null){
            address.setAddId(System.currentTimeMillis());
        }
        address.setUserId(getUserId());

        List<WapUserAddress> list = addressMapper.findByUserIdAndDefaultAdd(getUserId(),1);
        for(WapUserAddress add : list){
            add.setDefaultAdd(0);
            addressMapper.save(add);
        }
        addressMapper.save(address);
        //购物车过来的
        if("0".equals(type)){
            return "redirect:/profile/addressList/0";
        } else if("1".equals(type)){
            return "redirect:/goods/checkout/"+getUser().getLevel();
        }

        return "redirect:/profile/addressList/0";
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @Transactional
    @GetMapping("/profile/delete{id}")
    public String deleteAdd(@PathVariable String id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        addressMapper.deleteWapUserAddressByAddId(Long.parseLong(id));
        return "redirect:/profile/addressList/0";
    }


    //显示地址列表

    @GetMapping("/profile/getCity")
    @ResponseBody
    public List<EcsRegion> getCity(HttpServletRequest request){
        String parentId = request.getParameter("parent");
        List<EcsRegion> list = regionMapper.findByParentId(Integer.parseInt(parentId));
        return list;
    }

}
