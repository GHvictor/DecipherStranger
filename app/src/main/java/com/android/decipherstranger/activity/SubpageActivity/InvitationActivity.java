package com.android.decipherstranger.activity.SubpageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;

/**
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 *
 * @author penghaitao
 * @version V1.0
 * @Date 2015/5/23 16:19
 * @e-mail 785351408@qq.com
 */
public class InvitationActivity extends BaseActivity {
    
    private InvitationBroadcastReceiver receiver = null;
    
    private SurfaceView surfaceView = null;
    private ImageButton sendInvitation = null;
    private ImageButton receiveInvitation = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_invitation);
        this.registerBroadcas();
        this.init();
    }
    
    private void init() {
        this.surfaceView = (SurfaceView) super.findViewById(R.id.surfaceView);
        this.sendInvitation = (ImageButton) super.findViewById(R.id.sendInvitation);
        this.receiveInvitation = (ImageButton) super.findViewById(R.id.receiveInvitation);
    }
    
    public void InvitationOnClick(View view) {
        switch (view.getId()) {
            case R.id.sendInvitation:
                send();
                break;
            case R.id.receiveInvitation:
                receive();
                break;
        }
    }
    
    private void send() {
        //  发送邀请券
    }
    
    private void receive() {
        //  接收邀请券
    }
    
    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new InvitationBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.INVITATION");
        this.registerReceiver(receiver, filter);
    }

    public class InvitationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.INVITATION")) {

            }
        }
    }
}
