package com.summerrc.dumplingplan.activion.listener;

import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.view.View;

/**
 * Created by SummerRC on 2015/11/7.
 * description:播放动画的监听器
 */
public class PlayAnimClickListener implements View.OnClickListener {

    private Animatable animatable;
    private Handler handler;
    private long playTime;
    private AnimStopCallBack animStopCallBack;
    private boolean isPlay = false;

    public PlayAnimClickListener(Animatable animatable, Handler handler, long playTime, AnimStopCallBack animStopCallBack) {
        this.animatable = animatable;
        this.handler= handler;
        this.playTime = playTime;
        this.animStopCallBack = animStopCallBack;
    }

    @Override
    public void onClick(View v) {
        if(!isPlay) {
            isPlay = true;
            animatable.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopAnim();
                    animStopCallBack.afterAnimStop();
                }
            }, playTime);
        }

    }

    private void stopAnim() {
        if(animatable.isRunning()) {
            animatable.stop();
        }
    }

    public interface AnimStopCallBack {
        void afterAnimStop();
    }

}
