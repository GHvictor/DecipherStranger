package com.android.decipherstranger.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/4/2 0002.
 */
public class ChatData {
    private String recentUserName;
    private String recentMessage;
    private String recentMessageTime;
    private int recentUserPhotoId;
    @Override
    public String toString() {
        return "ChatListData{" +
                "recentUserName='" + recentUserName + '\'' +
                ", recentMessage='" + recentMessage + '\'' +
                ", recentMessageTime='" + recentMessageTime + '\'' +
                ", recentUserPhotoId=" + recentUserPhotoId +
                '}';
    }
    public ChatData(String recentUserName, String recentMessage, String recentMessageTime, int recentUserPhotoId) {
        super();
        this.recentUserName = recentUserName;
        this.recentMessage = recentMessage;
        this.recentMessageTime = recentMessageTime;
        this.recentUserPhotoId = recentUserPhotoId;
    }

    public ChatData() {
        super();
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
