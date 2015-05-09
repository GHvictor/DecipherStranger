package com.android.decipherstranger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;


public class AudioRecorderButton extends Button {

    private static final int DISTANCE_Y_CANCEL = 50;
    //默认状态
    private static final int STATE_NORMAL = 1;
    //录音状态
    private static final int STATE_RECORDING = 2;
    //取消录音发送
    private static final int STATE_WANG_TO_CANCEL = 3;
    //记录当前状态
    private int mCurState = STATE_NORMAL;
    //已经开始录音
    private boolean isRecording = false;
    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioRecorderButton(Context context) {
        super(context,null);
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
                    //根据X,Y的左边判断是否想要取消
                    if (wantToCancel(x,y)){
                        changeState(STATE_WANG_TO_CANCEL);
                    }else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(mCurState == STATE_RECORDING){
                     //release
                    //callBackToActivity
                }else if (mCurState == STATE_WANG_TO_CANCEL){
                    //cancel
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }
    //恢复状态及标志位
    private void reset() {
        isRecording = false;
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
                    break;
                case STATE_RECORDING:
                    if (isRecording){
                        //Dialog.recording()
                    }
                    break;
                case STATE_WANG_TO_CANCEL:
                    break;
            }
        }
    }
}
