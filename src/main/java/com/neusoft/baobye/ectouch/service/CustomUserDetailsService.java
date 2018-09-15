package com.neusoft.baobye.ectouch.service;

import com.neusoft.baobye.ectouch.entity.WapUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private WapUserService service;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        WapUser user = service.findByUsername(s);

        // 判断用户是否存在
        if(user == null) {
//            throw new UsernameNotFoundException("用户名不存在");
            throw new DisabledException("用户名不存在");
        }
        if(user.getStatus() == 1){
            throw new DisabledException("上级未审批不能登录");
        }

        // 返回UserDetails实现类
//        return new User(user.getUsername(), user.getPassword(), authorities);
//        return new WapUser(user.getUserId(),user.getUsername(),user.getPassword(),true,authorities);
        return user;
    }


}
