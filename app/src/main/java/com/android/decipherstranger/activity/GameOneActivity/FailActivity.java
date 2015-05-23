package com.android.decipherstranger.activity.GameOneActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;

/**
 * Created by acmer on 2015/3/20.
 */
public class FailActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fail);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.makefriend_lose);
        mediaPlayer.start();
    }
    
    public void GameFailOnClick(View view) {
        FailActivity.this.finish();
    }

}
