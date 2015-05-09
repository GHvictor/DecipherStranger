package com.android.decipherstranger.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;


public class AudioRecorderButton extends Button {

    private static final int DISTANCE_Y_CANCEL = 50;
    //
    private static final int STATE_NORMAL = 1;
    //
    private static final int STATE_RECORDING = 2;
    //
    private static final int STATE_WANG_TO_CANCEL = 3;
    //
    private int mCurState = STATE_NORMAL;
    //
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
                    //
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
    //�ָ�״̬����־λ
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
