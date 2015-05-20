package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.GameOneActivity.WelcomeRspActivity;
import com.android.decipherstranger.util.ChangeUtils;


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
 * Created by WangXin on 2015/5/20 0020.
 */
public class NearbyInfoActivity extends BaseActivity {
    private ImageView photo;
    private TextView name;
    private ImageView sex;
    private Button addFriend;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_info);
        initView();
    }

    private void initView() {
        photo = (ImageView) findViewById(R.id.nearby_info_photo);
        name = (TextView) findViewById(R.id.nearby_info_name);
        sex = (ImageView) findViewById(R.id.nearby_info_sex);
        addFriend = (Button) findViewById(R.id.nearby_add_friend);
        back = (Button) findViewById(R.id.nearby_info_back);
        photo.setImageBitmap(ChangeUtils.toBitmap(getIntent().getStringExtra("photo")));
        name.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("sex").equals("0")){
            sex.setImageResource(R.drawable.ic_sex_male);
        }else {
            sex.setImageResource(R.drawable.ic_sex_female);
        }
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyInfoActivity.this,WelcomeRspActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyInfoActivity.this,NearbyListViewActivity.class);
                startActivity(intent);
            }
        });
    }
}

