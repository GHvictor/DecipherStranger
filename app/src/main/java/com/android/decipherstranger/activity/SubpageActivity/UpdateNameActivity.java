package com.android.decipherstranger.activity.SubpageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

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
 * @Date 2015/5/16 13:18
 * @e-mail 785351408@qq.com
 */
public class UpdateNameActivity extends BaseActivity {
    
    private EditText editText = null;
    private MyApplication application = null;

    private UpdateBroadcastReceiver receiver = null;
    private SharedPreferencesUtils sharedPreferencesUtils = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_user_update_name);
        this.registerBroadcas();
        this.init();
    }

    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(receiver);
    }
    
    private void init() {
        this.editText = (EditText) super.findViewById(R.id.nameText);
        this.application = (MyApplication) getApplication();

        this.sharedPreferencesUtils = new SharedPreferencesUtils(this, MyStatic.FILENAME_USER);
    }
    
    private void updateName() {
        // 上传至服务器
        if(NetworkService.getInstance().getIsConnected()) {
            String changeInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgChangeInf)+":"+
                              "account"+":"+application.getAccount()+":"+
                              "cname"+":"+this.editText.getText().toString();
            Log.v("aaaaa", changeInfo);
            NetworkService.getInstance().sendUpload(changeInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(UpdateNameActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void UpdateNameOnClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                updateName();
                onBackPressed();
                break;
            case R.id.updateName_back_button:
                onBackPressed();
        }
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new UpdateBroadcastReceiver();
        filter.addAction("com.android.decipherstranger.CHANGE");
        this.registerReceiver(receiver, filter);
    }

    public class UpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("reResult", false)) {
                application.setName(editText.getText().toString());
                sharedPreferencesUtils.set(MyStatic.USER_NAME, application.getName());
                Intent it = new Intent(MyStatic.USER_BOARD);
                sendBroadcast(it);
            }
        }
    }
}
