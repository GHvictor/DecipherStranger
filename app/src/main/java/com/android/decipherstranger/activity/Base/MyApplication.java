package com.android.decipherstranger.activity.Base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.List;

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
 * @Date 2015/5/8 16:34
 * @e-mail 785351408@qq.com
 */
public class MyApplication extends Application {
    
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;

    // 单例模式中获取唯一的MyApplication实例  
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // 添加Activity到容器中  
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    // 遍历所有Activity并finish  
    public void exit() {
        for (Activity activity : activityList) {
            System.out.println("tag_activity﹕ Exit" + activity.getClass().getSimpleName());
            activity.finish();
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    
    /*******全局变量***********************************************************************************************/

    //  用户账号
    private String account = null;
    //  用户昵称
    private String name = null;
    //  用户头像
    private Bitmap portrait = null;
    //  用户性别
    private String sex = null;
    //  用户邮箱
    private String email = null;
    //  用户电话
    private String phone = null;
    //  用户生日
    private String birth = null;
    //  用户地址
    private String address = null;
    //  个性签名
    private String signature = null;
    //未读消息
    private int unReadMessage;

    public int getUnReadMessage() {
        return unReadMessage;

    }

    public void setUnReadMessage(int unReadMessage) {
        this.unReadMessage = unReadMessage;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setSex(String userSex) {
        this.sex = userSex;
    }

    public String getSex() {
        return sex;
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

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }
}
