package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
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

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.ChatMsgEntity;
import com.android.decipherstranger.util.SoundMeter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * Created by WangXin on 2015/4/12 0012.
 */
public class ChatMsgActivity extends Activity implements OnClickListener {

    //���Ͱ�ť
    private Button mBtnSend;
    //����¼�ư�ť
    private TextView mBtnRcd;
    //���ڶԻ��˱�ע���سƣ���ʾ
    private TextView who;
    //���ڷ��ؼ�
    private Button mBtnBack;
    //�ı�����༭
    private EditText mEditTextContent;
    private RelativeLayout mBottom;
    //����������ʾ����
    private ListView mListView;
    //����������ʾ����listView������
    private ChatMsgViewAdapter mAdapter;
    //�����¼
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    //�ж�¼��ʱ���Ƿ����
    private boolean isShosrt = false;
    //����¼�ƣ��Խ����ȶ������
    private LinearLayout voice_rcd_hint_loading, voice_rcd_hint_rcding,
            voice_rcd_hint_tooshort;
    private ImageView img1, sc_img1;
    //�����ļ�
    private SoundMeter mSensor;
    //¼����UI
    private View rcChat_popup;
    //��ָ����¼����ťButton��Χ���Ƕ���
    private LinearLayout del_re;
    //����ͼƬѡ������л���ť
    private ImageView add_panel_im;
    //ѡ�����
    private RelativeLayout panel_add_rl;
    //��ͼ����ѡȡ��Ƭ
    private ImageView select_photo;
    //����
    private ImageView take_picture;
    //����¼���л���ť
    private ImageView chatting_mode_btn;
    //����¼�ƣ�ģ��������С����
    private ImageView volume;
    private boolean btn_vocie = false;
    //����¼��ʱ��onTouchEvent��ǩ
    private int flag = 1;
    private Handler mHandler = new Handler();
    //�����ļ�����
    private String voiceName;
    //��ʼ¼�ƺͽ���¼��ʱ��
    private long startVoiceT, endVoiceT;
    //��ǰ�Ի���id
    private String currentUserAccount;
    //��ǰ�Ի�������
    private String currentUserName;
    //��ǰ�Ի���ͷ��
    private int currentUserPhotoId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // ����activityʱ���Զ����������
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        initData();
    }

    public void initView() {
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

        //���������л���ť
        chatting_mode_btn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

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
                //��������¼�ư�ťʱ����falseִ�и���OnTouch
                return false;
            }
        });
    }

    private String[] msgArray = new String[] { "hello","what is wrong","s","ss ","sss","sssss"};

    private String[] dataArray = new String[] { "2012-10-31 18:00",
            "2012-10-31 18:10", "2012-10-31 18:11", "2012-10-31 18:20",
            "2012-10-31 18:30", "2012-10-31 18:35"};
    private final static int COUNT = 6;

    public void initData() {
        Bundle bundle =this.getIntent().getExtras();
        currentUserAccount = bundle.getString("userAccount");
        currentUserName = bundle.getString("userName");
        currentUserPhotoId = bundle.getInt("userPhotoId");
        who.setText(currentUserName);
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName(currentUserName);
                entity.setUserPhoto(currentUserPhotoId);
                entity.setMsgType(1);
            } else {
                entity.setName("�߸�˧");
                entity.setMsgType(0);
            }

            entity.setText(msgArray[i]);
            mDataArrays.add(entity);
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
            case R.id.panel_add_im:
                addPanel();
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
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(getDate());
            entity.setName("�߸�˧");
            entity.setMsgType(0);
            entity.setText(contString);

            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();

            mEditTextContent.setText("");

            mListView.setSelection(mListView.getCount() - 1);
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

    //��������¼�ư�ťʱ
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!Environment.getExternalStorageDirectory().exists()) {
            Toast.makeText(this, "No SDCard", Toast.LENGTH_LONG).show();
            return false;
        }

        if (btn_vocie) {
            System.out.println("1");
            int[] location = new int[2];
            mBtnRcd.getLocationInWindow(location); // ��ȡ�ڵ�ǰ�����ڵľ�������
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
                if (event.getY() > btn_rc_Y && event.getX() > btn_rc_X) {//�ж����ư��µ�λ���Ƿ�������¼�ư�ť�ķ�Χ��
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
            } else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {//�ɿ�����ʱִ��¼�����
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
                    ChatMsgEntity entity = new ChatMsgEntity();
                    entity.setDate(getDate());
                    entity.setName("�߸�˧");
                    entity.setMsgType(0);
                    entity.setTime(time+"\"");
                    entity.setText(voiceName);
                    mDataArrays.add(entity);
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mListView.getCount() - 1);
                    rcChat_popup.setVisibility(View.GONE);

                }
            }
            if (event.getY() < btn_rc_Y) {//���ư��µ�λ�ò�������¼�ư�ť�ķ�Χ��
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
}
