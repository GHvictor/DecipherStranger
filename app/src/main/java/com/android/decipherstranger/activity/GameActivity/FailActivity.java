package com.android.decipherstranger.activity.GameActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                FailActivity.this.finish();
            }
        }, 5000);
    }

    /*    重写返回键，返回主界面*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){// 防止连续两次返回键
            //这你写你的返回处理
            Intent it = new Intent(FailActivity.this,MainPageActivity.class);
            startActivity(it);
            FailActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
