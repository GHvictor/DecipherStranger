package com.android.decipherstranger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.android.decipherstranger.R;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

/**
 * Created by peng on 2015/3/25.
 */
public class CustomDialogSettings extends Dialog {

    private Context context;
    private Switch effectSwitch = null;
    private Switch backgroundSwitch = null;
    private Button confirmButton = null;

    private boolean bgMusic = MyStatic.gameBackgroundMusicFlag;
    private boolean effectMusic = MyStatic.gameEffectMusicFlag;

    public CustomDialogSettings(Context context) {
        super(context);
        this.context = context;
    }
    public CustomDialogSettings(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.game_dialog_settings);

        this.effectSwitch = (Switch) super.findViewById(R.id.gameEffectSwitch);
        this.backgroundSwitch = (Switch) super.findViewById(R.id.gameBgSwitch);
        this.confirmButton = (Button) super.findViewById(R.id.confirm);

        this.effectSwitch.setChecked(MyStatic.gameEffectMusicFlag);
        this.backgroundSwitch.setChecked(MyStatic.gameBackgroundMusicFlag);
        this.effectSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl1());
        this.backgroundSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl2());

        this.confirmButton.setOnClickListener(new confirmOnClickImpl());
    }

    private class SwitchOnCheckedChangeListenerImpl1 implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                effectMusic = true;
            } else {
                effectMusic = false;
            }
        }
    }

    private class SwitchOnCheckedChangeListenerImpl2 implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                bgMusic = true;
            } else {
                bgMusic = false;
            }
        }
    }

    private class confirmOnClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            new Thread() {
                public void run(){
                    MyStatic.gameBackgroundMusicFlag = bgMusic;
                    MyStatic.gameEffectMusicFlag = effectMusic;
                    Intent intent = new Intent("com.android.game.SETTINGS");
                    context.sendBroadcast(intent);
                }
            }.start();
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,MyStatic.FILENAME_SETTINGS);
            sharedPreferencesUtils.set(MyStatic.KEY_BG, bgMusic);
            sharedPreferencesUtils.set(MyStatic.KEY_EFFECT,effectMusic);
            onBackPressed();
        }
    }
}
