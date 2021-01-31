package com.JW.security.config;

public class Admin {
    private Integer id;

    private String loginAcct;


    private String password;

    private String email;

    private String createTime;

    public Admin(Integer id, String loginAcct, String password, String email, String createTime) {
        this.id = id;
        this.loginAcct = loginAcct;
        this.password = password;
        this.email = email;
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct == null ? null : loginAcct.trim();
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }


    public Admin(){}



}