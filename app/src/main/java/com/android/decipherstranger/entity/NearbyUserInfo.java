package com.android.decipherstranger.entity;

import android.graphics.Bitmap;

import com.android.decipherstranger.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/20 0020.
 */
public class NearbyUserInfo implements Serializable {

    private String userAccount;
    private double latitude;
    private double longtitude;
    private Bitmap imgId;
    private String userName;
    private String sex;
    private String distance;

    public NearbyUserInfo() {
    }


    public NearbyUserInfo(double latitude, double longtitude, Bitmap imgId, String userName,String userAccount, String sex, String distance) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.imgId = imgId;
        this.userName = userName;
        this.sex = sex;
        this.distance = distance;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public Bitmap getImgId() {
        return imgId;
    }

    public void setImgId(Bitmap imgId) {
        this.imgId = imgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}