package com.android.decipherstranger.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.ChatMsgActivity;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.view.HandyTextView;

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
        initView();
        initData();
    }

    private void initData() {
        friendInfo = this.getIntent().getExtras();
        friendAccount.setText(friendInfo.getString("userAccount"));
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
}
