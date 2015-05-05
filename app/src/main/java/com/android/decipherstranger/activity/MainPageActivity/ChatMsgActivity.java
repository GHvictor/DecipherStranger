package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.db.ChatRecord;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.RecentContacts;
import com.android.decipherstranger.entity.ChatMsgEntity;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SoundMeter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * Created by WangXin on 2015/4/12 0012.
 */
public class ChatMsgActivity extends Activity implements OnClickListener {

    //文本信息发送按钮
    private Button mBtnSend;
    //录音按钮
    private TextView mBtnRcd;
    //窗口聊天好友备注（呢称）显示
    private TextView who;
    //聊天窗口返回按钮
    private Button mBtnBack;
    //文本信息编辑框༭
    private EditText mEditTextContent;
    private RelativeLayout mBottom;
    //聊天内容显示界面
    private ListView mListView;
    //聊天功能显示界面显示适配器
    private ChatMsgViewAdapter mAdapter;
    //聊天记录内容和List
    private List<Contacts> mDataArrays = new ArrayList<Contacts>();
    private SQLiteOpenHelper helper = null;
    //读取本地缓存聊天记录
    private ChatRecord readerChatLog;
    //写入本地缓存聊天记录
    private ChatRecord writeChatLog;
    //最近聊天列表
    private List<Contacts>recentChat;
    //写入最近聊天列表缓存
    private RecentContacts writeRecentLog;
    //判断录音时间是否过短
    private boolean isShosrt = false;
    //录制语音信息相关动画
    private LinearLayout voice_rcd_hint_loading, voice_rcd_hint_rcding,
            voice_rcd_hint_tooshort;
    private ImageView img1, sc_img1;
    //语音信息工具类
    private SoundMeter mSensor;
    //录音层UI
    private View rcChat_popup;
    //手指不在录音按钮范围内时的
    private LinearLayout del_re;
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
    //录音模拟音量大小动画
    private ImageView volume;
    private boolean btn_vocie = false;
    private int flag = 1;
    private Handler mHandler = new Handler();
    //语音文件名
    private String voiceName;
    //语音录制起始时间
    private long startVoiceT, endVoiceT;
    //当前聊天好友ID
    private String currentUserAccount;
    //当前聊天好友备注（呢称）
    private String currentUserName;
    //当前聊天好友头像
    private Bitmap currentUserPhoto;

    private static final int IS_COM_MSG = 1;
    private static final int SEND_TO_MSG = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.helper = new DATABASE(this);
        chatBroadcas();
        initView();
        initData();
    }

    public void initView() {
        //获取本地聊天记录
        this.readerChatLog = new ChatRecord(this.helper.getReadableDatabase());
        //将聊天记录写入本地
        this.writeChatLog = new ChatRecord(this.helper.getWritableDatabase());
        //写入最近聊天缓存记录
        this.writeRecentLog = new RecentContacts(this.helper.getWritableDatabase());
        mListView = (ListView) findViewById(R.id.chat_listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnRcd = (TextView) findViewById(R.id.btn_rcd);
        who = (TextView) findViewById(R.id.who);
        mBtnSend.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBottom = (RelativeLayout) findViewById(R.id.btn_bottom);
        mBtnBack.setOnClickListener(this);
        chatting_mode_btn = (ImageView) this.findViewById(R.id.ivPopUp);
        volume = (ImageView) this.findViewById(R.id.volume);
        rcChat_popup = this.findViewById(R.id.rcChat_popup);
        img1 = (ImageView) this.findViewById(R.id.img1);
        sc_img1 = (ImageView) this.findViewById(R.id.sc_img1);
        del_re = (LinearLayout) this.findViewById(R.id.del_re);
        add_panel_im = (ImageView) findViewById(R.id.panel_add_im);
        panel_add_rl = (RelativeLayout) findViewById(R.id.add_panel_rl);
        select_photo = (ImageView) findViewById(R.id.select_photo);
        take_picture = (ImageView) findViewById(R.id.take_picture);
        voice_rcd_hint_rcding = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_rcding);
        voice_rcd_hint_loading = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_loading);
        voice_rcd_hint_tooshort = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_tooshort);
        mSensor = new SoundMeter();
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
                    mBtnRcd.setVisibility(View.GONE);
                    mBottom.setVisibility(View.VISIBLE);
                    btn_vocie = false;
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_msg_btn);

                } else {
                    mBtnRcd.setVisibility(View.VISIBLE);
                    mBottom.setVisibility(View.GONE);
                    chatting_mode_btn
                            .setImageResource(R.drawable.chatting_setmode_voice_btn);
                    btn_vocie = true;
                }
            }
        });
        mBtnRcd.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //按下语音录制按钮时返回false执行父类OnTouch
                return false;
            }
        });
    }

    private String[] msgArray = new String[] { "hello","what is wrong","说人话","是你先拽英语的 ","so?那有怎样","哇哦，你好厉害哦，有吓到我哦"};

    private String[] dataArray = new String[] { "2012-10-31 18:00",
            "2012-10-31 18:10", "2012-10-31 18:11", "2012-10-31 18:20",
            "2012-10-31 18:30", "2012-10-31 18:35"};
    private final static int COUNT = 6;

    public void initData() {
        Bundle bundle =this.getIntent().getExtras();
        currentUserAccount = bundle.getString("userAccount");
        currentUserName = bundle.getString("userName");
        currentUserPhoto = bundle.getParcelable("userPhoto");
        who.setText(currentUserName);
        mDataArrays = readerChatLog.getInfo(currentUserAccount);
        int length = mDataArrays.size();
        for (int i = 0; i < length; i++) {
            if (mDataArrays.get(i).getWho() == IS_COM_MSG){
                mDataArrays.get(i).setUsername(currentUserName);
                mDataArrays.get(i).setPortrait(currentUserPhoto);
            }
            else{
                mDataArrays.get(i).setUsername("帅锅");
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
            Contacts entity = new Contacts();
            entity.setDatetime(getDate());
            entity.setUsername("帅锅");
            entity.setWho(SEND_TO_MSG);
            entity.setMessage(contString);
            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();
            writeChatLog.insert(currentUserAccount, SEND_TO_MSG, contString, null);
            mEditTextContent.setText("");
            mListView.setSelection(mListView.getCount() - 1);
            sendMessage(contString);
        }
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
                + mins);

        return sbBuffer.toString();
    }

    //按下语音录制按钮时
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!Environment.getExternalStorageDirectory().exists()) {
            Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
            return false;
        }

        if (btn_vocie) {
            System.out.println("1");
            int[] location = new int[2];
            mBtnRcd.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
            int btn_rc_Y = location[1];
            int btn_rc_X = location[0];
            int[] del_location = new int[2];
            del_re.getLocationInWindow(del_location);
            int del_Y = del_location[1];
            int del_x = del_location[0];
            if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1) {
                if (!Environment.getExternalStorageDirectory().exists()) {
                    Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
                    return false;
                }
                System.out.println("2");
                if (event.getY() > btn_rc_Y && event.getX() > btn_rc_X) {//判断手势按下的位置是否是语音录制按钮的范围内
                    System.out.println("3");
                    mBtnRcd.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
                    rcChat_popup.setVisibility(View.VISIBLE);
                    voice_rcd_hint_loading.setVisibility(View.VISIBLE);
                    voice_rcd_hint_rcding.setVisibility(View.GONE);
                    voice_rcd_hint_tooshort.setVisibility(View.GONE);
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!isShosrt) {
                                voice_rcd_hint_loading.setVisibility(View.GONE);
                                voice_rcd_hint_rcding
                                        .setVisibility(View.VISIBLE);
                            }
                        }
                    }, 300);
                    img1.setVisibility(View.VISIBLE);
                    del_re.setVisibility(View.GONE);
                    startVoiceT = SystemClock.currentThreadTimeMillis();
                    voiceName = startVoiceT + ".amr";
                    start(voiceName);
                    flag = 2;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {//松开手势时执行录制完成
                System.out.println("4");
                mBtnRcd.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                if (event.getY() >= del_Y
                        && event.getY() <= del_Y + del_re.getHeight()
                        && event.getX() >= del_x
                        && event.getX() <= del_x + del_re.getWidth()) {
                    rcChat_popup.setVisibility(View.GONE);
                    img1.setVisibility(View.VISIBLE);
                    del_re.setVisibility(View.GONE);
                    stop();
                    flag = 1;
                    File file = new File(android.os.Environment.getExternalStorageDirectory()+
                            "/DecipherStranger/cache/voice" + voiceName);
                    if (file.exists()) {
                        file.delete();
                    }
                } else {

                    voice_rcd_hint_rcding.setVisibility(View.GONE);
                    stop();
                    endVoiceT = SystemClock.currentThreadTimeMillis();
                    flag = 1;
                    int time = (int) ((endVoiceT - startVoiceT) / 50);
                    if (time < 1) {
                        isShosrt = true;
                        voice_rcd_hint_loading.setVisibility(View.GONE);
                        voice_rcd_hint_rcding.setVisibility(View.GONE);
                        voice_rcd_hint_tooshort.setVisibility(View.VISIBLE);
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                voice_rcd_hint_tooshort
                                        .setVisibility(View.GONE);
                                rcChat_popup.setVisibility(View.GONE);
                                isShosrt = false;
                            }
                        }, 500);
                        return false;
                    }
                    Contacts entity = new Contacts();
                    entity.setDatetime(getDate());
                    entity.setUsername("帅锅");
                    entity.setWho(0);
                    entity.setTimeLen(time + "\"");
                    entity.setMessage(voiceName);
                    mDataArrays.add(entity);
                    mAdapter.notifyDataSetChanged();
                    writeChatLog.insert(currentUserAccount,SEND_TO_MSG,voiceName,time + "\"");
                    mListView.setSelection(mListView.getCount() - 1);
                    rcChat_popup.setVisibility(View.GONE);
                    sendVoice(voiceName, time);
                }
            }
            if (event.getY() < btn_rc_Y) {//手势按下的位置不在语音录制按钮的范围内
                System.out.println("5");
                Animation mLitteAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.cancel_rc);
                Animation mBigAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.cancel_rc2);
                img1.setVisibility(View.GONE);
                del_re.setVisibility(View.VISIBLE);
                del_re.setBackgroundResource(R.drawable.voice_rcd_cancel_bg);
                if (event.getY() >= del_Y
                        && event.getY() <= del_Y + del_re.getHeight()
                        && event.getX() >= del_x
                        && event.getX() <= del_x + del_re.getWidth()) {
                    del_re.setBackgroundResource(R.drawable.voice_rcd_cancel_bg_focused);
                    sc_img1.startAnimation(mLitteAnimation);
                    sc_img1.startAnimation(mBigAnimation);
                }
            } else {

                img1.setVisibility(View.VISIBLE);
                del_re.setVisibility(View.GONE);
                del_re.setBackgroundResource(0);
            }
        }
        return super.onTouchEvent(event);
    }

    private static final int POLL_INTERVAL = 300;

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };

    private void start(String name) {
        mSensor.start(name);
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        volume.setImageResource(R.drawable.amp1);
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            case 0:
            case 1:
                volume.setImageResource(R.drawable.amp1);
                break;
            case 2:
            case 3:
                volume.setImageResource(R.drawable.amp2);

                break;
            case 4:
            case 5:
                volume.setImageResource(R.drawable.amp3);
                break;
            case 6:
            case 7:
                volume.setImageResource(R.drawable.amp4);
                break;
            case 8:
            case 9:
                volume.setImageResource(R.drawable.amp5);
                break;
            case 10:
            case 11:
                volume.setImageResource(R.drawable.amp6);
                break;
            default:
                volume.setImageResource(R.drawable.amp7);
                break;
        }
    }
    private void sendMessage(String message){
        if(NetworkService.getInstance().getIsConnected()) {
            String msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgMessage)+":"+
                    "account"+":"+ MyStatic.UserAccount+":"+"re_account"+":"+currentUserAccount+
                    ":"+"message"+":"+message+":"+"date"+":"+getDate();
            Log.v("aaaaa", msg);
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
                    "account"+":"+ MyStatic.UserAccount+":"+"re_account"+":"+currentUserAccount+
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
        ChatBroadcastReceiver receiver = new ChatBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.MESSAGE");
        this.registerReceiver(receiver, filter);
    }

    public class ChatBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.MESSAGE")) {
                if(intent.getStringExtra("result").equals(MyStatic.resultTrue)) {
                    Toast.makeText(context, intent.getStringExtra("reMessage"), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
