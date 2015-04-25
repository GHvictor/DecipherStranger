
package com.android.decipherstranger.util;

import android.util.Log;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;

import java.util.Random;

/**
 * Created by peng on 2015/3/17.
 */
public class GameUtils {

    //  计算电脑出招
    public static int Answer() {
        int count = MyStatic.rockInt + MyStatic.scissorsInt + MyStatic.paperInt;
        Random random = new Random();
        int rand = (random.nextInt() % count + count) % count; 
        if (rand <= MyStatic.rockInt) {
            return 5;
        } else if (rand <= MyStatic.rockInt + MyStatic.scissorsInt) {
            return 0;
        } else {
            return 2;
        }
    }
    
    //  实时更新用户习性
    public static void update(int temp){
        switch (temp){
            case 0:
                MyStatic.rockInt += 1;
                break;
            case 2:
                MyStatic.scissorsInt += 1;
                break;
            case 5:
                MyStatic.paperInt += 1;
                break;
        }
    }
    
    //  获取用户习性
    public static void get(){
/*
        if(NetworkService.getInstance().getIsConnected()){
            String gameUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgGameOneRecieve)+"account"+":"+MyStatic.UserAccount;
            Log.v("aaaaa", gameUser);
            NetworkService.getInstance().sendUpload(gameUser);
        }
        else {
            NetworkService.getInstance().closeConnection();
            //Toast.makeText(LoginActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
*/
        /*
        int a = 33;
        int b = 33;
        int c = 33;
        MyStatic.rockInt = a;
        MyStatic.scissorsInt = b;
        MyStatic.paperInt = c;*/
    }
    
    //  上传用户习性
    public static void set(){
/*
        if(NetworkService.getInstance().getIsConnected()){
            String gameUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgGameOneSend)+
                    ":"+"account"+":"+MyStatic.UserAccount+":"+"rock"+":"+MyStatic.rockInt+":"+
                    "scissors"+":"+MyStatic.scissorsInt+":"+"paper"+":"+MyStatic.paperInt;
            Log.v("aaaaa", gameUser);
            NetworkService.getInstance().sendUpload(gameUser);
        }
        else {
            NetworkService.getInstance().closeConnection();
            //Toast.makeText(LoginActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
*/
    }
}