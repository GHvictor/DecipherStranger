package com.android.decipherstranger.activity.GameOneActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

/**
 * Created by PengHaitao on 2015/2/12.
 */
public class WelcomeRspActivity extends BaseActivity {

    private MyApplication application = null;
    private int grade = 6;  //  设置等级 默认为3
    private int sum = 20;
    private GameBroadcastReceiver receiver = null;
    private PopupWindow helpPopWin = null;
    private ImageButton musicImage = null;
    private SharedPreferencesUtils sharedPreferencesUtils = null;
    public static MediaPlayer backgroundMusic = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_welcome);
        application = (MyApplication) getApplication();
        this.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setBackgroundMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.backgroundMusic.isPlaying()) {
            this.backgroundMusic.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(WelcomeRspActivity.this.receiver);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //返回处理
            if (helpPopWin.isShowing()) {
                helpPopWin.dismiss();
                return true;
            }else {
                onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        this.musicImage = (ImageButton) super.findViewById(R.id.gameSound);
        this.sharedPreferencesUtils = new SharedPreferencesUtils(WelcomeRspActivity.this,MyStatic.FILENAME_SETTINGS);
        this.backgroundMusic = MediaPlayer.create(this, R.raw.background_music); //  获取背景音乐资源
        LayoutInflater inflater = LayoutInflater.from(WelcomeRspActivity.this);
        View view = inflater.inflate(R.layout.game_help_popup, null);
        this.helpPopWin = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

        Intent intent = getIntent();
        MyStatic.friendAccount = intent.getStringExtra("Account");
        this.gameBroadcas();
        //  设置用户游戏数据
        this.setGameInfo();
    }

    public void GameWelcomeOnClick(View view) {
        switch (view.getId()){
            case R.id.gameStart:gameStart();
                break;
            case R.id.gameTips:helpPopWin.showAtLocation(findViewById(R.id.top), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.gameHelp:helpPopWin.dismiss();
                break;
            case R.id.gameSound:
                if (MyStatic.gameBackgroundMusicFlag) {
                    MyStatic.gameBackgroundMusicFlag = false;
                } else {
                    MyStatic.gameBackgroundMusicFlag = true;
                }
                this.setBackgroundMusic();
                break;
        }
    }

    private void setGameInfo() {
        //  从缓存获取用户游戏设置
        MyStatic.gameEffectMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_EFFECT,true);
        MyStatic.gameBackgroundMusicFlag = (Boolean) sharedPreferencesUtils.get(MyStatic.KEY_BG,true);
        //  从服务器获取好友游戏等级

        //  TODO 此处写与服务器的通信函数
        if(NetworkService.getInstance().getIsConnected()){
            String gameUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgGameOneRecieve)+":"+
                    "account"+":"+application.getAccount()+":"+
                    "friend"+":"+MyStatic.friendAccount;
            Log.v("aaaaa", gameUser);
            NetworkService.getInstance().sendUpload(gameUser);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(WelcomeRspActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }

    private void gameBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new GameBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.GAMEONE");
        this.registerReceiver(receiver, filter);
    }

    private void gameStart(){
        Intent it = new Intent(WelcomeRspActivity.this, RockPaperScissorsActivity.class);
        it.putExtra("Grade", grade);        //  游戏等级
        it.putExtra("Sum", sum);
        startActivity(it);
        WelcomeRspActivity.this.finish();
    }

    private void setBackgroundMusic(){
        Drawable drawable = null;
        if (MyStatic.gameBackgroundMusicFlag) {
            this.backgroundMusic.setLooping(true);
            drawable = getResources().getDrawable(R.drawable.selector_music);
            this.backgroundMusic.start();
        } else {
            drawable = getResources().getDrawable(R.drawable.selector_music_false);
            if (this.backgroundMusic.isPlaying()) {
                this.backgroundMusic.pause();
            }
        } this.musicImage.setImageDrawable(drawable);
        sharedPreferencesUtils.set(MyStatic.KEY_BG, MyStatic.gameBackgroundMusicFlag);
    }

    public class GameBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.GAMEONE")) {
                // TODO 将获取的数据赋值到本地
                grade = intent.getIntExtra("reGrade", 3);
                sum = intent.getIntExtra("reSum", 20);
                MyStatic.rockInt = intent.getIntExtra("reRock", 10);
                MyStatic.scissorsInt = intent.getIntExtra("reScissors", 10);
                MyStatic.paperInt = intent.getIntExtra("rePaper", 10);
            }
        }
    }
}
