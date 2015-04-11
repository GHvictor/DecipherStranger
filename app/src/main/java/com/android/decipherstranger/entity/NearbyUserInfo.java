package com.android.decipherstranger.entity;

import com.android.decipherstranger.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/20 0020.
 */
public class NearbyUserInfo implements Serializable {
    private double latitude;
    private double longtitude;
    private  int imgId;
    private String userName;
    private String sex;
    private String distance;
    private int zan;

    public NearbyUserInfo() {
    }

    @Override
    public String toString() {
        return "NearByUserInfo{" +
                "latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", imgId=" + imgId +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", distance='" + distance + '\'' +
                ", zan=" + zan +

                '}';
    }

    public static List<NearbyUserInfo> infos = new ArrayList<NearbyUserInfo>();

    static {
        infos.add(new NearbyUserInfo(43.835591,125.312506, R.drawable.user_photo1,"梁静茹","女","距离855米",226));
        infos.add(new NearbyUserInfo(43.834290,125.300649, R.drawable.user_photo5,"周杰伦","男","距离955米",339));
        infos.add(new NearbyUserInfo(43.835903,125.303020, R.drawable.user_photo6,"孙燕姿","女","距离755米",669));
        infos.add(new NearbyUserInfo(43.834941,125.304583, R.drawable.user_photo7,"陈奕迅","男","距离1520米",80));
    }

    public NearbyUserInfo(double latitude, double longtitude, int imgId, String userName, String sex, String distance, int zan) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.imgId = imgId;
        this.userName = userName;
        this.sex = sex;
        this.distance = distance;
        this.zan = zan;
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

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
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

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}