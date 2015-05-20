package com.android.decipherstranger.activity.MainPageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.activity.SubpageActivity.UpdateNameActivity;
import com.android.decipherstranger.db.ChatRecord;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Map;

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
 * @Date 2015/5/6 21:39
 * @e-mail 785351408@qq.com
 */
public class UserPageActivity extends BaseActivity {
    
    private MyApplication application = null;
    private UserBroadcastReceiver receiver = null;
    private ImageView PopPortrait = null;

    private SQLiteOpenHelper helper = null;
    private ChatRecord chatRecord;
    private ContactsList contacts;
    private ConversationList conversationList;
    
    private ImageView portraitImage = null;
    private TextView nameText = null;
    private TextView accountText = null;
    private TextView sexText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.helper = new DATABASE(this);
        super.setContentView(R.layout.activity_main_user);
        this.registerBroadcas();
        this.init();
    }
    
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            System.out.println("### 这样是对的 4");
            return true;
        }
        System.out.println("### TMD 这样是对的 4");
        return super.onKeyDown(keyCode, event);
    }
    
    private void init() {
        this.application = (MyApplication) getApplication();

        this.portraitImage = (ImageView) super.findViewById(R.id.portraitImage);
        this.nameText = (TextView) super.findViewById(R.id.nameText);
        this.accountText = (TextView) super.findViewById(R.id.accountText);
        this.sexText = (TextView) super.findViewById(R.id.sexText);

        portraitImage.setImageBitmap(application.getPortrait());
        this.nameText.setText(application.getName());
        this.accountText.setText(application.getAccount());
        this.sexText.setText(application.getSex());
    }
    
    public void MePageOnClick(View view) {
        switch (view.getId()) {
            case R.id.myPortrait:
                // 从本地文档获取图片并上传
                break;
            case R.id.portraitImage:
                //  放大头像
                break;
            case R.id.myName:
                Intent intent = new Intent(this, UpdateNameActivity.class);
                startActivity(intent);
                break;
            case R.id.button:
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,MyStatic.FILENAME_USER);
                sharedPreferencesUtils.clear();
                sharedPreferencesUtils.set(MyStatic.USER_LOGIN, false);
                chatRecord = new ChatRecord(this.helper.getWritableDatabase());
                chatRecord.clear();
                contacts = new ContactsList(this.helper.getWritableDatabase());
                contacts.clear();
                conversationList = new ConversationList(this.helper.getWritableDatabase());
                conversationList.clear();
                MyApplication.getInstance().exit();
                this.finish();
                break;
        }
    }


    private void registerBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new UserBroadcastReceiver();
        filter.addAction(MyStatic.USER_BOARD);
        this.registerReceiver(receiver, filter);
    }

    public class UserBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            init();
        }
    }
}
