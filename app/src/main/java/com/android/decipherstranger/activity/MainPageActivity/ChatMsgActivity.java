package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.view.AudioRecorderButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    //写入最近聊天列表缓存
    private ConversationList conversationList;
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
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(ChatMsgActivity.this.receiver);
    }

    public void initView() {
        Bundle bundle =this.getIntent().getExtras();
        currentUserAccount = bundle.getString("userAccount");
        currentUserName = bundle.getString("userName");
        currentUserPhoto = bundle.getParcelable("userPhoto");
        mListView = (ListView) findViewById(R.id.chat_listview);
        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                Contacts recorderMessage = new Contacts();
                recorderMessage.setAccount(currentUserAccount);
                recorderMessage.setUsername(application.getName());
                recorderMessage.setDatetime(getDate());
                recorderMessage.setWho(SEND_TO_MSG);
                recorderMessage.setTimeLen(Math.round(seconds) + "");
                recorderMessage.setMessage(filePath);
                mDataArrays.add(recorderMessage);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mListView.getCount() - 1);
                //将聊天记录写入本地
                writeChatLog = new ChatRecord(helper.getWritableDatabase());
                writeChatLog.insert(currentUserAccount, SEND_TO_MSG, filePath, seconds + "\"");
                sendVoice(filePath, Math.round(seconds));
//                writeRecentLog.update(currentUserAccount,currentUserName,currentUserPhoto,"[语音]");
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

        //发送照片相关
        add_panel_im.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addPanel();
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
        }
    }

    private void addPanel() {
        panel_add_rl.setVisibility(View.VISIBLE);
        select_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        take_picture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            conversationList = new ConversationList(helper.getWritableDatabase());
            conversationList.setMessage(currentUserAccount, contString);
            
            Intent intent = new Intent(MyStatic.CONVERSATION_UPDATE);
            intent.putExtra(MyStatic.CONVERSATION_ACCOUNT, currentUserAccount);
            sendBroadcast(intent);
            
            Contacts entity = new Contacts();
            entity.setDatetime(getDate());
            entity.setUsername(application.getName());
            entity.setWho(SEND_TO_MSG);
            entity.setPortrait(application.getPortrait());
            entity.setMessage(contString);
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            this.writeChatLog = new ChatRecord(this.helper.getWritableDatabase());
            writeChatLog.insert(currentUserAccount, SEND_TO_MSG, contString, "");
            mEditTextContent.setText("");
            mListView.setSelection(mListView.getCount() - 1);
            sendMessage(contString);
//            writeRecentLog.update(currentUserAccount,currentUserName,currentUserPhoto,contString);
        }
    }

    private String getDate() {
//        Calendar c = Calendar.getInstance();

//        String year = String.valueOf(c.get(Calendar.YEAR));
//        String month = String.valueOf(c.get(Calendar.MONTH));
//        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//        String mins = String.valueOf(c.get(Calendar.MINUTE));
//
//        StringBuffer sbBuffer = new StringBuffer();
//        sbBuffer.append(year + "-" + month + "-" + day + "-" + hour + "-"
//                + mins);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");//可以方便地修改日期格式


        String hehe = dateFormat.format( now );

        return hehe;
    }
    private void sendMessage(String message){
        if(NetworkService.getInstance().getIsConnected()) {
            String msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgMessage)+":"+
                    "account"+":"+ application.getAccount()+":"+"re_account"+":"+currentUserAccount+
                    ":"+"message"+":"+message+":"+"date"+":"+getDate();
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

    private void sendVoice(String message, int time){
        if(NetworkService.getInstance().getIsConnected()) {
            String msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgVoice)+":"+
                    "account"+":"+ application.getAccount()+":"+"re_account"+":"+currentUserAccount+
                    ":"+"message"+":"+message+":"+"time"+":"+time+":"+"date"+":"+getDate();
            Log.v("aaaaa", msg);
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
            if (intent.getAction().equals("com.android.decipherstranger.MESSAGE")
                    &&intent.getStringExtra("rereSender")==currentUserAccount) {
                Contacts receiveMsg = new Contacts();
                if(intent.getBooleanExtra("isVoice", false)) {
                    //Todo 用来写语音接收处理
                    receiveMsg.setAccount(currentUserAccount);
                    receiveMsg.setUsername(currentUserName);
                    receiveMsg.setPortrait(currentUserPhoto);
                    receiveMsg.setMessage(intent.getStringExtra("reMessage"));
                    receiveMsg.setDatetime(intent.getStringExtra("reDate"));
                    receiveMsg.setTimeLen(intent.getStringExtra("reTime"));
                    receiveMsg.setWho(IS_COM_MSG);
                    writeChatLog = new ChatRecord(helper.getWritableDatabase());
                    writeChatLog.insert(currentUserAccount, IS_COM_MSG, intent.getStringExtra("reMessage"), intent.getStringExtra("reTime"));
                }
                else {
                    //Todo 用来写消息传送
                    Toast.makeText(context, intent.getStringExtra("reMessage"), Toast.LENGTH_LONG).show();
                    receiveMsg.setAccount(currentUserAccount);
                    receiveMsg.setUsername(currentUserName);
                    receiveMsg.setPortrait(currentUserPhoto);
                    receiveMsg.setMessage(intent.getStringExtra("reMessage"));
                    receiveMsg.setDatetime(intent.getStringExtra("reDate"));
                    receiveMsg.setWho(IS_COM_MSG);
                    writeChatLog = new ChatRecord(helper.getWritableDatabase());
                    writeChatLog.insert(currentUserAccount, IS_COM_MSG, intent.getStringExtra("reMessage"),"");
                }
                    mDataArrays.add(receiveMsg);

/*                writeRecentLog.update(currentUserAccount,currentUserName,currentUserPhoto,intent.getStringExtra("reMessage"));        penghaitao*/
                    if (mAdapter == null) {
                        mAdapter = new ChatMsgViewAdapter(ChatMsgActivity.this, mDataArrays);
                        mListView.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                        mListView.setSelection(mListView.getCount() - 1);
                    }
            } else {
                Toast.makeText(context, "这不是发给你的，小朋友！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
