package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.dialog.CustomDialogSettings;
import com.android.decipherstranger.util.ComputerAnswer;

/*
 * Created by peng on 2015/3/15.
 *
 * 　　　　  　┏┓　　　┏┓
 *　 　　 　┏┛┻━━━┛┻┓
 * 　　　　 ┃　　　　　　　┃
 * 　　　　 ┃　　　━　　　┃
 * 　　 　　┃　┳┛　┗┳　┃
 *　　　　　┃　　　　　　　┃
 *  　　　　┃　　　┻　　　┃
 *　　　　　┃　　　　　　　┃
 *　　  　　┗━┓　　　┏━┛
 * 　　 　　　　┃　　　┃ 神兽保佑
 * 　　　　 　　┃　　　┃   代码无BUG
 *　　　　　　　┃　　　┗━━━┓
 *　　 　　 　　┃　　　　　　　┣┓
 * 　　　　　 　┃　　　　　　　┏┛
 * 　　　 　　  ┗┓┓┏━┳┓┏┛
 * 　　　　 　　　┃┫┫　┃┫┫
 *　　　　　  　 ┗┻┛　┗┻┛
 * 
 *      Amended  by peng on 2015/3/28.
 */

public class RockPaperScissorsActivity extends Activity implements View.OnClickListener {
    
    private Handler handler = null;
    
    private PopupWindow popupWindow = null;

    private boolean overFlag = true;
    private boolean startFlag = false;

    private Intent intent = null;   /*接收玩家设定等级*/
    private int Grade = 0;          /*存储所接收等级*/
    private RelativeLayout gameStartLayout = null;

    private AnimationDrawable animationDrawablePlayer = null;
    private AnimationDrawable animationDrawableComputer = null;

    private static final String FILENAME = "Game_Music";
    private boolean backgroundMusicFlag = true;     //  音乐控制标志
    private boolean effectMusicFlag = true;         //  音效控制标志

    private MediaPlayer backgroundMusic = null;
    private MediaPlayer effectMusic = null;
    private MediaPlayer winMusic = null;
    private MediaPlayer loseMusic = null;
    private MediaPlayer dogfallMusic = null;

    private Drawable answerImageSrc = null;
    private Drawable playerImageSrc = null;
    private Drawable computerImageSrc = null;

    private int count = 0;
    private int gameGradeInt = 0;
    private int playerGradeInt = 0;
    private int computerGradeInt = 0;

    private String gameGradeString = null;
    private String playerGradeString = null;
    private String computerGradeString = null;

    private TextView gradeText = null;
    private TextView playerText = null;
    private TextView computerText = null;

    private ImageView playerImage = null;
    private ImageView computerImage = null;
    private ImageView gameAnswerImage = null;

    private ImageButton rockButton = null;
    private ImageButton paperButton = null;
    private ImageButton scissorsButton = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
    }
    
    private void start() {
        this.getMusicFlag();
        this.intiMenu();
        this.getGrade();        //  获取游戏等级
        this.setBackgroundMusic();
        this.setEffectMusic();
        this.intiView();
    }

    private void intiView(){

        this.gameStartLayout = (RelativeLayout)super.findViewById(R.id.gameLayout);
        this.gradeText = (TextView)super.findViewById(R.id.grade);
        this.playerText = (TextView)super.findViewById(R.id.player);
        this.computerText = (TextView)super.findViewById(R.id.computer);
        this.playerImage = (ImageView)super.findViewById(R.id.PlayerImage);
        this.computerImage = (ImageView)super.findViewById(R.id.ComputerImage);
        this.gameAnswerImage = (ImageView)super.findViewById(R.id.gameAnswer_Image);
        this.rockButton = (ImageButton)super.findViewById(R.id.RockImageBtn);
        this.paperButton = (ImageButton)super.findViewById(R.id.PaperImageBtn);
        this.scissorsButton = (ImageButton)super.findViewById(R.id.ScissorsImageBtn);

        this.gameStartLayout.setOnClickListener(new gameStartOnClickListenerImpl());
        this.rockButton.setOnClickListener(new RockOnClickListenerImpl());
        this.paperButton.setOnClickListener(new PaperOnClickListenerImpl());
        this.scissorsButton.setOnClickListener(new ScissorsOnClickListenerImpl());
    }

    private void getMusicFlag(){
        SharedPreferences share = super.getSharedPreferences(FILENAME,RockPaperScissorsActivity.MODE_PRIVATE);
        RockPaperScissorsActivity.this.backgroundMusicFlag = share.getBoolean("background",true);
        RockPaperScissorsActivity.this.effectMusicFlag = share.getBoolean("effect",true);
    }

    private void setBackgroundMusic(){
        this.backgroundMusic = MediaPlayer.create(this,R.raw.background_music); //  获取资源
        if (backgroundMusicFlag) {
            this.backgroundMusic.setLooping(true);
            this.backgroundMusic.start();
        }
    }

    private void setEffectMusic(){
        this.effectMusic = MediaPlayer.create(this, R.raw.effect_music);
        this.winMusic = MediaPlayer.create(this, R.raw.win_music); //  获取资源
        this.loseMusic = MediaPlayer.create(this, R.raw.lose_music); //  获取资源
        this.dogfallMusic = MediaPlayer.create(this, R.raw.dogfall_music); //  获取资源
    }

    private void closeMusic(){
        this.winMusic.release();
        this.loseMusic.release();
        this.dogfallMusic.release();
        this.backgroundMusic.release();
    }

    private class gameStartOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            if (overFlag) {
                overFlag = false;
                gameStart();
                startFlag = true;
            }
        }
    }

    private class RockOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            if (startFlag) {
                gamePause();
                gameOver(0);
                activityOver();
            }
        }
    }

    private class PaperOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            if (startFlag) {
                gamePause();
                gameOver(5);
                activityOver();
            }
        }
    }

    private class ScissorsOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            if (startFlag) {
                gamePause();
                gameOver(2);
                activityOver();
            }
        }
    }

    private void getGrade(){
        this.intent = super.getIntent();
        int gradeIt = intent.getIntExtra("Grade",5);
        this.Grade = 2 * gradeIt;
    }


    private void gameStart(){
        this.gameAnswerImage.setImageDrawable(null);

        this.animationDrawablePlayer = new AnimationDrawable();
        this.animationDrawableComputer = new AnimationDrawable();
        this.animationDrawablePlayer = (AnimationDrawable)getResources().getDrawable(R.drawable.game_rock_paper_scissors_player);
        this.animationDrawableComputer = (AnimationDrawable)getResources().getDrawable(R.drawable.game_rock_paper_scissors_computer);

        this.playerImage.setImageDrawable(this.animationDrawablePlayer);
        this.computerImage.setImageDrawable(this.animationDrawableComputer);

        this.animationDrawablePlayer.start();
        this.animationDrawableComputer.start();
    }

    private void gamePause(){
        this.animationDrawablePlayer.stop();
        this.animationDrawableComputer.stop();
        startFlag = false;
    }

    private void gameOver(int player){
        int computer = computerShow(player);
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
            count = 0;
            answerImageSrc = getResources().getDrawable(R.drawable.game_dogfall);
            if (effectMusicFlag) {
                this.dogfallMusic.start();
            }
            player = computer = 1;
        }else if (answer == -2 || answer == -3 || answer == 5){
            count ++;
            answerImageSrc = getResources().getDrawable(R.drawable.game_win);
            if (effectMusicFlag) {
                this.winMusic.start();
            }
            player = 2; computer = 0;
        }else {
            count = 0;
            answerImageSrc = getResources().getDrawable(R.drawable.game_lose);
            if (effectMusicFlag) {
                this.loseMusic.start();
            }
            player = 0; computer = 2;
        }
        setText(player, computer);
        this.gameAnswerImage.setImageDrawable(answerImageSrc);
    }

    private void activityOver(){
        boolean flag = false;
        if (this.gameGradeInt >= this.Grade) {
            flag = true;
            Toast.makeText(this, "请求成功", Toast.LENGTH_LONG).show();
            this.intent = new Intent(RockPaperScissorsActivity.this, SuccessActivity.class);   //  根据实际情况跳转
        } else if (this.gameGradeInt <= -this.Grade) {
            flag = true;
            Toast.makeText(this, "请求失败", Toast.LENGTH_LONG).show();
            this.intent = new Intent(RockPaperScissorsActivity.this, ErrorActivity.class);   //  根据实际情况跳转
        }
        if (flag) {
            this.closeMusic();            
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(intent);
                    RockPaperScissorsActivity.this.finish();
                }
            }, 2000);
        } else {
            this.overFlag = true;
        }
    }

    private int computerShow(int player){
        ComputerAnswer computerAnswer = new ComputerAnswer();
        int answerC = computerAnswer.Answer(count,player,Grade);
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

    private void goHelp(){
        intent = new Intent(RockPaperScissorsActivity.this,HelpActivity.class);
        startActivity(intent);
    }

    private void settings(){
        CustomDialogSettings.Builder dialog = new CustomDialogSettings.Builder(RockPaperScissorsActivity.this);
        dialog.create().show();
    }

    private void intiMenu() {
        LayoutInflater inflater = LayoutInflater.from(RockPaperScissorsActivity.this);
        View view = inflater.inflate(R.layout.game_meun_popup, null);

        LinearLayout blankView = (LinearLayout) view.findViewById(R.id.blank);
        Button settingsButton = (Button) view.findViewById(R.id.game_setUp);
        Button helpButton = (Button) view.findViewById(R.id.game_help);
        Button quitButton = (Button) view.findViewById(R.id.game_quit);
        Button cancelButton = (Button) view.findViewById(R.id.cancel);

        blankView.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        this.popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_setUp:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                settings(); break;
            case R.id.game_help: goHelp();
                break;
            case R.id.game_quit:
                this.closeMusic();
                onBackPressed();
                break;
            default:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(findViewById(R.id.gameAnswer_Image), Gravity.BOTTOM, 0, 0);
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.closeMusic();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //这你写你的返回处理
            if (popupWindow.isShowing()) {
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
}
