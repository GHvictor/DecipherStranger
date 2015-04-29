package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.MainPage;
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
 *              
 */
    }
}
