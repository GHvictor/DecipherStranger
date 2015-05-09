package com.android.decipherstranger.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.UserTabOperate;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.StringUtils;

/**
 * Created by PengHaitao on 2015/2/10.
 */
public class LoginActivity extends Activity {

    private MyApplication application = null;
    private Intent it = null;
    private StringUtils stringUtils = null;
    private SQLiteOpenHelper helper = null;
    private UserTabOperate userInfo = null;
    private LoginBroadcastReceiver receiver = null;

    private static final String FILENAME = "Login_CheckBox";

    private ImageView imageView = null;
    private AutoCompleteTextView accountEdit = null;
    private EditText pawEdit = null;
    private Button loginButton = null;
    private CheckBox checkBox = null;
    private Button registerButton = null;
    private String account = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBroadcas();
        application = (MyApplication) getApplication();
        this.helper = new DATABASE(this);
        initView();
        getCheckBox();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(LoginActivity.this.receiver);
    }

    private void initView(){
        this.imageView = (ImageView)super.findViewById(R.id.login_image);
        Bitmap bitmap = BitmapFactory.decodeFile("mypic.png");
        this.imageView.setImageBitmap(bitmap);

        LoginActivity.this.userInfo = new UserTabOperate(LoginActivity.this.helper.getReadableDatabase());
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,LoginActivity.this.userInfo.accountInfo());

        this.accountEdit = (AutoCompleteTextView)super.findViewById(R.id.login_edit_account);
        this.accountEdit.setAdapter(adapter);
        this.pawEdit = (EditText)super.findViewById(R.id.login_edit_password);
        this.loginButton = (Button)super.findViewById(R.id.login_button);
        this.checkBox = (CheckBox)super.findViewById(R.id.auto_save_password);
        this.registerButton = (Button)super.findViewById(R.id.register_button);

        this.accountEdit.setAdapter(adapter);
        this.accountEdit.setOnFocusChangeListener(new passwordOnFocusChangeListenerImpl());
        this.accountEdit.setOnClickListener(new accountOnClickListenerImpl());
        this.pawEdit.setOnClickListener(new passwordOnClickListenerImpl());
        this.loginButton.setOnClickListener(new loginOnClickListenerImpl());
        this.registerButton.setOnClickListener(new registerOnClickListenerImpl());
    }

    private class accountOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            LoginActivity.this.accountEdit.setText("");
        }
    }

    private class passwordOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            LoginActivity.this.pawEdit.setText("");
        }
    }

    private class passwordOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == LoginActivity.this.accountEdit.getId()){
                if (!focus){
                    String account = LoginActivity.this.accountEdit.getText().toString();
                    LoginActivity.this.userInfo = new UserTabOperate(LoginActivity.this.helper.getReadableDatabase());
                    User user = LoginActivity.this.userInfo.userTabInfo(account);
                    LoginActivity.this.pawEdit.setText(user.getPassword());
                }
            }
        }
    }

    private class loginOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            account = LoginActivity.this.accountEdit.getText().toString();
            String password = LoginActivity.this.pawEdit.getText().toString();
            String passwordMD5 = stringUtils.MD5(password);

            if (account.equals("")){
                Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            }else if (password.equals("")){
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }else {
                LoginActivity.this.userInfo = new UserTabOperate(LoginActivity.this.helper.getReadableDatabase());
                User user = LoginActivity.this.userInfo.userTabInfo(account);

                LoginActivity.this.userInfo = new UserTabOperate(LoginActivity.this.helper.getWritableDatabase());
                SharedPreferences share = getSharedPreferences(FILENAME, LoginActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();

                if (LoginActivity.this.checkBox.isChecked()) {
                    editor.putBoolean("Checked",true);
                    if (!user.getAccount().equals("")) {
                        LoginActivity.this.userInfo.update(account, password);
                    }else {
                        LoginActivity.this.userInfo.insert(account, password);
                    }
                }else {
                    editor.putBoolean("Checked",false);
                    if (!user.getAccount().equals("")) {
                        LoginActivity.this.userInfo.update(account, "");
                    }else {
                        LoginActivity.this.userInfo.insert(account, "");
                    }
                }
                editor.commit();
            }
            accountCheckByWeb(account, passwordMD5);
            Intent it = new Intent(LoginActivity.this,MainPageActivity.class);
            startActivity(it);
            finish();
        }
    }

    private class registerOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            it = new Intent(LoginActivity.this, RegisterActivityBase.class);
            startActivity(it);
        }
    }
    /**
     * Created by Feng on 2015/3/24.
     */
    private void accountCheckByWeb(String account, String password){

        NetworkService.getInstance().closeConnection();
        NetworkService.getInstance().onInit(LoginActivity.this);
        NetworkService.getInstance().setupConnection();
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgLogin)+":"+"account"+":"+account+":"+"password"+":"+password;
            Log.v("aaaaa",userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(LoginActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }

    }

    private void getCheckBox(){
        SharedPreferences shared = getSharedPreferences(FILENAME, LoginActivity.MODE_PRIVATE);
        LoginActivity.this.checkBox.setChecked(shared.getBoolean("Checked",true));
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new LoginBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.LOGIN");
        this.registerReceiver(receiver, filter);
    }

    public class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.LOGIN")) {
                if(intent.getStringExtra("result").equals(MyStatic.resultTrue)) {
                    //MyStatic.UserName = intent.putExtra("name");
                    application.setAccount(account);
                    Intent it = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(context, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
