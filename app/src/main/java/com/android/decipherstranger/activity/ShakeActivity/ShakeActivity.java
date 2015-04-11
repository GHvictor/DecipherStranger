package com.android.decipherstranger.activity.ShakeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.WelcomeActivity;
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
public class ShakeActivity extends Activity implements View.OnClickListener{

    private Intent intent = null;
    ShakeListener shakeListener = null;

    private Button backButton = null;
    private ImageButton settingsImageButton = null;
    private PopupWindow popupWindow = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        intiView();
    }

    private void intiView() {
        this.backButton = (Button) super.findViewById(R.id.shake_back_button);
        this.settingsImageButton = (ImageButton) super.findViewById(R.id.shake_imageButton);
        
        this.backButton.setOnClickListener(this);
        this.settingsImageButton.setOnClickListener(this);
        
        LayoutInflater inflater = LayoutInflater.from(ShakeActivity.this);
        View view = inflater.inflate(R.layout.shake_friend_popup, null);

        LinearLayout userInfo = (LinearLayout) view.findViewById(R.id.shake_friend_show);
        ImageButton userPhoto = (ImageButton) view.findViewById(R.id.userPhoto);

        userInfo.setOnClickListener(this);
        userPhoto.setOnClickListener(this);

        this.popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // 回调接口
        this.shakeListener = new ShakeListener(this);  // 创建一个对象
        this.shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {// 调用setOnShakeListener方法进行监听
            public void onShake() {
                // 对手机摇晃后的处理（如换歌曲，换图片，震动……）
                onVibrator();
            }
        });

    }
    
    private void onVibrator() {

        MediaPlayer shakeEffect = MediaPlayer.create(this,R.raw.shake_effect); //  获取资源
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // 获取振动器Vibrator实例
        if (vibrator == null) {
            Vibrator localVibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator = localVibrator;
        }
        vibrator.vibrate(500L);
        shakeEffect.start();
        searchFriend(); //  搜索同时参与摇一摇的好友
    }
    
    private void searchFriend() {
/*        JsonData.setJsonObj_send("type" + "-" + "Shake" + "-" + "account" + "-" + "此处放用户account");
        new Task_Socket_Cloud().execute();*/

        //显示弹窗
        popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
        popupWindow.showAsDropDown(findViewById(R.id.shake_image));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shake_friend_show:
                intent = new Intent(ShakeActivity.this,WelcomeActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.userPhoto:
                break;
            case R.id.shake_back_button:
                onBackPressed();
                break;
            case R.id.shake_imageButton:
                intent = new Intent(ShakeActivity.this,ShakeSettingsActivity.class);
                startActivity(intent);
                break;
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //这你写你的返回处理
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            } else {
                onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}