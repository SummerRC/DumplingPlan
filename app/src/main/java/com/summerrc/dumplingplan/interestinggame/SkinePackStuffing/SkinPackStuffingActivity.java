package com.summerrc.dumplingplan.interestinggame.SkinePackStuffing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;
import java.lang.ref.WeakReference;

/**
 * @author SummerRC on 2015/7/22 0011.
 * 皮接馅，没用布局文件，使用的是自定义的SurfaceView
 */
public class SkinPackStuffingActivity extends Activity {

    private MyHandler handler;
    private MyGameView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        handler = new MyHandler(this);
        view = new MyGameView(this, handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(view);
    }

    static class MyHandler extends Handler {
        WeakReference<SkinPackStuffingActivity> mActivity;
        MyHandler(SkinPackStuffingActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final SkinPackStuffingActivity activity = mActivity.get();
            if(activity == null) {
                return;
            }
            activity.setContentView(R.layout.activity_cut_food);
            switch (msg.what) {
                case IntentConstant.WIN:
                    if(GameDataManager.init(activity.getApplicationContext()).getUnLock() <= 8) {
                        GameDataManager.init(activity.getApplicationContext()).setUnLock(9);
                    }
                    SoundUtil.initSounds(activity.getApplicationContext());
                    SoundUtil.playSounds(SoundUtil.WIN, 0, activity.getApplicationContext());
                    activity.findViewById(R.id.rootView).setBackgroundResource(R.mipmap.success);
                    break;
                case IntentConstant.LOSE:
                    SoundUtil.initSounds(activity.getApplicationContext());
                    SoundUtil.playSounds(SoundUtil.LOSE, 0, activity.getApplicationContext());
                    activity.findViewById(R.id.rootView).setBackgroundResource(R.mipmap.fail);
                    break;
            }
            activity.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UIHelper.openLockActivity(activity);
                }
            }, 2000);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SoundUtil.release();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}