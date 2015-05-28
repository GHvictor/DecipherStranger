package com.android.decipherstranger.activity.SubpageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.AudioManager;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;

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

    private MyApplication application = null;
    private InvitationBroadcastReceiver receiver = null;
    
    private ImageView imageView = null;
    
    private boolean click = false;
    
    private String account = null;
    private String name = null;
    private int sex = 0;
    private String portrait = null;

    int startAnimationFile[] = {
            R.drawable.invitation_start_01, R.drawable.invitation_start_02, R.drawable.invitation_start_03, R.drawable.invitation_start_04, R.drawable.invitation_start_05,
            R.drawable.invitation_start_06, R.drawable.invitation_start_07, R.drawable.invitation_start_08, R.drawable.invitation_start_09, R.drawable.invitation_start_10,
            R.drawable.invitation_start_11, R.drawable.invitation_start_12, R.drawable.invitation_start_13, R.drawable.invitation_start_14, R.drawable.invitation_start_15,
            R.drawable.invitation_start_16, R.drawable.invitation_start_17, R.drawable.invitation_start_18, R.drawable.invitation_start_19, R.drawable.invitation_start_20,
            R.drawable.invitation_start_21, R.drawable.invitation_start_22, R.drawable.invitation_start_23, R.drawable.invitation_start_24, R.drawable.invitation_start_25,
            R.drawable.invitation_start_26, R.drawable.invitation_start_27, R.drawable.invitation_start_28, R.drawable.invitation_start_29, R.drawable.invitation_start_30,
            R.drawable.invitation_start_31, R.drawable.invitation_start_32, R.drawable.invitation_start_33, R.drawable.invitation_start_34, R.drawable.invitation_start_35,
            R.drawable.invitation_start_36, R.drawable.invitation_start_37, R.drawable.invitation_start_38, R.drawable.invitation_start_39, R.drawable.invitation_start_40
    };

    int successAnimationFile[] = {
            R.drawable.invitation_move_01, R.drawable.invitation_move_02, R.drawable.invitation_move_03, R.drawable.invitation_move_04, R.drawable.invitation_move_05,
            R.drawable.invitation_move_06, R.drawable.invitation_move_07, R.drawable.invitation_move_08, R.drawable.invitation_move_09, R.drawable.invitation_move_10,
            R.drawable.invitation_move_11, R.drawable.invitation_move_12, R.drawable.invitation_move_13,
            R.drawable.invitation_success_01, R.drawable.invitation_success_02, R.drawable.invitation_success_03, R.drawable.invitation_success_04, R.drawable.invitation_success_05,
            R.drawable.invitation_success_06, R.drawable.invitation_success_07, R.drawable.invitation_success_08, R.drawable.invitation_success_09, R.drawable.invitation_success_10,
            R.drawable.invitation_success_11, R.drawable.invitation_success_12, R.drawable.invitation_success_13, R.drawable.invitation_success_14, R.drawable.invitation_success_15,
            R.drawable.invitation_success_16, R.drawable.invitation_success_17, R.drawable.invitation_success_18, R.drawable.invitation_success_19, R.drawable.invitation_success_20,
            R.drawable.invitation_success_21, R.drawable.invitation_success_22, R.drawable.invitation_success_23, R.drawable.invitation_success_24, R.drawable.invitation_success_25,
            R.drawable.invitation_success_26, R.drawable.invitation_success_27, R.drawable.invitation_success_28, R.drawable.invitation_success_29, R.drawable.invitation_success_30,
            R.drawable.invitation_success_31, R.drawable.invitation_success_32, R.drawable.invitation_success_33, R.drawable.invitation_success_34, R.drawable.invitation_success_35
    };

    int failAnimationFile[] = {
            R.drawable.invitation_move_01, R.drawable.invitation_move_02, R.drawable.invitation_move_03, R.drawable.invitation_move_04, R.drawable.invitation_move_05,
            R.drawable.invitation_move_06, R.drawable.invitation_move_07, R.drawable.invitation_move_08, R.drawable.invitation_move_09, R.drawable.invitation_move_10,
            R.drawable.invitation_move_11, R.drawable.invitation_move_12, R.drawable.invitation_move_13,
            R.drawable.invitation_fail_01, R.drawable.invitation_fail_02, R.drawable.invitation_fail_03, R.drawable.invitation_fail_04, R.drawable.invitation_fail_05,
            R.drawable.invitation_fail_06, R.drawable.invitation_fail_07, R.drawable.invitation_fail_08, R.drawable.invitation_fail_09, R.drawable.invitation_fail_10,
            R.drawable.invitation_fail_11, R.drawable.invitation_fail_12, R.drawable.invitation_fail_13, R.drawable.invitation_fail_14, R.drawable.invitation_fail_15,
            R.drawable.invitation_fail_16, R.drawable.invitation_fail_17, R.drawable.invitation_fail_18, R.drawable.invitation_fail_19, R.drawable.invitation_fail_20,
            R.drawable.invitation_fail_21, R.drawable.invitation_fail_22, R.drawable.invitation_fail_23, R.drawable.invitation_fail_24, R.drawable.invitation_fail_25,
            R.drawable.invitation_fail_26, R.drawable.invitation_fail_27, R.drawable.invitation_fail_28, R.drawable.invitation_fail_29, R.drawable.invitation_fail_30,
            R.drawable.invitation_fail_31, R.drawable.invitation_fail_32, R.drawable.invitation_fail_33, R.drawable.invitation_fail_34, R.drawable.invitation_fail_35,
            R.drawable.invitation_fail_36, R.drawable.invitation_fail_37, R.drawable.invitation_fail_38, R.drawable.invitation_fail_39, R.drawable.invitation_fail_40,
            R.drawable.invitation_fail_41, R.drawable.invitation_fail_42, R.drawable.invitation_fail_43, R.drawable.invitation_fail_44, R.drawable.invitation_fail_45,
            R.drawable.invitation_fail_46, R.drawable.invitation_fail_47, R.drawable.invitation_fail_48, R.drawable.invitation_fail_49, R.drawable.invitation_fail_50, 
            R.drawable.invitation_fail_51, R.drawable.invitation_fail_52, R.drawable.invitation_fail_53, R.drawable.invitation_fail_54, R.drawable.invitation_fail_55,
            R.drawable.invitation_fail_56, R.drawable.invitation_fail_57, R.drawable.invitation_fail_58, R.drawable.invitation_fail_59, R.drawable.invitation_fail_60,
            R.drawable.invitation_fail_61, R.drawable.invitation_fail_62, R.drawable.invitation_fail_63, R.drawable.invitation_fail_64, R.drawable.invitation_fail_65,
            R.drawable.invitation_fail_66 
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_invitation);
        this.imageView = (ImageView) super.findViewById(R.id.invitationImage);
        application = (MyApplication) getApplication();
        this.registerBroadcas();
        this.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(InvitationActivity.this.receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.ECLAIR) {
                event.startTracking();
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void InvitationOnClick(View view) {
        switch (view.getId()) {
            case R.id.sendInvitation:
                if (click) {
                    click = false;
                    imageView.setBackgroundResource(R.drawable.invitation);
                    sendInvitation();
                    send();
                }
                break;
            case R.id.receiveInvitation:
                if (click) {
                    click = false;
                    if (application.getInvSum() > 0) {
                        application.setInvSum(application.getInvSum() - 1);
                        receive();
                    } else {
                        Toast.makeText(InvitationActivity.this, "很抱歉您当前机会用完了╮(╯Д╰)╭", Toast.LENGTH_LONG).show();
                        click = true;
                    }
                }
                break;
        }
    }

    private void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; ++ i) {
                    try {
                        Thread.sleep(55);
                        Message message = new Message();
                        message.what = MyStatic.INVITATION_START;
                        message.arg1 = i;
                        InvitationActivity.this.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                click = true;
            }
        }).start();
    }
    
    private void info() {
        System.out.println("### Acccount_2" + account);
        Intent intent = new Intent(this,InvitationInfoActivity.class);
        intent.putExtra("Account", account);
        intent.putExtra("Name", name);
        intent.putExtra("Sex", sex);
        intent.putExtra("Portrait", portrait);
        startActivity(intent);
    }


    private void success() {
        if (application.getAccount().equals(account)) {
            fail();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 13 + 35; ++ i) {
                        try {
                            if (i < 13) {
                                Thread.sleep(100);
                            } else {
                                Thread.sleep(55);
                            }
                            Message message = new Message();
                            message.what = MyStatic.INVITATION_SUCCESS;
                            message.arg1 = i;
                            InvitationActivity.this.handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    click = true;
                    info();
                }
            }).start();
        }
    }

    private void fail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 13 + 66; ++ i) {
                    try {
                        if (i < 13) {
                            Thread.sleep(100);
                        } else {
                            Thread.sleep(55);
                        }
                        Message message = new Message();
                        message.what = MyStatic.INVITATION_FAIL;
                        message.arg1 = i;
                        InvitationActivity.this.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                click = true;
            }
        }).start();
    }

    private void sendInvitation(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 13 + 35 - 1; i >= 0; i --) {
                    try {
                        Thread.sleep(60);
                        Message message = new Message();
                        message.what = MyStatic.INVITATION_SUCCESS;
                        message.arg1 = i;
                        InvitationActivity.this.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 39; i >= 0; i --) {
                    try {
                        Thread.sleep(55);
                        Message message = new Message();
                        message.what = MyStatic.INVITATION_START;
                        message.arg1 = i;
                        InvitationActivity.this.handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                click = true;
            }
        }).start();
    }
    
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MyStatic.INVITATION_START:
                    int count_start = message.arg1;
                    imageView.setBackgroundResource(startAnimationFile[count_start]);
                    break;
                case MyStatic.INVITATION_SUCCESS:
                    int count_success = message.arg1;
                    imageView.setBackgroundResource(successAnimationFile[count_success]);
                    break;
                case MyStatic.INVITATION_FAIL:
                    int count_fail = message.arg1;
                    imageView.setBackgroundResource(failAnimationFile[count_fail]);
                    break;
            }
        }
    };

    private void send() {
        if(NetworkService.getInstance().getIsConnected()) {
            String sendInv = "type"+":"+Integer.toString(GlobalMsgUtils.msgSendInv)+":"+
                    "account"+":"+application.getAccount();
            Log.v("aaaaa", sendInv);
            NetworkService.getInstance().sendUpload(sendInv);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(InvitationActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
    }

    private void receive() {
        if(NetworkService.getInstance().getIsConnected()) {
            String reInv = "type"+":"+Integer.toString(GlobalMsgUtils.msgReceiveInv);
            Log.v("aaaaa", reInv);
            NetworkService.getInstance().sendUpload(reInv);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(InvitationActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
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
                if(intent.getBooleanExtra("reResult", true)){
                    account = intent.getStringExtra("reAccount");
                    name = intent.getStringExtra("reName");
                    sex = intent.getIntExtra("reGender",0);
                    portrait = intent.getStringExtra("rePhoto");
                    System.out.println("### Acccount" + account);
                    success();
                } else {
                    fail();
                }
            }
        }
    }
}