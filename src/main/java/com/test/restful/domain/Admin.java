package com.test.restful.domain;

//lombok组件，挺好用的哈哈，至少代码看起来简洁了很多
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin {
    private Integer adminId;

    private String password;

//    public Integer getAdminId() {
//        return adminId;
//    }
//
//    public void setAdminId(Integer adminId) {
//        this.adminId = adminId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password == null ? null : password.trim();
//    }
}