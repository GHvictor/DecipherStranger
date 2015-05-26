package com.android.decipherstranger.activity.SubpageActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Animation.SwingAnimation;
import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.GameOneActivity.WelcomeRspActivity;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.ShakeListener;

/**
 *                 ┏┓　　　┏┓+ + 
 * 　　　　　 　┏┛┻━━━┛┻┓ + + 
 * 　　　　 　　┃　　　　　　　┃ 　 
 *　　　　　　　┃　　　━　　　┃ ++ + + +      
 *　　　　　　 ████━████ ┃+            
 *　　　　　　　┃　　　　　　　┃ +             
 *　　　　　　　┃　　　┻　　　┃               
 *　　　　　　　┃　　　　　　　┃ + + 
 *　　　　　　　┗━┓　　　┏━┛ 
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　 
 *　　　　　　　　　┃　　　┃ + + + + 
 *　　　　　　　　　┃　　　┃　　　　　　　神兽保佑　　　　 
 *　　　　　　　　　┃　　　┃ + 　　　　　　 
 *　　　　　　　　　┃　　　┃                    代码无BUG　
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　 
 *　　　　　　　　　┃　 　　┗━━━┓ + + 
 *　　　　　　　　　┃ 　　　　　　　┣┓ 
 *　　　　　　　　　┃ 　　　　　　　┏┛ 
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + + 
 *　　　　　　　　　　┃┫┫　┃┫┫ 
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + + 
 *          @Created by peng on 2015/3/23.
 */
public class ShakeActivity extends BaseActivity {

    private MyApplication application = null;
    private ProgressDialog progressDialog = null;
    private ShakeListener shakeListener = null;
    private ImageView imageView = null;
    private LayoutInflater inflater = null;
    private PopupWindow popupWindow = null;
    private PopupWindow PortraitWindow = null;
    private ShakeBroadcastReceiver receiver = null;
    private ImageView PopPortrait = null;
    private LinearLayout friendButton = null;
    private ImageButton portrait = null;
    private TextView userName = null;
    private ImageView sex = null;

    private String FriendAccount = null;
    private String FriendName = null;
    private Bitmap bitmap = null;
    private String FriendSex = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        application = (MyApplication) getApplication();
        intiView();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        this.registerBroadcas();
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.shakeListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.shakeListener.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.unregisterReceiver(ShakeActivity.this.receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //这你写你的返回处理
            if (PortraitWindow.isShowing()) {
                PortraitWindow.dismiss();
                return true;
            }else if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;
            }else if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            } else {
                if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.ECLAIR) {
                    event.startTracking();
                } else {
                    onBackPressed();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void intiView() {
        this.registerBroadcas();
        this.progressDialog = new ProgressDialog(ShakeActivity.this);
        this.imageView = (ImageView) super.findViewById(R.id.shake_image);
        this.inflater = LayoutInflater.from(ShakeActivity.this);
        View view = this.inflater.inflate(R.layout.shake_friend_popup, null);
        this.popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View viewPortrait = this.inflater.inflate(R.layout.activity_shake_portrait, null);
        this.PortraitWindow = new PopupWindow(viewPortrait, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        this.PopPortrait = (ImageView) viewPortrait.findViewById(R.id.imageView2);

        this.friendButton = (LinearLayout) view.findViewById(R.id.shake_friend_info);
        this.portrait = (ImageButton) view.findViewById(R.id.userPhoto);
        this.userName = (TextView) view.findViewById(R.id.userName);
        this.sex = (ImageView) view.findViewById(R.id.sexImage);
        // 回调接口
        this.shakeListener = new ShakeListener(this);  // 创建一个对象
        this.shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {// 调用setOnShakeListener方法进行监听
            public void onShake() {
                // 对手机摇晃后的处理（如换歌曲，换图片，震动……）
                onVibrator();
            }
        });
    }

    public void shakeMain(View v) {
        switch (v.getId()) {
            case R.id.shake_back_button:
                onBackPressed();
                break;
            case R.id.shakeMain:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } break;
        }
    }

    private void onVibrator() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        MediaPlayer shakeEffect = MediaPlayer.create(this, R.raw.shake_effect); //  获取资源
        this.startAnimation();
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // 获取振动器Vibrator实例
        if (vibrator == null) {
            Vibrator localVibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator = localVibrator;
        }
        vibrator.vibrate(2000L);
        shakeEffect.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                searchFriend(); //  搜索同时参与摇一摇的好友
            }
        }, 2000);
    }

    private void startAnimation() {
        SwingAnimation swingAnimation = new SwingAnimation(-40, 0.5f, 0.5f);
        swingAnimation.setDuration(1000);//设置动画持续时间 
        swingAnimation.setRepeatCount(1);
        imageView.startAnimation(swingAnimation);
    }

    private void searchFriend() {
        //创建我们的进度条
        progressDialog.setMessage("正在搜寻同一时刻摇晃手机的人");
        progressDialog.onStart();
        progressDialog.show();
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgShake)+":"+"account"+":"+application.getAccount();
            Log.v("aaaaa", userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(ShakeActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }

    public void ShakePopup(View view) {
        switch (view.getId()) {
            case R.id.shake_friend_info:
                Intent intent1 = new Intent(ShakeActivity.this,WelcomeRspActivity.class);
                intent1.putExtra("Type","AddFriend");
                intent1.putExtra("Account", FriendAccount);
                intent1.putExtra("Name", FriendName);
                intent1.putExtra("Photo", bitmap);
                intent1.putExtra("Sex",FriendSex);
                startActivity(intent1);
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                this.finish();
                break;
            case R.id.userPhoto:
                this.PopPortrait.setImageDrawable(portrait.getDrawable());
                this.PortraitWindow.showAsDropDown(findViewById(R.id.top));
                break;
            case R.id.imageView2:
                if (PortraitWindow.isShowing()) {
                    PortraitWindow.dismiss();
                }break;
        }
    }

    private void popInitView(Intent intent) {
        Drawable sexDrawable = null;
        this.bitmap = ChangeUtils.toBitmap(intent.getStringExtra("rePhoto"));
        this.portrait.setImageBitmap(bitmap);
        this.userName.setText(intent.getStringExtra("reName"));
        this.FriendSex = intent.getIntExtra("reGender", 1)+"";
        if (intent.getIntExtra("reGender", 1) == 1) {
            sexDrawable = getResources().getDrawable(R.drawable.man);
        } else {
            sexDrawable = getResources().getDrawable(R.drawable.women);
        }
        this.sex.setImageDrawable(sexDrawable);
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new ShakeBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.SHAKE");
        this.registerReceiver(receiver, filter);
    }

    public class ShakeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.SHAKE")) {
                if(intent.getBooleanExtra("reResult", false)) {
                    popInitView(intent);
                    FriendAccount = intent.getStringExtra("reAccount");
                    FriendName = intent.getStringExtra("reName");
                    progressDialog.dismiss();
                    popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
                    popupWindow.showAsDropDown(findViewById(R.id.shake_image));
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(ShakeActivity.this, "没摇到( ⊙ o ⊙ )！重新来过~~", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
