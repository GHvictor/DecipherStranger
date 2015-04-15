package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.LoginActivity;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

/**
 * Created by PengHaitao on 2015/2/12.
 * toDo 获取游戏等级以及游戏设置
 */
public class WelcomeActivity extends Activity {

    private int grade = 3;  //  设置等级 默认为3

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_welcome);

        //  从缓存获取用户游戏设置
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,MyStatic.FILENAME_SETTINGS);
        MyStatic.gameEffectMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_EFFECT,true);
        MyStatic.gameBackgroundMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_BG,true);

        //  获取用户游戏网络数据
        this.getGrade();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent it = new Intent(WelcomeActivity.this, RockPaperScissorsActivity.class);
                it.putExtra("Grade", grade);        //  游戏等级
                startActivity(it);
                WelcomeActivity.this.finish();
            }
        }, 1000);
    }

    private void getGrade() {
        //  获取好友游戏等级
        this.grade = 1;
        // 获取用户游戏习惯 石头剪刀布概率
        MyStatic.rockInt = 333;
        MyStatic.scissorsInt = 333;
        MyStatic.paperInt = 333;
    }

}
