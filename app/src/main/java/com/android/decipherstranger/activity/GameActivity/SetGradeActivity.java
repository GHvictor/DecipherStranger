package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyApplication;
import com.android.decipherstranger.util.MyStatic;

/**
 * Created by acmer on 2015/3/20.
 */
public class SetGradeActivity extends Activity {
    
    private MyApplication application = null;
    private EditText gradeEdit = null;
    private EditText sumEdit = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rsp_set_grade);
        application = (MyApplication) getApplication();
        this.init();
    }
    
    private void init() {
        this.gradeEdit = (EditText) super.findViewById(R.id.editText1);
        this.sumEdit = (EditText) super.findViewById(R.id.editText2);
        this.gradeEdit.setText("6");
        this.sumEdit.setText("20");
    }
    
    public void GradeSetOnClick(View view) {
        switch (view.getId()) {
            case R.id.gamelist_back_button:
                onBackPressed();
                break;
            case R.id.save_btn:
                setGradeToWeb();
                onBackPressed();
                break;
        }
    }
 
    private void setGradeToWeb(){
        int grade = Integer.parseInt(this.gradeEdit.getText().toString());
        int sum = Integer.parseInt(this.sumEdit.getText().toString());
            //  TODO 在此处上传grade & sum至服务器
            if(NetworkService.getInstance().getIsConnected()){
                String gameUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgGameOneGrade)+
                        ":"+"account"+":"+ application.getAccount()+":"+"grade"+":"+grade+":"+
                        "sum"+":"+sum;
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
