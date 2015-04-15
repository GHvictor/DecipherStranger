package com.android.decipherstranger.util;

/**
 *      へ　　　　　／|
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
 * @Date 2015/4/13 11:56
 * @e-mail 785351408@qq.com
 */
public class MyStatic {
    //  设置全局变量
    public static final String FILENAME_SETTINGS = "Settings";      //  游戏设置文件
    public static final String KEY_BG = "gameBgMusic";              //  游戏设置文件 背景音乐KEY
    public static final String KEY_EFFECT = "gameEffectMusic";      //  游戏设置文件 音效KEY

    public static boolean gameEffectMusicFlag = true;         //  游戏音效控制标志
    public static boolean gameBackgroundMusicFlag = true;     //  游戏音乐控制标志

    public static String friendAccount = null;          //  所添加好友account

    public static int rockInt = 333;            //  当前用户出石头的几率
    public static int scissorsInt = 333;        //  当前用户出剪刀的几率
    public static int paperInt = 333;           //  当前用户出布的几率
}
