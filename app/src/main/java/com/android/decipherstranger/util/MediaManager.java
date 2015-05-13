package com.android.decipherstranger.util;

import android.media.*;
import android.media.AudioManager;

import java.io.IOException;

/**
 * *
 * ¤Ø¡¡¡¡¡¡¡¡¡¡£¯|
 * ¡¡¡¡/£Ü7¡¡¡¡¡¡ ¡Ï£ß/
 * ¡¡ /¡¡©¦¡¡¡¡ £¯¡¡£¯
 * ¡¡©¦¡¡Z £ß,£¼¡¡£¯¡¡¡¡ /`©c
 * ¡¡©¦¡¡¡¡¡¡¡¡¡¡©c¡¡¡¡ /¡¡¡¡¡µ
 * ¡¡ Y¡¡¡¡¡¡¡¡¡¡`¡¡ /¡¡¡¡/
 * ¡¡?¡ñ¡¡?¡¡¡ñ¡¡¡¡??¡´¡¡¡¡/
 * ¡¡()¡¡ ¤Ø¡¡¡¡¡¡¡¡|¡¡£Ü¡´
 * ¡¡¡¡>? ?_¡¡ ¥£¡¡ ©¦ £¯£¯      È¥°É£¡
 * ¡¡ / ¤Ø¡¡¡¡ /¡¡?£¼| £Ü£Ü        ±È¿¨Çð~
 * ¡¡ ©c_?¡¡¡¡(_£¯¡¡ ©¦£¯£¯           ÏûÃð´úÂëBUG
 * ¡¡¡¡7¡¡¡¡¡¡¡¡¡¡¡¡¡¡|£¯
 * ¡¡¡¡£¾¨Dr£þ£þ`?¨D£ß
 * Created by WangXin on 2015/5/12 0012.
 */
public class MediaManager {
    private static MediaPlayer mMediaPlayer;
    private static boolean isPause;

    public static void playSound(String filePath,MediaPlayer.OnCompletionListener onCompletionListener){
        if (mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mMediaPlayer.reset();
                    return false;
                }
            });
        }else {
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pause(){
        if (mMediaPlayer!=null&&mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    public void resume(){
        if (mMediaPlayer!=null&&isPause){
            mMediaPlayer.start();
            isPause = false;
        }
    }
    public void release(){
        if (mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
