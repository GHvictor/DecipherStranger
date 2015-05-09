package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.ChatMsgActivity;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.view.HandyTextView;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/4/19 0019.
 *
 **/
public class FriendInfoActivity extends Activity {
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

    private ChangeUtils changeUtils;

    private final static String MALE = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);
        //friendBroadcas();
        //networkRequest();
        initView();
        initData();
    }

    private void initData() {
        friendInfo = this.getIntent().getExtras();
        friendAccount.setText(friendInfo.getString("userAccount"));
        System.out.println("asdasd"+friendInfo.getString("userAccount"));
        if (friendInfo.getString("userSex") == MALE){
            friendSex.setImageResource(R.drawable.ic_sex_male);
        }else{
            friendSex.setImageResource(R.drawable.ic_sex_female);
        }
        changeUtils = new ChangeUtils();
        Bitmap photo = null;
        try {
            photo = changeUtils.toBitmap(friendInfo.getString("userPhoto"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        friendPhoto.setImageBitmap(photo);
        friendName.setText(friendInfo.getString("userName"));
        friendAtavar.setText(friendInfo.getString("userAtavar"));
        friendEmail.setText(friendInfo.getString("userEmail"));
        friendBirth.setText(friendInfo.getString("userBirth"));
        friendPhone.setText(friendInfo.getString("userPhone"));
    }

    private void initView() {
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

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendInfoActivity.this, ChatMsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName",friendInfo.getString("userName"));
                bundle.putString("userAccount", friendInfo.getString("userAccount"));
                bundle.putString("userPhoto", friendInfo.getString("userPhoto"));
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

    /*private void friendBroadcas() {
        //动态方式注册广播接收者
        FriendBroadcastReceiver receiver = new FriendBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.FRIEND");
        this.registerReceiver(receiver, filter);
    }

    public class FriendBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "行不行了", Toast.LENGTH_SHORT).show();
            if(intent.getAction().equals("com.android.decipherstranger.FRIEND")){
                if(intent.getBooleanExtra("reResult", false)) {
                    ArrayList<User> serverContactData = new ArrayList<>();

                    //Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show();
                    //serverContactData = ((ArrayList) intent.getSerializableExtra("friend"));
                    //Toast.makeText(context, serverContactData.get(0).getAccount().toString(),Toast.LENGTH_LONG).show();
                    //int sum = intent.getIntExtra("sum", 0);
                    *//*for (int i = 0; i < sum; i++) {
                        String s[] = new String[5];
                        s = intent.getStringExtra(Integer.toString(i)).split(":");
                        Bitmap bitmap = ChangeUtils.toBitmap(s[2]);
                        User user = new User();
                        user.setAccount(s[0]);
                        user.setUsername(s[1]);
                        user.setPortrait(bitmap);
                        serverContactData.add(user);
                        System.out.println("qqqqqqqq" + serverContactData.get(i).getAccount());
                    }*//*
                    System.out.println("wowowowo");
                    Toast.makeText(context, "成功了", Toast.LENGTH_LONG).show();
                *//*
                for(int i=0;i<serverContactData.size();i++){
                    SourceDateList.add(serverContactData.get(i));
                }
                if (adapter == null){
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter = new SortAdapter(getActivity(), SourceDateList);
                    sortListView.setAdapter(adapter);
                }else{
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter.updateListView(SourceDateList);
                }*//*
                }
                else{
                    System.out.println("aacxzzxc");
                    Toast.makeText(context, "第一次？", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(context, "bbbbbb", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
