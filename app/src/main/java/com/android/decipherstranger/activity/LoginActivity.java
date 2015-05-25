package com.android.decipherstranger.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.MainPageActivity.ChatMsgActivity;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.UserTabOperate;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;
import com.android.decipherstranger.util.StringUtils;

/**
 * Created by PengHaitao on 2015/2/10.
 */
public class LoginActivity extends BaseActivity {

    private MyApplication application = null;
    private Intent it = null;
    private ArrayAdapter<String> adapter = null;
    private StringUtils stringUtils = null;
    private SQLiteOpenHelper helper = null;
    private UserTabOperate userInfo = null;
    private ContactsList contactCach = null;
    private LoginBroadcastReceiver receiver = null;
    private ProgressDialog progressDialog = null;

    private static final String FILENAME = "Login_CheckBox";
    private SharedPreferencesUtils sharedPreferencesUtils = null;

    private AutoCompleteTextView accountEdit = null;
    private EditText pawEdit = null;
    private Button loginButton = null;
    private CheckBox checkBox = null;
    private Button registerButton = null;
    private String account = null;
    private String passwordMD5 = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        application = (MyApplication) getApplication();
        this.helper = new DATABASE(this);
        this.sharedPreferencesUtils = new SharedPreferencesUtils(this, MyStatic.FILENAME_USER);
        initView();
        getCheckBox();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loginBroadcas();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(LoginActivity.this.receiver);
    }

    private void initView(){
        this.progressDialog = new ProgressDialog(LoginActivity.this);

        LoginActivity.this.userInfo = new UserTabOperate(LoginActivity.this.helper.getReadableDatabase());
        this.adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,LoginActivity.this.userInfo.accountInfo());

        this.accountEdit = (AutoCompleteTextView)super.findViewById(R.id.login_edit_account);
        this.accountEdit.setAdapter(adapter);
        this.pawEdit = (EditText)super.findViewById(R.id.login_edit_password);
        this.loginButton = (Button)super.findViewById(R.id.login_button);
        this.checkBox = (CheckBox)super.findViewById(R.id.auto_save_password);
        this.registerButton = (Button)super.findViewById(R.id.register_button);

        this.accountEdit.setOnItemClickListener(new OnItemClickListenerImpl());
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

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String userAccount = adapter.getItem(position);
            userInfo = new UserTabOperate(LoginActivity.this.helper.getReadableDatabase());
            User user = LoginActivity.this.userInfo.userTabInfo(userAccount);
            pawEdit.setText(user.getPassword());
        }
    }

    private class loginOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            
            account = LoginActivity.this.accountEdit.getText().toString();
            String password = LoginActivity.this.pawEdit.getText().toString();
            passwordMD5 = stringUtils.MD5(password);

            if (account.equals("")){
                Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            }else if (password.equals("")){
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            }else {
                progressDialog.setMessage("Login...");
                progressDialog.onStart();
                progressDialog.show();
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
                accountCheckByWeb(account, passwordMD5);
            }
        }
    }

    private class registerOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            it = new Intent(LoginActivity.this, test.class);
            startActivity(it);
            finish();
        }
    }
    /**
     * Created by Feng on 2015/3/24.
     */
    private void accountCheckByWeb(String account, String password){

        NetworkService.getInstance().closeConnection();
        NetworkService.getInstance().onInit(LoginActivity.this,application);
        NetworkService.getInstance().setupConnection();
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgLogin)+":"+"account"+":"+account+":"+"password"+":"+password;
            Log.v("aaaaa",userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
                    Log.v("Login", "已经执行T（）方法");
                }
            }, 3000);
        }

    }

    private void getCheckBox(){
        SharedPreferences shared = getSharedPreferences(FILENAME, LoginActivity.MODE_PRIVATE);
        LoginActivity.this.checkBox.setChecked(shared.getBoolean("Checked",true));
    }

    private void loginBroadcas() {
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
                    application.setAccount(account);
                    saveUserInfo();
                    progressDialog.dismiss();
                    Intent it = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(it);
                    finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    
    private void saveUserInfo() {
        sharedPreferencesUtils.set(MyStatic.USER_LOGIN, true);
        sharedPreferencesUtils.set(MyStatic.USER_ACCOUNT, account);
        sharedPreferencesUtils.set(MyStatic.USER_PASSWORD, passwordMD5);
        sharedPreferencesUtils.set(MyStatic.USER_NAME, application.getName());
        sharedPreferencesUtils.set(MyStatic.USER_PORTRAIT, ChangeUtils.toBinary(application.getPortrait()));
        sharedPreferencesUtils.set(MyStatic.USER_SEX, application.getSex());
        sharedPreferencesUtils.set(MyStatic.USER_BIRTH, application.getBirth());
        sharedPreferencesUtils.set(MyStatic.USER_EMAIL, application.getEmail());
        sharedPreferencesUtils.set(MyStatic.USER_PHONE, application.getPhone());
 //       sharedPreferencesUtils.set(MyStatic.USER_SIGNATURE, application.getSignature());
    }
}
