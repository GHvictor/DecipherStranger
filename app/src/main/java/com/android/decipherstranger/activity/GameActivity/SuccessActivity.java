package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;

/**
 * Created by acmer on 2015/3/20.
 */
public class SuccessActivity extends Activity {

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
        }, 3000);
    }
    
    private void Send() {
/*          type && MyStatic.UserAccount && MyStatic.friendAccount && 用户昵称
 *         聊天主界面创建接收
 *              实时刷新列表
 *              并用Toast提示
 *       Toast.makeText(this, "\t" + 好友昵称(即上传的用户昵称) + "\n成功添加您为好友！",Toast.LENGTH_SHORT).show();
 */
        if(NetworkService.getInstance().getIsConnected()){
            String addUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgAddFriend)+":"+
                    "account"+":"+MyStatic.UserAccount+":"+
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
