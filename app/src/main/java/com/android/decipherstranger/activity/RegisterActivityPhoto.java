package com.android.decipherstranger.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.decipherstranger.R;

import java.io.File;

import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.Tools;
import com.android.decipherstranger.view.HandyTextView;


/**
 * Created by Administrator on 2015/3/15 0015.
 */
public class RegisterActivityPhoto extends Activity {

    private HandyTextView test;
    private LinearLayout selectPhoto;
    private LinearLayout takePicture;
    private ImageView userPhoto;
    private User userInfo;
    private String portraitUrl;

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
        initView();

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
        userInfo.setPortrait(portraitUrl);
    }

    private void initView(){
        this.selectPhoto = (LinearLayout)super.findViewById(R.id.reg_photo_layout_selectphoto);
        this.takePicture = (LinearLayout)super.findViewById(R.id.reg_photo_layout_takepicture);
        this.userPhoto = (ImageView)super.findViewById(R.id.reg_photo_iv_userphoto);
        this.previousStepButton = (Button)super.findViewById(R.id.previous_step);
        this.registerButton = (Button)super.findViewById(R.id.register_btn);
        this.test = (HandyTextView) findViewById(R.id.test);

        try {
            test.setText(userInfo.getAccount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.selectPhoto.setOnClickListener(new selectPhotoOnClickListenerImpl());
        this.takePicture.setOnClickListener(new takePictureOnClickListenerImpl());
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
    public class previousStepButtonOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){

        }
    }
    public class registerButtonOnClickListenerImpl implements View.OnClickListener{
        @Override
        public void onClick(View view){
            initData();
            test.setText(userInfo.getPortrait());
        }
    }

    @Override
       protected void onActivityResult( int requestCode, int resultCode, Intent data){
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
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
            ChangeUtils changeUtils = new ChangeUtils();
            portraitUrl = changeUtils.toBinary(photo);
            Bitmap p = changeUtils.toBitmap(portraitUrl);
            Drawable drawable = new BitmapDrawable(this.getResources(),p);
            userPhoto.setImageDrawable(drawable);
        }
    }
}
