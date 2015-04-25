package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.MainPage;

/**
 * Created by acmer on 2015/3/20.
 */
public class SuccessActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_success);

           //  TODO 添加好友成功 发送推送

        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.makefriend_success);
        mediaPlayer.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                SuccessActivity.this.finish();
            }
        }, 3000);
    }
}
