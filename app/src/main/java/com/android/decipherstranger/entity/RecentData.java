package com.android.decipherstranger.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/2 0002.
 */
public class RecentData {
    private String recentUserAccount;
    private String recentUserName;
    private String recentMessage;
    private String recentMessageTime;
    private int recentUserPhotoId;


    public RecentData(String recentUserAccount, String recentUserName, String recentMessage, String recentMessageTime, int recentUserPhotoId) {
        super();
        this.recentUserAccount = recentUserAccount;
        this.recentUserName = recentUserName;
        this.recentMessage = recentMessage;
        this.recentMessageTime = recentMessageTime;
        this.recentUserPhotoId = recentUserPhotoId;
    }

    public RecentData() {
        super();
    }
    public String getRecentUserAccount() {
        return recentUserAccount;
    }

    public void setRecentUserAccount(String recentUserAccount) {
        this.recentUserAccount = recentUserAccount;
    }

    public String getRecentUserName() {
        return recentUserName;
    }

    public void setRecentUserName(String recentUserName) {
        this.recentUserName = recentUserName;
    }

    public String getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(String recentMessage) {
        this.recentMessage = recentMessage;
    }

    public String getRecentMessageTime() {
        return recentMessageTime;
    }

    public void setRecentMessageTime(String recentMessageTime) {
        this.recentMessageTime = recentMessageTime;
    }

    public int getRecentUserPhotoId() {
        return recentUserPhotoId;
    }

    public void setRecentUserPhotoId(int recentUserPhotoId) {
        this.recentUserPhotoId = recentUserPhotoId;
    }

}
