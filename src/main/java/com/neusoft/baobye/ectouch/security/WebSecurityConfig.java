package com.neusoft.baobye.ectouch.security;

import com.neusoft.baobye.ectouch.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    // 数据源是为了JdbcRememberMeImpl实例而注入的，如果不设置数据源会在登陆的时候抛空指针异常
    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        login-page指定的用户自定义的登录页面
//        default-target-url登录成功以后默认跳转到的页面
//        authentication-failure-url登录失败以后跳转到的页面
//        username-parameter指定登录表单中用户名的input中name，如果这里不配置，则默认为username
//        password-parameter指定登录表单中密码的input中name，如果这里不配置，则默认为password
//        logout-success-url成功退出以后跳转到的地址
        http.authorizeRequests()
                // 如果有匿名的url，填在下面
                .antMatchers("/register/**","/defaultKaptcha/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登陆页
                .formLogin().loginPage("/login").permitAll()
                // 设置登陆成功页
                .defaultSuccessUrl("/",true)
                .authenticationDetailsSource(authenticationDetailsSource)
                // 设置登陆失败页
                .failureUrl("/login/error")
                .permitAll()
                // 指定authenticationDetailsSource
                .and()
                .logout().logoutUrl("/logout").deleteCookies("remember-me").clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/login?logout=true").permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me").userDetailsService(userDetailsService)
                .tokenValiditySeconds(604800)
                .rememberMeServices(rememberMeServices())
                .key("INTERNAL_SECRET_KEY").and().csrf().disable();
        // 当通过JDBC方式记住密码时必须设置 key，key 可以为任意非空(null 或 "")字符串，但必须和 RememberMeService 构造参数的
        // key 一致，否则会导致通过记住密码登录失败

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**","/assets/**","/themes/**");
    }

    /**
     * 返回 RememberMeServices 实例
     *
     * @return the remember me services
     */
    @Bean
    public RememberMeServices rememberMeServices() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        // 此处需要设置数据源，否则无法从数据库查询验证信息
        rememberMeTokenRepository.setDataSource(dataSource);

        // 此处的 key 可以为任意非空值(null 或 "")，单必须和起前面
        // rememberMeServices(RememberMeServices rememberMeServices).key(key)的值相同
//        PersistentTokenBasedRememberMeServices rememberMeServices =
//                new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", userDetailsService, rememberMeTokenRepository);
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("INTERNAL_SECRET_KEY", userDetailsService);
        rememberMeServices.setAlwaysRemember(true);
        // 该参数不是必须的，默认值为 "remember-me", 但如果设置必须和页面复选框的 name 一致
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }
}