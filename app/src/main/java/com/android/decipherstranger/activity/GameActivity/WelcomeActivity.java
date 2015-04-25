package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.util.GameUtils;
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

        //  设置用户游戏数据
        this.setGameInfo();

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

    private void setGameInfo() {
        //  从缓存获取用户游戏设置
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,MyStatic.FILENAME_SETTINGS);
        MyStatic.gameEffectMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_EFFECT,true);
        MyStatic.gameBackgroundMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_BG,true);
        //  从服务器获取好友游戏等级
        
        //  TODO 此处写与服务器的通信函数
        
        //  从服务器获取用户游戏习惯 石头剪刀布概率
        GameUtils.get();
    }

    public class ShakeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.GAME")) {
                // TODO 将获取的数据赋值到本地        
                grade = 3;
                MyStatic.rockInt = 33;
                MyStatic.scissorsInt = 33;
                MyStatic.paperInt = 33;
                
            }
        }
    }
    
}
