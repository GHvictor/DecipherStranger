package com.android.decipherstranger.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * *
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 * Created by WangXin on 2015/5/10 0010.
 */
public class AudioManager {
    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private static AudioManager mInstance;

    public boolean isPrepared;

    public AudioManager(String dir){
        mDir = dir;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    public interface AudioStateListener{
        void wellPrepared();
    }

    public AudioStateListener mAudioStateListener;

    public void setOnAudioStateListener(AudioStateListener Listener){
        mAudioStateListener = Listener;
    }

    public static AudioManager getmInstance(String dir){
        if (mInstance == null){
            synchronized (AudioManager.class){
                if (mInstance == null){
                    mInstance = new AudioManager(dir);
                }
            }
        }
        return mInstance;
    }

    public void prepareAudio(){
        try {
            isPrepared = false;
            File dir = new File(mDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir,fileName);

            mCurrentFilePath = file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            //设置绝输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码为amr
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            //准备结束
            isPrepared =true;

            if (mAudioStateListener != null){
                mAudioStateListener.wellPrepared();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName(){
        return UUID.randomUUID().toString()+".amr";
    }
    public int  getVoiceLevel(int maxLevel){
        try {
            if (isPrepared){
                //mMediaRecorder.getMaxAmplitude()1-32767
                return maxLevel*mMediaRecorder.getMaxAmplitude()/32768 + 1;
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void release(){
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    public void cancel(){
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }

    }

}
