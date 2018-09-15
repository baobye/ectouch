package com.neusoft.baobye.ectouch.controller;

import com.neusoft.baobye.ectouch.entity.OrderInfo;
import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.OrderInfoMapper;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private WapUserMapper userMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @RequestMapping("/homepage")
    public String homepage(){
        return "index";
    }
    @RequestMapping("/")
    public String showHome(Model model) {
        /**
         * 已发货
         */
        Sort sort = new Sort(Sort.Direction.DESC,"insertDate");
        PageRequest pageable = PageRequest.of(0,5,sort);
        // 状态2  已发货状态
        Page<OrderInfo> pageObject = orderInfoMapper.findByUserIdAndStatus(getUserId(),2,pageable);
        model.addAttribute("totalCount",pageObject.getTotalElements());


        /**
         * 发货
         */
        PageRequest pageable1 = PageRequest.of(0,5,new Sort(Sort.Direction.DESC,"insert_Date"));
        Page pageObject1 = orderInfoMapper.nativeQuery(getUserId(),pageable1);
        model.addAttribute("lowerOrderCount",pageObject1.getTotalElements());
        model.addAttribute("wapUser",getUser());

        return "home";
    }

    @RequestMapping("/login")
    public String showLogin(Model model,WapUser user
              ,@RequestParam(value = "error",required = false) boolean error
              ,@RequestParam(value = "logout",required = false) boolean logout,HttpServletRequest request,HttpServletResponse response) {
         model.addAttribute(user);
        //如果已经登陆跳转到个人首页
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if(error == true) {
             model.addAttribute("error", error);
             return "login";
         }
         if(logout == true){
             return "login";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
         }
        if(authentication!=null&&
                !authentication.getPrincipal().equals("anonymousUser")&&
                authentication.isAuthenticated())
            return "redirect:/";
         return "login";
    }

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }


    @RequestMapping("/login/error")
    public String loginError(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            response.setContentType("text/html;charset=utf-8");
            AuthenticationException exception =
                    (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if(exception != null){
                model.addAttribute("message",exception.getMessage());
                model.addAttribute("buttonValue","登录");
                model.addAttribute("contentUrl","/login");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * 登出功能
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response){
        //如果已经登陆跳转到个人首页
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            new SecurityContextLogoutHandler().isInvalidateHttpSession();
            request.getSession().invalidate();
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
