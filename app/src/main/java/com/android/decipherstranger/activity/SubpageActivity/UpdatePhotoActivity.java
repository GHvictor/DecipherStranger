package com.android.decipherstranger.activity.SubpageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.ImageCompression;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;
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
    private MyApplication application = null;
    private Bitmap myPhoto;
    private UpdateBroadcastReceiver receiver = null;
    private SharedPreferencesUtils sharedPreferencesUtils = null;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_photo_activity);
        initView();
        this.application = (MyApplication) getApplication();
        updateBroadcas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(receiver);
    }

    private void initView() {
        updatePhoto = (ImageView) findViewById(R.id.update_photo);
        selectPhoto = (LinearLayout) findViewById(R.id.update_select_photo);
        takePicture = (LinearLayout) findViewById(R.id.update_take_picture);
        updateBack = (ImageButton) findViewById(R.id.update_back_button);
        upLoade = (Button) findViewById(R.id.uploade);

        this.sharedPreferencesUtils = new SharedPreferencesUtils(this, MyStatic.FILENAME_USER);

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
                    updatePhoto();
                }else{
                    Toast.makeText(UpdatePhotoActivity.this, "图片不能为空呀", Toast.LENGTH_SHORT).show();
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

    public void setImageToView(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            updatePhoto.setImageBitmap(bitmap);
            imageData = ChangeUtils.toBinary(bitmap);
            smallImageData = ChangeUtils.toBinary(ImageCompression.compressSimplify(bitmap, 0.3f));
        }
    }
    private void updatePhoto() {
        // 上传至服务器
        if(NetworkService.getInstance().getIsConnected()) {
            String changeInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgChangeInf)+":"+
                    "account"+":"+application.getAccount()+":"+
                    "cphoto"+":"+imageData+":"+
                    "csphoto"+":"+smallImageData+":"+
                    "kind"+":"+"photo";
            Log.v("aaaaa", changeInfo);
            NetworkService.getInstance().sendUpload(changeInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(UpdatePhotoActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new UpdateBroadcastReceiver();
        filter.addAction("com.android.decipherstranger.CHANGE");
        this.registerReceiver(receiver, filter);
    }

    public class UpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("reResult", false)) {
                Toast.makeText(context, "修改成功了", Toast.LENGTH_SHORT).show();
                application.setPortrait(ChangeUtils.toBitmap(smallImageData));
                sharedPreferencesUtils.set(MyStatic.USER_PORTRAIT, smallImageData);
                Intent it = new Intent(MyStatic.USER_BOARD);
                sendBroadcast(it);
                onBackPressed();
            }else{
                Toast.makeText(context, "竟然没成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
