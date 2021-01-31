package com.JW.security.config;

import com.JW.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {

        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);

        //super.configure(security); 注释掉将取消父类方法中的默认规则
        security.authorizeRequests()//对请求进行授权
                .antMatchers("/layui/**", "/index.jsp")

                //使用 ANT 风格设置要授权的 URL 地址
                .permitAll() //允许上面使用 ANT 风格设置的全部请求
                .antMatchers("/level1/**")
                .hasRole("学徒")  //指定访问的角色
                .antMatchers("/level2/**")
                .hasAuthority("内门弟子") //指定访问的权限
                .anyRequest() //其他未设置的全部请求
                .authenticated() //需要认证
                .and()
                .formLogin() //设置未授权请求跳转到登录页面：开启表单登 录功能
                .loginPage("/index.jsp") //指定登录页

                .permitAll() //为登录页设置所有人都可以访问
                .loginProcessingUrl("/do/login.html") // 指定提交登录表单的地址
                .usernameParameter("loginAcct") // 定制登录账号的请求参数名
                .passwordParameter("userPswd") // 定制登录密码的请求参数名
                .defaultSuccessUrl("/main.html")


                .and()
                .logout()
                .logoutUrl("/my/app/logout")
                .logoutSuccessUrl("/index.jsp")
                .and()
                .rememberMe()
                .tokenRepository(repository)
                .and()
                .exceptionHandling()
                // .accessDeniedPage("/to/no/auth/page.html") 第一种跳转页面处理异常请求方法，无参数传递

                .accessDeniedHandler(new AccessDeniedHandler() {  //第二种，有参数传递，处理异常请求方法，跳转指定页面
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("message", "不好意思，没有权限");
                        httpServletRequest.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(httpServletRequest, httpServletResponse);
                    }
                })

        ; //设置登录成功后默认前往的 URL 地址
    }

//
//    @Override  //旧的的方法，基于内存的验证方式
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        //super.configure(auth); 一定要禁用默认规则
//        builder.inMemoryAuthentication()
//                .withUser("tom")
//                .password("123123") //设置账号密码
//                .roles("学徒") //设置角色
//
//
//                .and()
//                .withUser("jerry")
//                .password("123123")//设置另一个账号密码
//                .authorities("UPDATE","内门弟子")//设置权限
//        ; //设置权限
//    }

    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder.
                userDetailsService(myUserDetailsService);
                //        .passwordEncoder(myPasswordEncoder)//JDK自带的MD5加密
                //.passwordEncoder(getBCryptPasswordEncoder());


    }
}



