package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.decipherstranger.R;

/**
 * Created by acmer on 2015/3/20.
 */
public class SetGradeActivity extends Activity {

    private RadioGroup radioGroup = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_set_grade);
        
        this.radioGroup = (RadioGroup) super.findViewById(R.id.GameRadioGroup);
        this.radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListenerImpl());
    }
    
    private class OnCheckedChangeListenerImpl implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.radioButton1:
                    setGradeToWeb(1);
                    break;
                case R.id.radioButton2:
                    setGradeToWeb(2);
                    break;
                case R.id.radioButton3:
                    setGradeToWeb(3);
                    break;
                case R.id.radioButton4:
                    setGradeToWeb(4);
                    break;
                case R.id.radioButton5:
                    setGradeToWeb(5);
                    break;
                case R.id.radioButton0:
                    setGradeToWeb(0);
                    break;
            }
        }
    }
 
    private void setGradeToWeb(int grade){
        if (grade == 0) {
            Toast.makeText(this, "该功能尚未开放，敬请期待", Toast.LENGTH_SHORT).show();
        } else {
            //  TODO 在此处上传游戏等级grade至服务器
        }
    }
}
