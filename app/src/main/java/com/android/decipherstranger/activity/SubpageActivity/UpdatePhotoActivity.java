package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.ImageCompression;
import com.android.decipherstranger.util.Tools;

import java.io.File;

/**
 * Created by WangXin on 2015/5/26 0026.
 */
public class UpdatePhotoActivity extends BaseActivity{

    private ImageView updatePhoto;
    private LinearLayout selectPhoto;
    private LinearLayout takePicture;
    private Button upLoade;
    private ImageButton updateBack;

    private String imageData;
    private String smallImageData;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_photo_activity);
        initView();
    }

    private void initView() {
        updatePhoto = (ImageView) findViewById(R.id.update_photo);
        selectPhoto = (LinearLayout) findViewById(R.id.update_select_photo);
        takePicture = (LinearLayout) findViewById(R.id.update_take_picture);
        updateBack = (ImageButton) findViewById(R.id.update_back_button);
        upLoade = (Button) findViewById(R.id.uploade);

        this.selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*");
                intentFromGallery
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery,
                        IMAGE_REQUEST_CODE);
            }
        });

        this.takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
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
        });

        updateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        this.upLoade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageData != null && smallImageData != null) {
                    //在这里调用上传照片的函数


                    onBackPressed();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                try {
                    try {
                        startPhotoZoom(data.getData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    Toast.makeText(UpdatePhotoActivity.this, "未找到存储卡，无法存储照片！",
                            Toast.LENGTH_LONG).show();
                }
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                   setImageToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

     public void setImageToView(Intent intent){
         Bundle bundle = intent.getExtras();
         if (bundle!=null){
             Bitmap bitmap = bundle.getParcelable("data");
             updatePhoto.setImageBitmap(bitmap);
             imageData = ChangeUtils.toBinary(bitmap);
             smallImageData = ChangeUtils.toBinary(ImageCompression.compressSimplify(bitmap, 0.3f));
         }
     }

}
