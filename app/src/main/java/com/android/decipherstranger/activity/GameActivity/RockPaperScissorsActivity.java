package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.dialog.CustomDialogSettings;
import com.android.decipherstranger.util.GameUtils;
import com.android.decipherstranger.util.MyApplication;
import com.android.decipherstranger.util.MyStatic;

/**
 *             へ　　　　　／|
 *        　　/＼7　　　 ∠＿/
 *        　 /　│　　 ／　／
 *        　│　Z ＿,＜　／　　 /`ヽ
 *        　│　　　　　ヽ　　 /　　〉
 *        　 Y　　　　　`　 /　　/
 *        　ｲ●　､　●　　⊂⊃〈　　/
 *        　()　 へ　　　　|　＼〈
 *        　　>ｰ ､_　 ィ　 │ ／／      去吧！
 *        　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 *        　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 *        　　7　　　　　　　|／
 *        　　＞―r￣￣`ｰ―＿
 *
 *       @ClassName        ShakeListener
 *       @author            penghaitao
 *       @version           1.0
 *       @Date              2015/4/10.
 *       @e-mail           785351408@qq.com
 **/

public class RockPaperScissorsActivity extends Activity {

    private MyApplication application = null;
    private static Intent intent = null;
    
    private TextView gradeText = null;
    private TextView playerText = null;
    private TextView computerText = null;
    private ImageView playerImage = null;
    private ImageView computerImage = null;
    private ImageView gameAnswerImage = null;
    private AnimationDrawable animationDrawablePlayer = null;
    private AnimationDrawable animationDrawableComputer = null;
    private Drawable answerImageSrc = null;
    private Drawable playerImageSrc = null;
    private Drawable computerImageSrc = null;
    private PopupWindow popupWindow = null;
    private MediaPlayer backgroundMusic = WelcomeRspActivity.backgroundMusic;
    private MediaPlayer winMusic = null;
    private MediaPlayer loseMusic = null;
    private MediaPlayer dogfallMusic = null;
    private SlidingDrawer slidingDrawer = null;
    private MyBroadcastReceiver receiver = null;

    private int Grade = 6;              //  存储所接收等级
    private int MaxSum = 20;
    private int sum = 0;
    private boolean isRun = true;      //  游戏运行标志 默认游戏运行    
    private int gameGradeInt = 0;
    private int playerGradeInt = 0;
    private int computerGradeInt = 0;
    private String gameGradeString = null;
    private String playerGradeString = null;
    private String computerGradeString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_game_main);
        application = (MyApplication) getApplication();
        this.init();
        this.gameStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setMusic();
        //  动态注册广播
        this.registerBroadcas();
    }

    @Override
    protected void onPause() {
        super.onPause();
        super.unregisterReceiver(RockPaperScissorsActivity.this.receiver);
        this.closeMusic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!slidingDrawer.isOpened()) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.showAtLocation(findViewById(R.id.gameAnswer_Image), Gravity.BOTTOM, 0, 0);
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //返回处理
            if (slidingDrawer.isOpened()) {
                slidingDrawer.animateClose();
                return true;
            }else if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            } else {
                if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.ECLAIR) {
                    event.startTracking();
                } else {
                    onBackPressed();
                }
                RockPaperScissorsActivity.this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init(){
        //  获取布局控件
        this.gradeText = (TextView)super.findViewById(R.id.grade);
        this.playerText = (TextView)super.findViewById(R.id.player);
        this.computerText = (TextView)super.findViewById(R.id.computer);
        this.playerImage = (ImageView)super.findViewById(R.id.PlayerImage);
        this.computerImage = (ImageView)super.findViewById(R.id.ComputerImage);
        this.gameAnswerImage = (ImageView)super.findViewById(R.id.gameAnswer_Image);
        this.slidingDrawer = (SlidingDrawer)super.findViewById(R.id.slidingDrawer);

        //  设置Animation
        animationDrawablePlayer = new AnimationDrawable();
        animationDrawableComputer = new AnimationDrawable();
        animationDrawablePlayer = (AnimationDrawable)getResources().getDrawable(R.drawable.game_rock_paper_scissors_player);
        animationDrawableComputer = (AnimationDrawable)getResources().getDrawable(R.drawable.game_rock_paper_scissors_computer);

        //  获取游戏初始数据
        Intent intent = getIntent();
        this.Grade = intent.getIntExtra("Grade", 6);    //  获取游戏等级，默认为3级
        this.MaxSum = intent.getIntExtra("Sum", 20);    

        //  获取Menu控件
        LayoutInflater inflater = LayoutInflater.from(RockPaperScissorsActivity.this);
        View view = inflater.inflate(R.layout.game_meun_popup, null);
        this.popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void setMusic() {
        this.setBackgroundMusic();
        this.setEffectMusic();
    }

    private void setBackgroundMusic(){
        if (MyStatic.gameBackgroundMusicFlag) {
            this.backgroundMusic.setLooping(true);
            this.backgroundMusic.start();
        } else {
            if (this.backgroundMusic.isPlaying()) {
                this.backgroundMusic.pause();
            }
        }
    }

    private void setEffectMusic(){
        if (MyStatic.gameEffectMusicFlag) {
            this.winMusic = MediaPlayer.create(this, R.raw.win_music); //  获取资源
            this.loseMusic = MediaPlayer.create(this, R.raw.lose_music); //  获取资源
            this.dogfallMusic = MediaPlayer.create(this, R.raw.dogfall_music); //  获取资源
        } else {
            this.winMusic = MediaPlayer.create(this, R.raw.no_music); //  获取资源
            this.loseMusic = MediaPlayer.create(this, R.raw.no_music); //  获取资源
            this.dogfallMusic = MediaPlayer.create(this, R.raw.no_music); //  获取资源
        }
    }

    private void closeMusic(){
        this.winMusic.release();
        this.loseMusic.release();
        this.dogfallMusic.release();
        this.backgroundMusic.pause();
    }

    public void GameOnClick(View view) {
        switch (view.getId()) {
            case R.id.gameLayout:
                if (!isRun) {
                    gameStart();
                    isRun = true;
                } break;
            case R.id.RockImageBtn:
                if (isRun) {
                    gamePause(0);
                } break;
            case R.id.PaperImageBtn:
                if (isRun) {
                    gamePause(5);
                } break;
            case R.id.ScissorsImageBtn:
                if (isRun) {
                    gamePause(2);
                } break;
        }
    }

    private void gameStart(){
        this.sum += 1;
        this.gameAnswerImage.setImageDrawable(null);
        this.playerImage.setImageDrawable(this.animationDrawablePlayer);
        this.computerImage.setImageDrawable(this.animationDrawableComputer);
        this.animationDrawablePlayer.start();
        this.animationDrawableComputer.start();
    }

    private void gamePause(int player){
        isRun = false;
        new Thread(){
            public void run() {
                animationDrawablePlayer.stop();
                animationDrawableComputer.stop();
            }
        }.start();
        GameUtils.update(player);
        int computer = computerShow();
        switch (player){
            case 0:this.playerImageSrc = getResources().getDrawable(R.drawable.game_rock_pressed);
                break;
            case 2:this.playerImageSrc = getResources().getDrawable(R.drawable.game_scissors_pressed);
                break;
            case 5:this.playerImageSrc = getResources().getDrawable(R.drawable.game_paper_pressed);
                break;
        }
        this.playerImage.setImageDrawable(playerImageSrc);
        int answer = player - computer;
        if (answer == 0){
            answerImageSrc = getResources().getDrawable(R.drawable.game_dogfall);
            this.dogfallMusic.start();
            player = computer = 1;
        }else if (answer == -2 || answer == -3 || answer == 5){
            answerImageSrc = getResources().getDrawable(R.drawable.game_win);
            this.winMusic.start();
            player = 2; computer = 0;
        }else {
            answerImageSrc = getResources().getDrawable(R.drawable.game_lose);
            this.loseMusic.start();
            player = 0; computer = 2;
        }
        setText(player, computer);
        this.gameAnswerImage.setImageDrawable(answerImageSrc);
        IfGameOver();
    }

    private int computerShow(){
        int answerC = GameUtils.Answer();
        switch (answerC){
            case 0:this.computerImageSrc = getResources().getDrawable(R.drawable.game_rock_computer);
                break;
            case 2:this.computerImageSrc = getResources().getDrawable(R.drawable.game_scissors_computer);
                break;
            case 5:this.computerImageSrc = getResources().getDrawable(R.drawable.game_paper_computer);
                break;
        }
        this.computerImage.setImageDrawable(computerImageSrc);
        return answerC;
    }

    private void setText(int playerGrade, int computerGrade){
        this.playerGradeInt += playerGrade;
        this.computerGradeInt += computerGrade;
        this.gameGradeInt = this.playerGradeInt - this.computerGradeInt;

        this.playerGradeString = Integer.toString(this.playerGradeInt);
        this.computerGradeString = Integer.toString(this.computerGradeInt);
        this.gameGradeString = Integer.toString(this.gameGradeInt);

        this.playerText.setText(this.playerGradeString);
        this.computerText.setText(this.computerGradeString);
        this.gradeText.setText(this.gameGradeString);
    }

    private void IfGameOver(){
        boolean flag = false;
        if (this.gameGradeInt >= this.Grade && this.sum <= this.MaxSum) {
            flag = true;
            intent = new Intent(RockPaperScissorsActivity.this, SuccessActivity.class);   //  根据实际情况跳转
        } else if (this.gameGradeInt <= -this.Grade || this.sum > this.MaxSum) {
            flag = true;
            intent = new Intent(RockPaperScissorsActivity.this, FailActivity.class);   //  根据实际情况跳转
        }
        if (flag) {
            //  上传游戏数据
            new Thread(){
                public void run(){
                    GameUtils.set(application.getAccount());
                }
            }.start();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    RockPaperScissorsActivity.this.closeMusic();
                    RockPaperScissorsActivity.this.startActivity(intent);
                    RockPaperScissorsActivity.this.finish();
                }
            }, 1000);
        } else {
            this.isRun = false;
        }
    }

    public void MenuOnClick(View v) {
        switch (v.getId()) {
            case R.id.game_setUp:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                settings(); break;
            case R.id.game_help:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                slidingDrawer.animateOpen();
                break;
            case R.id.game_quit:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                RockPaperScissorsActivity.this.finish();
                break;
            default:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    private void settings(){
        CustomDialogSettings dialogSettings = new CustomDialogSettings(RockPaperScissorsActivity.this,R.style.Dialog);
        dialogSettings.show();
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.game.SETTINGS");
        this.registerReceiver(receiver, filter);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.game.SETTINGS")) {
                setMusic();
            }
        }
    }

    public void backToGame(View view) {
        slidingDrawer.animateClose();
    }

}
