package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.LoginActivity;

/**
 * Created by PengHaitao on 2015/2/12.
 */
public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_welcome);
        
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                Intent it = new Intent(WelcomeActivity.this,RockPaperScissorsActivity.class);
                it.putExtra("Grade", 2);
                startActivity(it);
                WelcomeActivity.this.finish();
            }
        }, 500);
    }

}
