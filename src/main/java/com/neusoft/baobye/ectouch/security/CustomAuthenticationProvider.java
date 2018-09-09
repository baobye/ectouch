package com.neusoft.baobye.ectouch.security;

import com.neusoft.baobye.ectouch.entity.WapUser;
import com.neusoft.baobye.ectouch.mapper.WapUserMapper;
import com.neusoft.baobye.ectouch.service.CustomUserDetailsService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private WapUserMapper userMapper;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        UserDetails user = customUserDetailsService.loadUserByUsername(inputName);

        // 对密码进行验证，如有需要可以进行自定义加密解密的判断
        String passwd = new SimpleHash("SHA-1", inputName, inputPassword).toString();	//密码加密
        if (!passwd.equals(user.getPassword())) {
//            throw new BadCredentialsException("密码错误，登录失败！");
            throw new DisabledException("密码错误，登录失败！");
        }


        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String verifyCode = details.getVerifyCode();
        if(!validateVerify(verifyCode)) {
//            throw new DisabledException("验证码输入错误");
            throw new DisabledException("验证码输入错误");
        }

        // 添加权限
//        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
          List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (SysUserRole userRole : userRoles) {
//            SysRole role = roleService.selectById(userRole.getRoleId());
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        grantedAuthorities.add(new SimpleGrantedAuthority("普通用户"));
        return new UsernamePasswordAuthenticationToken(user, inputPassword, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean validateVerify(String inputVerify) {
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 不分区大小写
        // 这个validateCode是在servlet中存入session的名字
        String validateCode = (String) request.getSession().getAttribute("vrifyCode");
        System.out.println("Session  vrifyCode "+validateCode+" form vrifyCode "+inputVerify);
        System.out.println("验证码：" + validateCode + "用户输入：" + inputVerify);

        return validateCode.equalsIgnoreCase(inputVerify);
    }
}
