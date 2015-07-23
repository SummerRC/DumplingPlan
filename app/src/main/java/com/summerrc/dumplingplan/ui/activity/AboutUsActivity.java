package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.ui.widget.KeywordsFlow;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;
import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AboutUsActivity extends Activity implements View.OnClickListener {

    public static final String[] keywords = { "木木", "婷女神", "齐凤梨",
            "高小姐", "湖建人","皇上", "夏雨荷", "马总", "妖", "姚","晓冬",
            "饺子计划", "Dumpling Plan", "Nothing But", "git", "SummerRC", "不是饺子"};
    private KeywordsFlow keywordsFlow;
    private MyHandler handler;
    private Bitmap bitmap;
    private boolean tag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);

        handler = new MyHandler(this);
        initView();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 1500);
    }

    public void initView() {
        keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsFlow);
        keywordsFlow.setDuration(800l);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.background_welcome);
        }
        keywordsFlow.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:					//设置
                SoundUtil.initSounds(getApplicationContext());
                SoundUtil.playSounds(SoundUtil.BACK, 0, getApplicationContext());
                UIHelper.openWelcomeActivity(this);
                break;
        }
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }

    private void in() {
        keywordsFlow.rubAllViews();									// keywordsFlow.rubKeywords();
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
    }

    private void out() {
        keywordsFlow.rubAllViews();									// keywordsFlow.rubKeywords();
        feedKeywordsFlow(keywordsFlow, keywords);
        keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(bitmap != null && !bitmap.isRecycled()) {
            bitmap.isRecycled();
            bitmap = null;
            System.gc();
        }
    }


    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    static class MyHandler extends Handler {
        WeakReference<AboutUsActivity> mActivity;
        MyHandler(AboutUsActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final AboutUsActivity activity = mActivity.get();
            if(activity == null) {
                return;
            }
            switch (msg.what) {
                case 1:
                    if (activity.tag) {
                        SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, activity.getApplicationContext());
                        activity.out();
                        activity.tag = false;
                    } else {
                        SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, activity.getApplicationContext());
                        activity.in();
                        activity.tag = true;
                    }
                    break;
            }
        }
    }

}
