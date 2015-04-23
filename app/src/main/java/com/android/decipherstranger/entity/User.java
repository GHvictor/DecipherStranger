package com.android.decipherstranger.entity;

import java.io.Serializable;

/**
 * Created by PengHaitao on 2015/2/26.
 */
public class User implements Serializable {
    
    private String account = null;
    private String password = null;
    private String username = null;
    private String email = null;
    private String phone = null;
    private String birth = null;
    private String address = null;
    private String portrait = null;              //  头像
    private String signature = null;                //  个性签名

    public User(){}
    public User(User user) {
        this.account = user.account;
        this.password = user.password;
        this.username = user.username;
        this.email = user.email;
        this.phone = user.phone;
        this.birth = user.birth;
        this.address = user.address;
        this.portrait = user.portrait;
        this.signature = user.signature;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getAccount() {
        return account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBirth() {
        return birth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setSignature(String portraitUrl) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

}
