package com.android.decipherstranger.activity.ShakeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.WelcomeActivity;
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
public class ShakeActivity extends Activity{

    private ProgressDialog progressDialog = null;

    private ShakeListener shakeListener = null;

    private PopupWindow popupWindow = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        intiView();
    }

    private void intiView() {

        LayoutInflater inflater = LayoutInflater.from(ShakeActivity.this);
        View view = inflater.inflate(R.layout.shake_friend_popup, null);

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

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        MediaPlayer shakeEffect = MediaPlayer.create(this, R.raw.shake_effect); //  获取资源
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

        //创建我们的进度条
        progressDialog = new ProgressDialog(ShakeActivity.this);
        progressDialog.setMessage("正在搜寻同一时刻摇晃手机的人");
        progressDialog.onStart();
        LoadInfoTask loadInfoTask = new LoadInfoTask();
        loadInfoTask.execute();

    }

    private class LoadInfoTask extends AsyncTask<Intent,Intent,String> {

        //  onPreExecute方法在doInBackground
        //  用于在执行后台任务前做一些UI操作 
        @Override
        protected void onPreExecute() {
            try{
                Thread.sleep(1500);
            }catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.show();
        }

        //  doInBackground方法内部执行后台任务
        //  不可在此方法内修改UI
        @Override
        protected String doInBackground(Intent...params) {
            try{
                Thread.sleep(3000); //模拟获取数据
                //  TODO 上传Acccount、时间节点
                //  TODO 获取头像、昵称、性别、
                return "true";
            }catch (Exception e) {
                e.printStackTrace();
                return  "false";
            }
        }


        //  onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result.equals("true")) {
                popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
                popupWindow.showAsDropDown(findViewById(R.id.shake_image));
            } else {
                Toast.makeText(ShakeActivity.this, "很遗憾，当前没有同时摇晃手机的人", Toast.LENGTH_SHORT).show();
            }
        }

        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override
        protected void onCancelled() {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    public void shakeMain(View v) {
        switch (v.getId()) {
            case R.id.shake_back_button:
/*                onBackPressed();*/
                onVibrator();
                break;
            case R.id.shakeMain:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } break;
            case R.id.shake_imageButton: break;
        }
    }

    public void ShakePopup(View view) {
        switch (view.getId()) {
            case R.id.shake_friend_info:
                Intent intent = new Intent(ShakeActivity.this,WelcomeActivity.class);
                MyStatic.friendAccount = "我是小涛啊";   //  获取所加好友账号
                startActivity(intent);
                this.finish();
                break;
            case R.id.userPhoto:
                //  TODO 放大头像
                Toast.makeText(this,"userPhoto",Toast.LENGTH_SHORT);
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