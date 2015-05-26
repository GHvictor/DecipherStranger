package com.android.decipherstranger.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;

import java.io.File;

import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.MainPageActivity.MainPageActivity;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;

import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.ImageCompression;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.StringUtils;
import com.android.decipherstranger.util.Tools;


/**
 * Created by Administrator on 2015/3/15 0015.
 */
public class RegisterActivityPhoto extends BaseActivity {

    private LinearLayout selectPhoto;
    private LinearLayout takePicture;
    private ImageView userPhoto;
    private User userInfo;
    private String portraitUrl;
    private String sPortaitUrl;
    private RegisterBroadcastReceiver receiver = null;

    private ImageButton backButton = null;
    private Button previousStepButton;
    private Button registerButton;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_photo);
        registerBroadcas();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(RegisterActivityPhoto.this.receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //这你写你的返回处理
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initData() {
        Intent  intent = getIntent();
        userInfo = new User();
        userInfo.setAccount(intent.getStringExtra("account"));
        userInfo.setPassword(intent.getStringExtra("possword"));
        userInfo.setUsername(intent.getStringExtra("name"));
        userInfo.setUserSex(intent.getStringExtra("sex"));
        userInfo.setEmail(intent.getStringExtra("email"));
        userInfo.setPhone(intent.getStringExtra("phone"));
        userInfo.setBirth(intent.getStringExtra("birth"));
    }

    private void initView(){
        this.selectPhoto = (LinearLayout)super.findViewById(R.id.reg_photo_layout_selectphoto);
        this.takePicture = (LinearLayout)super.findViewById(R.id.reg_photo_layout_takepicture);
        this.userPhoto = (ImageView)super.findViewById(R.id.reg_photo_iv_userphoto);
        this.backButton = (ImageButton) super.findViewById(R.id.register_back_button);
        this.previousStepButton = (Button)super.findViewById(R.id.previous_step);
        this.registerButton = (Button)super.findViewById(R.id.register_btn);

        this.selectPhoto.setOnClickListener(new selectPhotoOnClickListenerImpl());
        this.takePicture.setOnClickListener(new takePictureOnClickListenerImpl());
        this.backButton.setOnClickListener(new backOnClickListenerImpl());
        this.previousStepButton.setOnClickListener(new previousStepButtonOnClickListenerImpl());
        this.registerButton.setOnClickListener(new registerButtonOnClickListenerImpl());
    }

    public class selectPhotoOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intentFromGallery = new Intent();
            intentFromGallery.setType("image/*"); // 设置文件类型
            intentFromGallery
                    .setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentFromGallery,
                    IMAGE_REQUEST_CODE);
        }
    }

    public class takePictureOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (Tools.hasSdcard()) {
                    File path = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file = new File(path, IMAGE_FILE_NAME);
                    intentFromCapture.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(file));
                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);
            }
        }
    }
    
    public class backOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(RegisterActivityPhoto.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            RegisterActivityPhoto.this.finish();
        }
    }
    
    public class previousStepButtonOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){
            onBackPressed();
        }
    }
    
    public class registerButtonOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){
            initData();

            NetworkService.getInstance().closeConnection();
            NetworkService.getInstance().onInit(RegisterActivityPhoto.this);
            NetworkService.getInstance().setupConnection();
            int userGender = 1;
            if(NetworkService.getInstance().getIsConnected()) {
                if (userInfo.getUserSex().equals("男")){
                    userGender = 1;
                }
                else
                    userGender = 0;
                StringUtils stringUtils = null;
                String sendInfo= "type"+":"+Integer.toString(GlobalMsgUtils.msgRegister)+":"+
                        "account"+":"+userInfo.getAccount()+":"+
                        "password"+":"+stringUtils.MD5(userInfo.getPassword())+":"+
                        "name"+":"+userInfo.getUsername()+":"+
                        "sex"+":"+userGender+":"+
                        "email"+":"+userInfo.getEmail()+":"+
                        "phone"+":"+userInfo.getPhone()+":"+
                        "birth"+":"+userInfo.getBirth()+":"+
                        "photo"+":"+portraitUrl+":"+
                        "sphoto"+":"+sPortaitUrl;
                Log.v("aaaaa", sendInfo);
                NetworkService.getInstance().sendUpload(sendInfo);
            }
            else {
                NetworkService.getInstance().closeConnection();
                Toast.makeText(RegisterActivityPhoto.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
                Log.v("Login", "已经执行T（）方法");
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                try {
                    startPhotoZoom(data.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (Tools.hasSdcard()) {
                    File path = Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File tempFile = new File(path, IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(RegisterActivityPhoto.this, "未找到存储卡，无法存储照片！",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    getImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            portraitUrl = ChangeUtils.toBinary(photo);
            sPortaitUrl = ChangeUtils.toBinary(ImageCompression.compressSimplify(photo, 0.3f));
            Drawable drawable = new BitmapDrawable(this.getResources(), photo);
            userPhoto.setImageDrawable(drawable);
        }
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new RegisterBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.REGISTER");
        this.registerReceiver(receiver, filter);
    }

    public class RegisterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.REGISTER")) {
                if(intent.getStringExtra("result").equals(MyStatic.resultTrue)) {
                    Intent it = new Intent(RegisterActivityPhoto.this, LoginActivity.class);
                    startActivity(it);
                    finish();
                }
                else{
                    Toast.makeText(context, "账号相同啦=_=", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
