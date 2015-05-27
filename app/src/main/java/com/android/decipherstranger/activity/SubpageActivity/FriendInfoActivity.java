package com.android.decipherstranger.activity.SubpageActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.db.ChatRecord;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;

/**
 * Created by WangXin on 2015/4/19 0019.
 *
 **/
public class FriendInfoActivity extends BaseActivity {
    
    private ImageView friendPhoto;
    private ImageView friendSex;
    private TextView friendName;
    private TextView friendAccount;
    private TextView friendEmail;
    private TextView friendBirth;
    private TextView friendPhone;
    private Button sendMessage;
    private Button deleteFriend;

    private Bundle friendInfo;

    private final static String MALE = "0";

    /*
    *  Created by penghaitao 2015/05/13
    **/
    private SQLiteOpenHelper helper = null;
    private ConversationList conversationList = null;
    private ContactsList contactsList= null;
    private ChatRecord chatRecord = null;
    private String userAccount = null;
    private String userName = null;
    private Bitmap userPhoto = null;
    private MyApplication application = null;
    private ShowBroadcastReceiver receiver = null;
    private AlertDialog.Builder builder = null;
    private User presonalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);
        showBroadcas();
        initData();
        SendInf();
        application = (MyApplication) getApplication();
        initView();
        initListener();
    }

    @Override
    protected void onDestroy(){
        super.unregisterReceiver(FriendInfoActivity.this.receiver);
        super.onDestroy();
    }

    private void initData() {
        friendInfo = this.getIntent().getExtras();
        this.userAccount = friendInfo.getString("userAccount");
        this.userPhoto = friendInfo.getParcelable("userPhoto");
        this.userName = friendInfo.getString("userName");
        presonalInfo = new User();
        presonalInfo.setUserSex(friendInfo.getString("userSex"));
    }

    private void showInfo(){
        friendAccount.setText(userAccount);
        if (presonalInfo.getUserSex().equals(MALE)){
            friendSex.setImageResource(R.drawable.ic_sex_male);
        }else{
            friendSex.setImageResource(R.drawable.ic_sex_female);
        }
        Drawable drawable = new BitmapDrawable(getResources(),userPhoto);
        friendPhoto.setImageDrawable(drawable);
        friendName.setText(userName);
        friendEmail.setText(presonalInfo.getEmail());
        friendBirth.setText(presonalInfo.getBirth());
        friendPhone.setText(presonalInfo.getPhone());
    }

    private void initView() {
        this.helper = new DATABASE(this);
        friendPhoto = (ImageView) findViewById(R.id.friend_photo);
        friendSex = (ImageView) findViewById(R.id.friend_sex);
        friendName = (TextView) findViewById(R.id.friend_name);
        friendAccount = (TextView) findViewById(R.id.friend_account);
        friendEmail = (TextView) findViewById(R.id.friend_email);
        friendBirth = (TextView) findViewById(R.id.friend_birth);
        friendPhone = (TextView) findViewById(R.id.friend_phone);
        sendMessage = (Button) findViewById(R.id.send_message);
        deleteFriend = (Button) findViewById(R.id.delete_friend);
        builder =  new AlertDialog.Builder(FriendInfoActivity.this);
    }

    private void reFresh(){
        Intent intent = new Intent("com.android.decipherstranger.FRIEND");
        intent.putExtra("reFresh","reFresh");
        sendBroadcast(intent);
        Toast.makeText(this, "删除成功", Toast.LENGTH_LONG).show();
        onBackPressed();
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
                builder.setTitle("你确定要删除" + userName + "吗?");
                Drawable drawable = new BitmapDrawable(getResources(),userPhoto);
                builder.setIcon(drawable);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SendMsg();
                        chatRecord = new ChatRecord(helper.getWritableDatabase());
                        chatRecord.delete(userAccount);
                        contactsList = new ContactsList(helper.getWritableDatabase());
                        contactsList.delete(userAccount);
                        reFresh();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                builder.create().show();
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

    private void SendInf(){
        if(NetworkService.getInstance().getIsConnected()) {
            String showInf = "type"+":"+Integer.toString(GlobalMsgUtils.msgShowFri)+":"+
                             "account"+":"+userAccount;
            Log.v("aaaaa", showInf);
            NetworkService.getInstance().sendUpload(showInf);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(FriendInfoActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
    }

    private void showBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new ShowBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.SHOWFRI");
        this.registerReceiver(receiver, filter);
    }

    public class ShowBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.android.decipherstranger.SHOWFRI")) {
                    //Todo reEmail rePhone reBirth
                    presonalInfo.setEmail(intent.getStringExtra("reEmail"));
                    presonalInfo.setBirth(intent.getStringExtra("reBirth"));
                    presonalInfo.setPhone(intent.getStringExtra("rePhone"));
                    showInfo();
            }
        }
    }
}
