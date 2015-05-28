package com.android.decipherstranger.activity.MainPageActivity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.activity.SubpageActivity.UpdateNameActivity;
import com.android.decipherstranger.activity.SubpageActivity.UpdatePhotoActivity;
import com.android.decipherstranger.db.ChatRecord;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
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

    private Switch moveSwitch = null;
    private Switch musicSwitch = null;

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        this.application = (MyApplication) getApplication();
        this.portraitImage = (ImageView) super.findViewById(R.id.portraitImage);
        this.nameText = (TextView) super.findViewById(R.id.nameText);
        this.accountText = (TextView) super.findViewById(R.id.accountText);
        this.sexText = (TextView) super.findViewById(R.id.sexText);
        this.musicSwitch = (Switch) super.findViewById(R.id.switch1);
        this.moveSwitch = (Switch) super.findViewById(R.id.switch2);

        portraitImage.setBackground(new BitmapDrawable(application.getPortrait()));
        this.nameText.setText(application.getName());
        this.accountText.setText(application.getAccount());
        this.sexText.setText(application.getSex());
        this.moveSwitch.setChecked(application.isMoveFlag());
        this.musicSwitch.setChecked(application.isMusicFlag());


        this.moveSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl1());
        this.musicSwitch.setOnCheckedChangeListener(new SwitchOnCheckedChangeListenerImpl2());
    }

    public void MePageOnClick(View view) {
        switch (view.getId()) {
            case R.id.myPortrait:
                Intent intent1 = new Intent (this, UpdatePhotoActivity.class);
                startActivity(intent1);
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
                this.finish();
                System.exit(0);
                break;
        }
    }


    private class SwitchOnCheckedChangeListenerImpl1 implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                application.setMoveFlag(true);
            } else {
                application.setMoveFlag(false);
            }
            save(0);
        }
    }

    private class SwitchOnCheckedChangeListenerImpl2 implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                application.setMusicFlag(true);
            } else {
                application.setMusicFlag(false);
            }
            save(1);
        }
    }

    private void save(int flag) {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,MyStatic.FILENAME_USER);
        switch (flag) {
            case 0:
                sharedPreferencesUtils.set(MyStatic.USER_MOVE, application.isMoveFlag());
                break;
            case 1:
                sharedPreferencesUtils.set(MyStatic.USER_MUSIC,application.isMusicFlag());
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
