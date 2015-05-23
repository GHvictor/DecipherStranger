package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.adapter.ChatMsgViewAdapter;
import com.android.decipherstranger.db.ChatRecord;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.ImageCompression;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.Tools;
import com.android.decipherstranger.view.AudioRecorderButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * Created by WangXin on 2015/4/12 0012.
 */
public class ChatMsgActivity extends BaseActivity implements OnClickListener {

    private MyApplication application = null;
    //文本信息发送按钮
    private Button mBtnSend;
    //窗口聊天好友备注（呢称）显示
    private TextView who;
    //聊天窗口返回按钮
    private Button mBtnBack;
    //文本信息编辑框༭
    private EditText mEditTextContent;
    private RelativeLayout mBottom;
    //聊天内容显示界面
    private ListView mListView;
    //录音按钮
    private AudioRecorderButton mAudioRecorderButton;
    //聊天功能显示界面显示适配器
    private ChatMsgViewAdapter mAdapter;
    //聊天记录内容和List
    private List<Contacts> mDataArrays = new ArrayList<Contacts>();
    private SQLiteOpenHelper helper = null;
    //读取本地缓存聊天记录
    private ChatRecord readerChatLog;
    //写入本地缓存聊天记录
    private ChatRecord writeChatLog;
//    //写入最近聊天列表缓存
//    private ConversationList conversationList;
    //图片信息发送按钮
    private ImageView add_panel_im;
    //图片偏选择面板
    private RelativeLayout panel_add_rl;
    //从相册选择图片发送
    private ImageView select_photo;
    //拍张发送
    private ImageView take_picture;
    //切换语音发送
    private ImageView chatting_mode_btn;
    private boolean btn_vocie = false;
    //当前聊天好友ID
    private String currentUserAccount;
    //当前聊天好友备注（呢称）
    private String currentUserName;
    //当前聊天好友头像
    private Bitmap currentUserPhoto;
    
    private RelativeLayout blank = null;

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private static final int TEXT_MESSAGE = 0;
    private static final int VOICE_MESSAGE = 1;
    private static final int PHOTO_MESSAGE = 2;

    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    private ChatBroadcastReceiver receiver = null;
    private static final int IS_COM_MSG = 1;
    private static final int SEND_TO_MSG = 0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        application = (MyApplication) getApplication();
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.helper = new DATABASE(this);
        chatBroadcas();
        initView();
        initData();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        sendToConversation(null);
    }

    @Override
    protected void onDestroy() {
        super.unregisterReceiver(ChatMsgActivity.this.receiver);
        super.onDestroy();
    }

    public void initView() {
        this.blank = (RelativeLayout) super.findViewById(R.id.blank);
        this.blank.setOnClickListener(this);
        Bundle bundle =this.getIntent().getExtras();
        currentUserAccount = bundle.getString("userAccount");
        currentUserName = bundle.getString("userName");
        currentUserPhoto = bundle.getParcelable("userPhoto");
        mListView = (ListView) findViewById(R.id.chat_listview);
        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                String time = getDate();
                Contacts recorderMessage = new Contacts();
                recorderMessage.setAccount(currentUserAccount);
                recorderMessage.setUsername(application.getName());
                recorderMessage.setPortrait(application.getPortrait());
                recorderMessage.setDatetime(time);
                recorderMessage.setWho(SEND_TO_MSG);
                recorderMessage.setType(VOICE_MESSAGE);
                recorderMessage.setMessage(filePath);
                recorderMessage.setTimeLen(Math.round(seconds) + "");
                File file = new File(filePath);
                mDataArrays.add(recorderMessage);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mListView.getCount() - 1);
                //将聊天记录写入本地
                writeChatLog = new ChatRecord(helper.getWritableDatabase());
                writeChatLog.insert(currentUserAccount, SEND_TO_MSG, filePath, Math.round(seconds)+ "", time,VOICE_MESSAGE);
                sendVoice(ChangeUtils.toBinary(file), Math.round(seconds), time);
                sendToConversation("[语音]");
            }
        });
        mBtnSend = (Button) findViewById(R.id.btn_send);
        who = (TextView) findViewById(R.id.who);
        mBtnSend.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBottom = (RelativeLayout) findViewById(R.id.btn_bottom);
        mBtnBack.setOnClickListener(this);
        chatting_mode_btn = (ImageView) this.findViewById(R.id.ivPopUp);
        add_panel_im = (ImageView) findViewById(R.id.panel_add_im);
        panel_add_rl = (RelativeLayout) findViewById(R.id.add_panel_rl);
        select_photo = (ImageView) findViewById(R.id.select_photo);
        take_picture = (ImageView) findViewById(R.id.take_picture);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
        mEditTextContent.setOnClickListener(this);

        //发送照片相关
        add_panel_im.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                addPanel();
                blank.setClickable(true);
            }
        });

        //语音文字切换按钮
        chatting_mode_btn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                panel_add_rl.setVisibility(View.GONE);
                if (btn_vocie) {
                    mAudioRecorderButton.setVisibility(View.GONE);
                    mBottom.setVisibility(View.VISIBLE);
                    add_panel_im.setVisibility(View.VISIBLE);
                    btn_vocie = false;
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_msg_btn);

                } else {
                    mAudioRecorderButton.setVisibility(View.VISIBLE);
                    add_panel_im.setVisibility(View.GONE);
                    mBottom.setVisibility(View.GONE);
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_voice_btn);
                    btn_vocie = true;
                }
            }
        });
    }


    public void initData() {
        who.setText(currentUserName);
        //获取本地聊天记录
        this.readerChatLog = new ChatRecord(this.helper.getReadableDatabase());
        mDataArrays = readerChatLog.getInfo(currentUserAccount);
        int length = mDataArrays.size();
        for (int i = 0; i < length; i++) {
            if (mDataArrays.get(i).getWho() == IS_COM_MSG){
                mDataArrays.get(i).setUsername(currentUserName);
                mDataArrays.get(i).setPortrait(currentUserPhoto);
            }
            else{
                mDataArrays.get(i).setUsername(application.getName());
                mDataArrays.get(i).setPortrait(application.getPortrait());
            }
        }
        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.blank:
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (panel_add_rl.isShown()) {
                    panel_add_rl.setVisibility(View.GONE);
                }
                blank.setClickable(false);
                break;
            case R.id.et_sendmessage:
                if (panel_add_rl.isShown()) {
                    panel_add_rl.setVisibility(View.GONE);
                }
                blank.setClickable(true);
                break;
        }
    }

    private void addPanel() {
        panel_add_rl.setVisibility(View.VISIBLE);
        select_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromGallery,
                        IMAGE_REQUEST_CODE);
            }
        });
        take_picture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(ChatMsgActivity.this, "未找到存储卡，无法存储照片！",
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

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras!=null){
            Bitmap photo = extras.getParcelable("data");
            Contacts entity = new Contacts();
            entity.setAccount(application.getAccount());
            entity.setUsername(application.getName());
            entity.setPortrait(application.getPortrait());
            entity.setWho(SEND_TO_MSG);
            entity.setType(PHOTO_MESSAGE);
            entity.setMessage(ChangeUtils.toBinary(ImageCompression.compressSimplify(photo, 0.3f)));
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(mListView.getCount() - 1);
            this.writeChatLog = new ChatRecord(this.helper.getWritableDatabase());
            writeChatLog.insert(currentUserAccount,SEND_TO_MSG,entity.getMessage(), "",
                    getDate(),entity.getType());
            sendMessage(GlobalMsgUtils.msgImage, entity.getMessage(), getDate());
            sendToConversation("[图片]");
        }
    }

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
    
    private void sendToConversation(String message) {
        Intent intent = new Intent(MyStatic.CONVERSATION_BOARD);
        intent.putExtra(MyStatic.CONVERSATION_TYPE, "Default");
        intent.putExtra(MyStatic.CONVERSATION_ACCOUNT, currentUserAccount);
        intent.putExtra(MyStatic.CONVERSATION_MESSAGE, message);
        sendBroadcast(intent); 
    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            String time = getDate();
            sendToConversation(contString);
            Contacts entity = new Contacts();
            entity.setDatetime(time);
            entity.setUsername(application.getName());
            entity.setWho(SEND_TO_MSG);
            entity.setPortrait(application.getPortrait());
            entity.setMessage(contString);
            entity.setType(TEXT_MESSAGE);
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            this.writeChatLog = new ChatRecord(this.helper.getWritableDatabase());
            writeChatLog.insert(currentUserAccount, SEND_TO_MSG, contString, "", time, TEXT_MESSAGE);
            mEditTextContent.setText("");
            mListView.setSelection(mListView.getCount() - 1);
            sendMessage(GlobalMsgUtils.msgMessage, contString, time);
        }
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.format(new java.util.Date());
        String time = dateFormat.format(new java.util.Date());
        return time;
    }
    public String getDir(){
        String dir = null;
        if (Tools.hasSdcard()){}
            dir = Environment.getExternalStorageDirectory()+"/JMMSH/voiceMsg";
        return dir;
    }

    public String getFileName(){
        return UUID.randomUUID().toString()+".amr";
    }

    private void sendMessage(int msgType, String message, String time){
        //Todo 图片传输调用例子 ChangeUtils.toBinary(ImageCompression.compressSimplify(photo, 0.3f));
        if(NetworkService.getInstance().getIsConnected()) {
            String msg = "type"+":"+Integer.toString(msgType)+":"+
                    "account"+":"+ application.getAccount()+":"+"re_account"+":"+currentUserAccount+
                    ":"+"message"+":"+message+":"+"date"+":"+time.replace(':', '-');
            Log.v("aaaaa", msg);
            System.out.println(msg);
            NetworkService.getInstance().sendUpload(msg);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(ChatMsgActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }

    private void sendVoice(String message, int time, String dataTime){
        if(NetworkService.getInstance().getIsConnected()) {
            String msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgVoice)+":"+
                    "account"+":"+ application.getAccount()+":"+"re_account"+":"+currentUserAccount+
                    ":"+"message"+":"+message+":"+"time"+":"+time+":"+"date"+":"+dataTime.replace(':', '-');
            Log.v("aaaaa", msg);
            System.out.println(msg);
            NetworkService.getInstance().sendUpload(msg);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(ChatMsgActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
    }


    private void chatBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new ChatBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.MESSAGE");
        this.registerReceiver(receiver, filter);
    }

    public class ChatBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Contacts receiveMsg = new Contacts();
            if (intent.getAction().equals("com.android.decipherstranger.MESSAGE")&&
                    intent.getStringExtra("reSender").equals(currentUserAccount)) {
//                sendToConversation(intent.getStringExtra("reMessage"));
                receiveMsg.setAccount(currentUserAccount);
                receiveMsg.setUsername(currentUserName);
                receiveMsg.setPortrait(currentUserPhoto);
                receiveMsg.setDatetime(getDate());
                receiveMsg.setWho(IS_COM_MSG);
                switch (intent.getIntExtra("msgType", 0)){
                    case TEXT_MESSAGE:
                        receiveMsg.setTimeLen("");
                        receiveMsg.setMessage(intent.getStringExtra("reMessage"));
                        receiveMsg.setType(TEXT_MESSAGE);
                        break;
                    case VOICE_MESSAGE:
                        receiveMsg.setTimeLen(intent.getStringExtra("reTime"));
                        File file = ChangeUtils.toFile(intent.getStringExtra("reMessage"),getDir(),getFileName());
                        receiveMsg.setMessage(file.getAbsolutePath());
                        receiveMsg.setType(VOICE_MESSAGE);
                        break;
                    case PHOTO_MESSAGE:
                        receiveMsg.setTimeLen("");
                        receiveMsg.setMessage(intent.getStringExtra("reMessage"));
                        receiveMsg.setType(PHOTO_MESSAGE);
                        break;
                }
                mDataArrays.add(receiveMsg);
                if (mAdapter == null) {
                    mAdapter = new ChatMsgViewAdapter(ChatMsgActivity.this, mDataArrays);
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mListView.getCount() - 1);
                }
                Intent it = new Intent(MyStatic.CONVERSATION_BOARD);
//                it.putExtra(MyStatic.CONVERSATION_TYPE, "Update");
//                it.putExtra(MyStatic.CONVERSATION_ACCOUNT, receiveMsg.getAccount());
//                it.putExtra(MyStatic.CONVERSATION_NAME, receiveMsg.getUsername());
//                it.putExtra(MyStatic.CONVERSATION_PORTRAIT, receiveMsg.getPortrait());
//                if (receiveMsg.getType() == TEXT_MESSAGE) {
//                    it.putExtra(MyStatic.CONVERSATION_MESSAGE, receiveMsg.getMessage());
//                }else if (receiveMsg.getType() == VOICE_MESSAGE){
//                    it.putExtra(MyStatic.CONVERSATION_MESSAGE, "[语音]");
//                }else {
//                    it.putExtra(MyStatic.CONVERSATION_MESSAGE,"[图片]");
//                }
//                sendBroadcast(it);
            }
        }
    }
}
