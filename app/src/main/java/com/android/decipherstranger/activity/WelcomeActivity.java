package com.android.decipherstranger.activity;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by PengHaitao on 2015/2/10.
 */
public class WelcomeActivity extends ActionBarActivity {

    private Boolean isLogin = false;
    private MyApplication application = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getLoginFlag();
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                Intent intent = null;
                if (isLogin) {
                    intent = new Intent(WelcomeActivity.this, MainPageActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                WelcomeActivity.this.finish();//结束本Activity
            }
        }, 1000);
    }
    
    private void getLoginFlag() {
        this.application = (MyApplication) getApplication();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this, MyStatic.FILENAME_USER);
        this.isLogin = (Boolean) sharedPreferencesUtils.get(MyStatic.USER_LOGIN, false);
        if (isLogin) {
            this.application.setAccount((String) sharedPreferencesUtils.get(MyStatic.USER_ACCOUNT, null));
            this.application.setName((String) sharedPreferencesUtils.get(MyStatic.USER_NAME, null));
  //          this.application.setPortrait(ChangeUtils.toBitmap((String) sharedPreferencesUtils.get(MyStatic.USER_PORTRAIT, null)));
            this.application.setSex((String) sharedPreferencesUtils.get(MyStatic.USER_SEX, null));
            this.application.setBirth((String) sharedPreferencesUtils.get(MyStatic.USER_BIRTH, null));
            this.application.setEmail((String) sharedPreferencesUtils.get(MyStatic.USER_EMAIL, null));
            this.application.setPhone((String) sharedPreferencesUtils.get(MyStatic.USER_PHONE, null));
            this.application.setSignature((String) sharedPreferencesUtils.get(MyStatic.USER_SIGNATURE, null));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}