package com.android.decipherstranger.activity.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.MainPage;

/**
 * Created by acmer on 2015/3/20.
 */
public class ErrorActivity extends Activity {

    /*    重写返回键，返回主界面*/

    private TextView textView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_error);
        initView();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){// 防止连续两次返回键
            //这你写你的返回处理
            Intent it = new Intent(ErrorActivity.this,MainPage.class);
            startActivity(it);
            ErrorActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView(){
        this.textView = (TextView)super.findViewById(R.id.textView);
    }

}
