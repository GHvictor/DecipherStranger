package com.android.decipherstranger.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by PengHaitao on 2015/2/26.
 */
public class User implements Serializable {
    //用户名
    private String account;
    //用户密码
    private String password;
    //用户呢称
    private String username;
    //用户备注
    private String remark;
    //用户性别
    private String userSex;
    //用户邮箱
    private String email;
    //用户电话
    private String phone;
    //用户生日
    private String birth;
    //用户地址
    private String address;
    //显示用户名拼音的首字母
    private String sortLetters;
    //  头像
    private Bitmap portrait;
    //  个性签名
    private String signature;

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

    public void setAccount(String account) {this.account = account;}
    
    public String getAccount() {return account;}

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return password;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRemark() {return remark;}

    public void setRemark(String remark) {this.remark = remark;}

    public String getUserSex() {return userSex;}

    public void setUserSex(String userSex) {this.userSex = userSex;}

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {return email;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBirth(String birth) {this.birth = birth;}

    public String getBirth() {return birth;}

    public void setAddress(String address) {this.address = address;}

    public String getAddress() {return address;}

    public void setSignature(String portraitUrl) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }
    
    public String getSortLetters() {return sortLetters;}

    public void setSortLetters(String sortLetters) {this.sortLetters = sortLetters;}

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setPortrait(Bitmap portrait) {this.portrait = portrait;}


}
