package com.JW.security.service;

import com.JW.security.config.Admin;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 总目标：根据表单提交的用户名查询 User 对象，并装配角色、权限等信息
    @Override
    public UserDetails loadUserByUsername(

            // 表单提交的用户名
            String username) throws UsernameNotFoundException {

        // 1.从数据库查询 Admin 对象
        String sql = "SELECT id,login_acct,password,user_name,email FROM t_admin WHERE login_acct=?";
        List<Admin> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Admin.class), username);

        Admin admin = list.get(0);
        System.out.println(admin.toString());

        ModelMap modelMap = new ModelMap();

        modelMap.addAttribute("username",username);
        System.out.println("username"+username);
        String username1 = (String) modelMap.get("username");
        System.out.println("user1"+username1);

        // 2.给 Admin 设置角色权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.add(new SimpleGrantedAuthority("ROLE_学徒"));
        authorities.add(new SimpleGrantedAuthority("UPDATE"));


        // 3.把 admin 对象和 authorities 封装到 UserDetails 中
        String userPswd = admin.getPassword();
        return new User(username, userPswd, authorities);
    }

}



