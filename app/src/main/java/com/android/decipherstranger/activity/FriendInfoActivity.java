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
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.view.HandyTextView;

/**
 * *
 * ¤Ø¡¡¡¡¡¡¡¡¡¡£¯|
 * ¡¡¡¡/£Ü7¡¡¡¡¡¡ ¡Ï£ß/
 * ¡¡ /¡¡©¦¡¡¡¡ £¯¡¡£¯
 * ¡¡©¦¡¡Z £ß,£¼¡¡£¯¡¡¡¡ /`©c
 * ¡¡©¦¡¡¡¡¡¡¡¡¡¡©c¡¡¡¡ /¡¡¡¡¡µ
 * ¡¡ Y¡¡¡¡¡¡¡¡¡¡`¡¡ /¡¡¡¡/
 * ¡¡?¡ñ¡¡?¡¡¡ñ¡¡¡¡??¡´¡¡¡¡/
 * ¡¡()¡¡ ¤Ø¡¡¡¡¡¡¡¡|¡¡£Ü¡´
 * ¡¡¡¡>? ?_¡¡ ¥£¡¡ ©¦ £¯£¯      È¥°É£¡
 * ¡¡ / ¤Ø¡¡¡¡ /¡¡?£¼| £Ü£Ü        ±È¿¨Çð~
 * ¡¡ ©c_?¡¡¡¡(_£¯¡¡ ©¦£¯£¯           ÏûÃð´úÂëBUG
 * ¡¡¡¡7¡¡¡¡¡¡¡¡¡¡¡¡¡¡|£¯
 * ¡¡¡¡£¾¨Dr£þ£þ`?¨D£ß
 * Created by WangXin on 2015/4/19 0019.
 */
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

    private User friendInfo;

    private ChangeUtils changeUtils;

    private final static String MALE = "ÄÐ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_info);
        initView();
        initData();
    }

    private void initData() {
        friendAccount.setText(friendInfo.getAccount());
        if (friendInfo.getUserSex() == MALE){
            friendSex.setImageResource(R.drawable.ic_sex_male);
        }else{
            friendSex.setImageResource(R.drawable.ic_sex_female);
        }
        changeUtils = new ChangeUtils();
        Bitmap photo = changeUtils.toBitmap(friendInfo.getPortraitUrl());
        friendPhoto.setImageBitmap(photo);
        friendName.setText(friendInfo.getUsername());
        friendAtavar.setText(friendInfo.getUsername());
        friendEmail.setText(friendInfo.getEmail());
        friendBirth.setText(friendInfo.getBirth());
        friendPhone.setText(friendInfo.getPhone());
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
                bundle.putString("userName",friendInfo.getUsername());
                bundle.putString("Account", friendInfo.getAccount());
                bundle.putString("UserPhoto", friendInfo.getPortraitUrl());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}
