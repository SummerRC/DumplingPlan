package com.summerrc.dumplingplan.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;
import java.lang.ref.WeakReference;

/**
 * @author SummerRC on 2015.07.12
 * description : 甩水界面
 */
public class ShakeActivity extends BaseActivity implements Animation.AnimationListener, SensorEventListener {
    private Bitmap bitmap_background_shake;
    public RotateAnimation rotateAnimation;             // 旋转动画
    private SoundPool soundPool;                        // 音频池
    private Vibrator mVibrator;                         // 震动
    private SensorManager mSensorManager;               // 重力传感器
    private Sensor mSensor;

    private MyHandler handler;
    private ImageView iv_hint_shake;
    private int index =0;
    private int shake_count = 0;
    private static final int SENSOR_SHAKE = 10;
    private int hitOkSfx;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_shake);
        initView();
        initShake();
    }

    private void initShake() {
        handler = new MyHandler(this);
        /** 创建一个SensorManager来获取系统的传感器服务 */
        mSensorManager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mVibrator = (Vibrator)getApplication().getSystemService(Context.VIBRATOR_SERVICE);

        iv_hint_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        /** 这里指定声音池的最大音频流数目为10，声音品质为5大家可以自 己测试感受下效果 */
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        /**  载入音频流  */
        hitOkSfx = soundPool.load(this, R.raw.shake, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_shake == null || bitmap_background_shake.isRecycled()) {
            bitmap_background_shake = BitmapFactory.decodeResource(getResources(), R.mipmap.background_shake);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_shake));
    }

    protected void initView() {
        super.initView();
        iv_hint_shake = (ImageView) findViewById(R.id.iv_hint_shake);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap_background_shake != null && !bitmap_background_shake.isRecycled()) {
            bitmap_background_shake.isRecycled();
            bitmap_background_shake = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openScoreActivity(this);
                break;
            case R.id.iv_strip:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.ll_hint_cut).setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return true;
    }

    private static class MyHandler extends Handler {
        WeakReference<ShakeActivity> weakReference;     //持有当前Activity对象的弱引用
        MyHandler(ShakeActivity activity) {
            weakReference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            ShakeActivity activity = weakReference.get();
            if(activity == null) return;
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    activity.shake_count++;
                    if(activity.shake_count == 80) {
                        activity.hintToNext();
                    }
                    activity.waterAlpha(activity.shake_count);
                    activity.startAnimation();
                    break;
            }
        }
    }

    public void startAnimation() {
        index = 1;
        rotateAnimation = new RotateAnimation(0, -10, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(200);
        rotateAnimation.setAnimationListener(this);
        iv_hint_shake.startAnimation(rotateAnimation);
        /** 速率最低0.5最高为2，1代表 正常速度 */
        soundPool.play(hitOkSfx, 1, 1, 0, 0, 1);
        mVibrator.vibrate(300);
        /** 第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复 */
        mVibrator.vibrate(new long[]{100, 200, 100, 300}, -1);
        Thread updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
        /**启动线程 */
        updateThread.start();
    }


    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        switch (index) {
            case 1:                 // 第一个动画
                index++;
                rotateAnimation = new RotateAnimation(-10, 10,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(200);
                rotateAnimation.setAnimationListener(this);
                iv_hint_shake.startAnimation(rotateAnimation);
                break;
            case 2:                 // 第二个动画
                index++;
                rotateAnimation = new RotateAnimation(10, -10,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(200);
                rotateAnimation.setAnimationListener(this);
                iv_hint_shake.startAnimation(rotateAnimation);
                break;
            case 3:                 // 第三个动画
                index++;
                rotateAnimation = new RotateAnimation(-10, 10,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(200);
                rotateAnimation.setAnimationListener(this);
                iv_hint_shake.startAnimation(rotateAnimation);
                break;
            case 4:                 // 第四个动画
                index = 0;
                rotateAnimation = new RotateAnimation(10, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(200);
                rotateAnimation.setAnimationListener(this);
                iv_hint_shake.startAnimation(rotateAnimation);
                break;
            default:
                break;
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /** 传感器信息改变时执行该方法 */
        float[] values = event.values;
        float x = values[0];                    // x轴方向的重力加速度，向右为正
        float y = values[1];                    // y轴方向的重力加速度，向前为正
        float z = values[2];                    // z轴方向的重力加速度，向上为正
         Log.i("speed", "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
        /** 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。*/
        double mediumValue = 15;                   // 三星  User.sensitivity i9250怎么晃都不会超过20，没办法，只设置19了
        if (Math.abs(x) > mediumValue || Math.abs(y) > mediumValue || Math.abs(z) > mediumValue) {
            mVibrator.vibrate(200);
            Message msg = new Message();
            msg.what = SENSOR_SHAKE;
            handler.sendMessage(msg);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        /** 当传感器精度改变时回调该方法，Do nothing. */
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mSensorManager != null) {            // 注册监听器
            /** 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率 */
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        if (mSensorManager != null) { // 取消监听器
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    private void waterAlpha(int i) {
        float start = 1 - 0.01f * (i-1);
        float end = 1 - 0.01f * i;
        ObjectAnimator waterObjectAnimator = ObjectAnimator.ofFloat(findViewById(R.id.iv_water),"alpha", start, end);
        waterObjectAnimator.setDuration(500);
        waterObjectAnimator.start();
    }

}