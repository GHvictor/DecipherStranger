
package com.android.decipherstranger.util;

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
        int a = 33;
        int b = 33;
        int c = 33;
        MyStatic.rockInt = a;
        MyStatic.scissorsInt = b;
        MyStatic.paperInt = c;
    }
    
    //  上传用户习性
    public static void set(){
        
    }
}