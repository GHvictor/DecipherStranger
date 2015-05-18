package com.android.decipherstranger.activity.GameOneActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;

/**
 * Created by acmer on 2015/3/20.
 */
public class SuccessActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_success);
        
        this.Send();

        Toast.makeText(this, "已添加" + MyStatic.friendName + "为好友！",Toast.LENGTH_LONG).show();
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.makefriend_success);
        mediaPlayer.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                SuccessActivity.this.finish();
            }
        }, 5000);
    }
    
    private void Send() {
        MyApplication application = (MyApplication) getApplication();
        if(NetworkService.getInstance().getIsConnected()){
            String addUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgAddFriend)+":"+
                    "account"+":"+application.getAccount()+":"+
                    "friend"+":"+MyStatic.friendAccount;
            Log.v("aaaaa", addUser);
            NetworkService.getInstance().sendUpload(addUser);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(SuccessActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }
}
