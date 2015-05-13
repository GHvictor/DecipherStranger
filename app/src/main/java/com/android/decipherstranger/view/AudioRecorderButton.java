package com.android.decipherstranger.view;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.android.decipherstranger.R;
import com.android.decipherstranger.util.AudioManager;
import com.android.decipherstranger.util.DialogManager;
import com.android.decipherstranger.util.Tools;



public class AudioRecorderButton extends Button implements AudioManager.AudioStateListener {

    private static final int DISTANCE_Y_CANCEL = 50;
    //正常状态
    private static final int STATE_NORMAL = 1;
    //录音状态
    private static final int STATE_RECORDING = 2;
    //取消状态
    private static final int STATE_WANG_TO_CANCEL = 3;
    //开始录音标记
    private int mCurState = STATE_NORMAL;
    //判断是否为录音状态
    private boolean isRecording = false;

    private static final float MIN_RECORDER_TIME = 0.6f;

    private DialogManager mDialogManager;

    private AudioManager mAudioManager;

    private float mTime;
    //是否触发longClick事件
    private boolean mReady;
    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDialogManager = new DialogManager(getContext());
        if (Tools.hasSdcard()){
            String dir = Environment.getExternalStorageDirectory()+"/JMMSH/voiceMsg";
            mAudioManager = AudioManager.getmInstance(dir);
            mAudioManager.setOnAudioStateListener(this);
        }
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady = true;
                mAudioManager.prepareAudio();
                return false;
            }
        });
    }

    public AudioRecorderButton(Context context) {
        super(context, null);
    }
    //录音完成后的回调
    public interface AudioFinishRecorderListener{
        void onFinish(float seconds,String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        mListener = listener;
    }

    //获取音量大小的Runable
    private Runnable mGetVoiceLevelRunable = new Runnable() {
        @Override
        public void run() {
            while(isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private static final int  MSG_AUDIO_PREPARED = 666;
    private static final int  MSG_VOICE_CHANGE = 888;
    private static final int  MSG_DIALOG_DIMISS = 999;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            switch(msg.what){
                case MSG_AUDIO_PREPARED:
                    mDialogManager.showRecordingDialog();
                    isRecording = true;
                    new Thread(mGetVoiceLevelRunable).start();
                    break;
                case MSG_VOICE_CHANGE:
                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    mDialogManager.dimissDialog();
                    break;
            }
        };
    };
    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording){
                    if (wantToCancel(x,y)){
                        changeState(STATE_WANG_TO_CANCEL);
                    }else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!mReady){
                    reset();
                    return super.onTouchEvent(event);
                }

                if (mTime<MIN_RECORDER_TIME||!isRecording){
                    mDialogManager.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);
                }else if(mCurState == STATE_RECORDING){     //正常录制结束
                    mDialogManager.dimissDialog();
                    mAudioManager.release();
                    if (mListener != null){
                        mListener.onFinish(mTime,mAudioManager.getCurrentFilePath());
                    }
                }else if (mCurState == STATE_WANG_TO_CANCEL){
                    mDialogManager.dimissDialog();
                    mAudioManager.cancel();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }
    //恢复标志位
    private void reset() {
        isRecording = false;
        mReady = false;
        mTime = 0;
        changeState(STATE_NORMAL);
    }

    private boolean wantToCancel(int x, int y) {
        if (y < -DISTANCE_Y_CANCEL || y >getHeight()+DISTANCE_Y_CANCEL){
            return true;
        }
        if (x < 0 || x > getWidth()){
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (mCurState != state){
            mCurState = state;
            switch (state){
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.btn_recorder_normal);
                    setText(R.string.str_recorder_normal);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.btn_recorder_recording);
                    setText(R.string.str_recorder_recording);
                    if (isRecording){
                        mDialogManager.recording();
                    }
                    break;
                case STATE_WANG_TO_CANCEL:
                    setBackgroundResource(R.drawable.btn_recorder_recording);
                    setText(R.string.str_recorder_want_cancel);
                    mDialogManager.wantToCancel();
                    break;
            }
        }
    }


}
