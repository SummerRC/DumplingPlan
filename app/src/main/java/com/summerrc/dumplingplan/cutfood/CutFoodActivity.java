package com.summerrc.dumplingplan.cutfood;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.UIHelper;

import java.lang.ref.WeakReference;

/**
 * @author SummerRC on 2015/7/21 0011.
 * 切食材，没用布局文件，使用的是自定义的SurfaceView
 */
public class CutFoodActivity extends Activity {

    private MyHandler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        handler = new MyHandler(this);
        MyGameView view = new MyGameView(this, handler);
        setContentView(view);
    }

    static class MyHandler extends Handler {
        WeakReference<CutFoodActivity> mActivity;
        MyHandler(CutFoodActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final CutFoodActivity activity = mActivity.get();
            if(activity == null) {
                return;
            }
            activity.setContentView(R.layout.activity_cut_food);
            switch (msg.what) {
                case IntentConstant.WIN:
                    if(GameDataManager.init(activity.getApplicationContext()).getUnLock() <= 6) {
                        GameDataManager.init(activity.getApplicationContext()).setUnLock(7);
                    }
                    activity.findViewById(R.id.rootView).setBackgroundResource(R.mipmap.success);
                    break;
                case IntentConstant.LOSE:
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
}