package com.android.decipherstranger.activity.GameTwoActivity;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

/**
 * Created by Feng on 2015/5/18.
 */
public class WelcomeGnActivity extends BaseActivity {

    private MyApplication application = null;
    private PopupWindow helpPopWin = null;
    private ImageButton musicImage = null;
    private SharedPreferencesUtils sharedPreferencesUtils = null;
    public static MediaPlayer backgroundMusic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_welcome);
        application = (MyApplication) getApplication();
        this.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setBackgroundMusic();
    }

    private void init(){

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
}
