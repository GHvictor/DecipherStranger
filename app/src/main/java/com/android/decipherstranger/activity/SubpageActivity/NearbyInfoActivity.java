package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.GameOneActivity.WelcomeRspActivity;


/**
 * Created by WangXin on 2015/5/20 0020.
 */
public class NearbyInfoActivity extends BaseActivity {
    private ImageView photo;
    private TextView name;
    private TextView sex;
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
        sex = (TextView) findViewById(R.id.nearby_info_sex);
        addFriend = (Button) findViewById(R.id.nearby_add_friend);
        back = (Button) findViewById(R.id.nearby_info_back);
        bitmap = getIntent().getParcelableExtra("photo");
        photo.setImageBitmap(bitmap);
        name.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("sex").equals("0")){
            sex.setText("女");
        }else {
            sex.setText("男");
        }
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyInfoActivity.this,WelcomeRspActivity.class);
                intent.putExtra("Type","AddFriend");
                intent.putExtra("Account",getIntent().getStringExtra("account"));
                intent.putExtra("Photo",bitmap);
                intent.putExtra("Name",getIntent().getStringExtra("name"));
                intent.putExtra("Sex",getIntent().getStringExtra("sex"));
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    private void back() {
        Intent intent = new Intent(NearbyInfoActivity.this,NearbyListViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        NearbyInfoActivity.this.finish();
    }
}

