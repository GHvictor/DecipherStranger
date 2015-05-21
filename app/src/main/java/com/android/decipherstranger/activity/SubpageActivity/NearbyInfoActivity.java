package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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
 *
 * Created by WangXin on 2015/5/20 0020.
 */
public class NearbyInfoActivity extends BaseActivity {
    private ImageView photo;
    private TextView name;
    private ImageView sex;
    private Button addFriend;
    private Button back;
    private Bitmap bitmap = null;
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
        bitmap = ChangeUtils.toBitmap(getIntent().getStringExtra("photo"));
        photo.setImageBitmap(bitmap);
        name.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("sex").equals("0")){
            sex.setImageResource(R.drawable.ic_sex_female);
        }else {
            sex.setImageResource(R.drawable.ic_sex_male);
        }
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyInfoActivity.this,WelcomeRspActivity.class);
                intent.putExtra("Account",getIntent().getStringExtra("account"));
                intent.putExtra("photo",bitmap);
                intent.putExtra("Name",getIntent().getStringExtra("name"));
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

