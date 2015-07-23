package com.summerrc.dumplingplan.utils;

import android.content.Context;
import android.media.MediaPlayer;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;


/**
 * @author : SummerRC on 2015/7/20 0020
 * @version :  V1.0 <当前版本>
 *          description : <背景音乐控制类>
 */
public class MusicPlayer {
    private static MediaPlayer music;
    private static boolean musicSt = true;                  //音乐开关
    private static Context context;

    /**
     * 初始化方法
     * @param c
     */
    public static void init(Context c) {
        if(music == null) {
            context = c;
            musicSt = GameDataManager.init(c).isMusicST();
            music = MediaPlayer.create(context, R.raw.lianliankan_bg_one);
            music.setLooping(true);
            music.start();
        }
    }


    /**
     * 暂停音乐
     */
    public static void pauseMusic() {
        if(music.isPlaying())
            music.pause();
    }

    /**
     * 播放音乐
     */
    public static void startMusic() {
        if(music.isPlaying()) {
            return;
        }
        if(musicSt) {
            music.start();
        }

    }


    /**
     * 设置音乐开关
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt) {
        MusicPlayer.musicSt = musicSt;
        if(musicSt) {
            startMusic();
        } else {
           pauseMusic();
        }

    }

}

