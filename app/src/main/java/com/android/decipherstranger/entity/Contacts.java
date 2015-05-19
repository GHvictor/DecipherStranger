package com.android.decipherstranger.entity;

import android.graphics.Bitmap;

/**
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 *
 * @author penghaitao
 * @version V1.0
 * @Date 2015/4/23 21:53
 * @e-mail 785351408@qq.com
 */
public class Contacts {

    private String account = null;
    private String username = null;
    private Bitmap portrait = null;
    private int who = 0;
    private String datetime = null;
    private String message = null;
    private String timeLen = null;
    private int type = 0;
    
    public Contacts(){}
    public Contacts(Contacts contacts) {
        this.account = contacts.account;
        this.username = contacts.username;
        this.portrait = contacts.portrait;
        this.who = contacts.who;
        this.datetime = contacts.datetime;
        this.message = contacts.message;
        this.timeLen = contacts.timeLen;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }

    public Bitmap getPortrait() {
        return portrait;
    }
    
    public void setWho(int who) {
        this.who = who;
    }
    
    public int getWho() {
        return who;
    }
    
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimeLen() {
        return timeLen;
    }

    public void setTimeLen(String timeLen) {
        this.timeLen = timeLen;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
