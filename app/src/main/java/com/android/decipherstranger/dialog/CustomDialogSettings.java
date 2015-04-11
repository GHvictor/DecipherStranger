package com.android.decipherstranger.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.RockPaperScissorsActivity;

/**
 * Created by peng on 2015/3/25.
 */
public class CustomDialogSettings extends Dialog {

    private Context context;

    private static final String FILENAME = "Game_Music";

    private CustomDialogSettings(Context context) {
        super(context);
        this.context = context;
    }

    private CustomDialogSettings(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static class Builder {

        private Context context;
        private SharedPreferences sharedPreferences = null;
        private Switch effectSwitch = null;
        private Switch backgroundSwitch = null;

        public Builder(Context context){
            this.context = context;
        }

        public CustomDialogSettings create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            this.sharedPreferences = context.getSharedPreferences(FILENAME,RockPaperScissorsActivity.MODE_PRIVATE);
            final CustomDialogSettings dialog = new CustomDialogSettings(context,R.style.Dialog);
            final View view = inflater.inflate(R.layout.game_dialog_settings,null);
            this.effectSwitch = (Switch)view.findViewById(R.id.setting_switch1);
            this.backgroundSwitch = (Switch) view.findViewById(R.id.setting_switch2);
            loadMusicFlag();
            this.effectSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl1());
            this.backgroundSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl2());
            dialog.setContentView(view);
            return dialog;
        }

        private void loadMusicFlag(){
            this.backgroundSwitch.setChecked(this.sharedPreferences.getBoolean("background",true));
            this.effectSwitch.setChecked(this.sharedPreferences.getBoolean("effect",true));
        }

        private class SwitchOnCheckedChangeListenerImpl1 implements CompoundButton.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"打开",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        }

        private class SwitchOnCheckedChangeListenerImpl2 implements CompoundButton.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"打开",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,"关闭",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
