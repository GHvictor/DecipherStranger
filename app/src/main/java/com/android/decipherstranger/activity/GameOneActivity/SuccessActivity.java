package com.android.decipherstranger.activity.GameOneActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.MainPageActivity.ContactsPageActivity;
import com.android.decipherstranger.activity.MainPageActivity.ConversationPageActivity;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;

/**
 * Created by acmer on 2015/3/20.
 */
public class SuccessActivity extends BaseActivity {

    private SQLiteOpenHelper helper = null;
    private ContactsList contactsList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_success);
        Intent intent = getIntent();
        String Type = intent.getStringExtra("Type");
        if(Type.equals("AddFriend")) {
            //Toast.makeText(this, "已添加" + MyStatic.friendName + "为好友！",Toast.LENGTH_LONG).show();
            this.helper = new DATABASE(this);
            this.SendToWeb();
            this.SendToLocal();
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.makefriend_success);
        mediaPlayer.start();
    }

    public void GameSuccessOnClick(View view) {
        Intent intent = new Intent(SuccessActivity.this, MainPageActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SuccessActivity.this.finish();
    }

    private void SendToWeb() {
        MyApplication application = (MyApplication) getApplication();
        if(NetworkService.getInstance().getIsConnected()){
            String addUser = "type"+":"+Integer.toString(GlobalMsgUtils.msgAddFriend)+":"+
                    "account"+":"+application.getAccount()+":"+
                    "friend"+":"+MyStatic.friendAccount;
            Log.v("aaaaa", addUser);
            NetworkService.getInstance().sendUpload(addUser);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(SuccessActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }

    private void SendToLocal() {
        contactsList = new ContactsList(this.helper.getWritableDatabase());
        User user = new User();
        user.setUsername(MyStatic.friendName);
        user.setAccount(MyStatic.friendAccount);
        user.setPortrait(MyStatic.friendPhoto);
        user.setUserSex(MyStatic.friendSex);
        contactsList.insert(user);
        Intent intent = new Intent("com.android.decipherstranger.FRIEND");
        intent.putExtra("reFresh","reFresh");
        sendBroadcast(intent);
    }

}
