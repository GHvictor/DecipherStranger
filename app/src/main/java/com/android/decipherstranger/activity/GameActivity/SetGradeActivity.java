package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;

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
            if(NetworkService.getInstance().getIsConnected()){
                String gameUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgGameOneGrade)+
                        ":"+"account"+":"+ MyStatic.UserAccount+":"+"grade"+":"+grade;
                Log.v("aaaaa", gameUser);
                NetworkService.getInstance().sendUpload(gameUser);
            }
            else {
                NetworkService.getInstance().closeConnection();
                Toast.makeText(SetGradeActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
                Log.v("Login", "已经执行T（）方法");
            }
        }
    }
}
