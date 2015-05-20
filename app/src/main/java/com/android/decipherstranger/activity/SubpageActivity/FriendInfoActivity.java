package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.MainPageActivity.ChatMsgActivity;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.view.HandyTextView;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/4/19 0019.
 *
 **/
public class FriendInfoActivity extends BaseActivity {
    
    private ImageView friendPhoto;
    private ImageView friendSex;
    private HandyTextView friendName;
    private HandyTextView friendAccount;
    private HandyTextView friendAtavar;
    private HandyTextView friendEmail;
    private HandyTextView friendBirth;
    private HandyTextView friendPhone;
    private Button sendMessage;
    private Button deleteFriend;

    private Bundle friendInfo;

    private final static String MALE = "男";

    /*
    *  Created by penghaitao 2015/05/13
    **/
    private SQLiteOpenHelper helper = null;
    private ConversationList conversationList = null;
    private String userAccount = null;
    private String userName = null;
    private Bitmap userPhoto = null;
    private MyApplication application = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);
        application = (MyApplication) getApplication();
        initView();
        initData();
        initListener();
    }

    private void initData() {
        friendInfo = this.getIntent().getExtras();
        this.userAccount = friendInfo.getString("userAccount");
        this.userPhoto = friendInfo.getParcelable("userPhoto");
        this.userName = friendInfo.getString("userName");
        friendAccount.setText(userAccount);
        if (friendInfo.getString("userSex") == MALE){
            friendSex.setImageResource(R.drawable.ic_sex_male);
        }else{
            friendSex.setImageResource(R.drawable.ic_sex_female);
        }
        Drawable drawable = new BitmapDrawable(getResources(),userPhoto);
        friendPhoto.setImageDrawable(drawable);
        friendName.setText(userName);
        friendAtavar.setText(friendInfo.getString("userAtavar"));
        friendEmail.setText(friendInfo.getString("userEmail"));
        friendBirth.setText(friendInfo.getString("userBirth"));
        friendPhone.setText(friendInfo.getString("userPhone"));
    }

    private void initView() {
        this.helper = new DATABASE(this);
        friendPhoto = (ImageView) findViewById(R.id.friend_photo);
        friendSex = (ImageView) findViewById(R.id.friend_sex);
        friendName = (HandyTextView) findViewById(R.id.friend_name);
        friendAccount = (HandyTextView) findViewById(R.id.friend_account);
        friendAtavar = (HandyTextView) findViewById(R.id.friend_atavar);
        friendEmail = (HandyTextView) findViewById(R.id.friend_email);
        friendBirth = (HandyTextView) findViewById(R.id.friend_birth);
        friendPhone = (HandyTextView) findViewById(R.id.friend_phone);
        sendMessage = (Button) findViewById(R.id.send_message);
        deleteFriend = (Button) findViewById(R.id.delete_friend);
    }
    
    private void initListener() {
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationList = new ConversationList(helper.getWritableDatabase());
                conversationList.create(userAccount, userName, userPhoto);
                Intent intent = new Intent(FriendInfoActivity.this, ChatMsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName",userName);
                bundle.putString("userAccount", userAccount);
                bundle.putParcelable("userPhoto", userPhoto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void SendMsg(){
        if(NetworkService.getInstance().getIsConnected()) {
            String delFri = "type"+":"+Integer.toString(GlobalMsgUtils.msgDelFri)+":"+
                            "account"+":"+application.getAccount()+":"+
                            "delaccount"+":"+userAccount;
            Log.v("aaaaa", delFri);
            NetworkService.getInstance().sendUpload(delFri);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(FriendInfoActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
    }
}
