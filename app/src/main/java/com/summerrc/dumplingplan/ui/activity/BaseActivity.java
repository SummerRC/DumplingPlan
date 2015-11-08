package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 相关Activity的父类
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener, View.OnTouchListener{
    private long firstTime;
    private enum TipType{
        TYPE_BACK, TYPE_RESUME, TYPE_DEFAULT
    }
    private TipType tipType = TipType.TYPE_DEFAULT;
    public int PhoneWidth;                         //手机屏幕的宽度
    public int PhoneHeight;                        //手机屏幕的高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager wm=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
        PhoneWidth = wm.getDefaultDisplay().getWidth();
        PhoneHeight = wm.getDefaultDisplay().getHeight();
        setView();
        findViewById(R.id.viewContainer).setBackgroundResource(R.mipmap.background_film);
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

    protected abstract void setView();
    protected void initView() {
        findViewById(R.id.iv_back).setOnTouchListener(this);
        findViewById(R.id.iv_resume).setOnTouchListener(this);
        findViewById(R.id.iv_pause).setOnTouchListener(this);
        findViewById(R.id.iv_next).setOnClickListener(this);
        findViewById(R.id.iv_setting).setOnTouchListener(this);
    }

    /**
     * 返回、重玩、设置、暂停等按钮的监听器
     * @param v 被监听的控件
     * @param event 监听到的触摸事件
     * @return true代表事件被消费
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean touchResult = false;
        switch (v.getId()) {
            case R.id.iv_pause:
                SoundUtil.playSounds(SoundUtil.STOP, 0, getApplicationContext());
                clickBtnPause();
                touchResult = true;
                break;
            case R.id.iv_tip_content:           //继续游戏
                SoundUtil.playSounds(SoundUtil.REPLAY, 0, getApplicationContext());
                findViewById(R.id.rl_dialog_background).setVisibility(View.GONE);
                touchResult = true;
                break;
            case R.id.iv_back:                   //返回欢迎界面按钮
                SoundUtil.playSounds(SoundUtil.BACK, 0, getApplicationContext());
                clickBtnBack();
                touchResult = true;
                break;
            case R.id.iv_resume:                 //返回游戏第一个界面按钮
                SoundUtil.playSounds(SoundUtil.REPLAY, 0, getApplicationContext());
                clickBtnResume();
                touchResult = true;
                break;
            case R.id.iv_yes:                    //两种情况：返回首页或者重玩本关卡,记得要清空游戏游戏数据
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                GameDataManager.init(getApplicationContext()).clean();
                switch (tipType) {
                    case TYPE_BACK:
                        UIHelper.openWelcomeActivity(this);
                        break;
                    case TYPE_RESUME:
                        UIHelper.openSelectFoodActivity(this);
                        break;
                }
                touchResult = true;
                break;
            case R.id.iv_no:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                findViewById(R.id.rl_dialog_background).setVisibility(View.GONE);
                touchResult = true;
                break;
            case R.id.rl_dialog_background:
                break;
            case R.id.iv_setting:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
                    UIHelper.openSettingActivity(this);
                }
                break;
        }
        return touchResult;
    }

    /**
     * 点击返回欢迎界面按钮后的弹出层
     */
    private void clickBtnBack() {
        tipType = TipType.TYPE_BACK;
        findViewById(R.id.rl_dialog_background).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_dialog_background).setOnTouchListener(this);
        findViewById(R.id.rl_dialog_background).setBackgroundResource(R.mipmap.pause_black_);
        findViewById(R.id.iv_tip_content).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_tip_content).setBackgroundResource(R.mipmap.back_to_start);
        findViewById(R.id.ll_yes_and_no).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_yes).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_no).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_yes).setOnTouchListener(this);
        findViewById(R.id.iv_no).setOnTouchListener(this);
    }

    /**
     * 点击重玩按钮后的弹出层
     */
    private void clickBtnResume() {
        tipType = TipType.TYPE_RESUME;
        findViewById(R.id.rl_dialog_background).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_dialog_background).setOnTouchListener(this);
        findViewById(R.id.rl_dialog_background).setBackgroundResource(R.mipmap.pause_black_);
        findViewById(R.id.iv_tip_content).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_tip_content).setBackgroundResource(R.mipmap.background_resume);
        findViewById(R.id.ll_yes_and_no).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_yes).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_no).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_yes).setOnTouchListener(this);
        findViewById(R.id.iv_no).setOnTouchListener(this);
    }

    /**
     * 点击暂停按钮后的弹出层
     */
    private void clickBtnPause() {
        findViewById(R.id.rl_dialog_background).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_dialog_background).setOnTouchListener(this);
        findViewById(R.id.rl_dialog_background).setBackgroundResource(R.mipmap.pause_black_);
        findViewById(R.id.iv_tip_content).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_tip_content).setOnTouchListener(this);
        findViewById(R.id.ll_yes_and_no).setVisibility(View.GONE);
    }

    /**
     * 提示进入下一关
     */
    protected void hintToNext() {
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_next).setOnTouchListener(this);
        scaleAnimation(findViewById(R.id.iv_next), 0.4f, 1.2f, 0.4f, 1.2f, 1500);
    }

    /**
     * 缩放动画
     * @param view    组件
     * @param fromX 缩放开始的比例
     * @param toX      缩放结束的比例
     * @param fromY 缩放开始的比例
     * @param toY      缩放结束的比例
     * @param time    缩放一次的时间
     */
    protected void  scaleAnimation(View view, float fromX, float toX, float fromY, float toY, long time) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(time);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setRepeatCount(0);
        view.startAnimation(scaleAnimation);
    }

    /**
     * 平移动画
     * @param view          要平移的组件
     * @param x                 x轴方向位移
     * @param y                 y轴方向位移
     * @param time          一次动画的时间
     * @param times        动画运行几次
     * @param keepState        动画结束保持原位置
     */
    protected void translateAnimationStart(View view, float x, float y, long time, int times, boolean keepState) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, y);
        translateAnimation.setDuration(time);
        translateAnimation.setStartTime(0);
        translateAnimation.setRepeatCount(times);             //Integer.MAX_VALUE 可以设置无穷次
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setFillAfter(keepState);
        view.startAnimation(translateAnimation);
    }

    protected void translateAnimationStop(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0,0);
        translateAnimation.setDuration(0);
        translateAnimation.setStartTime(0);
        translateAnimation.setRepeatCount(0);             //Integer.MAX_VALUE 可以设置无穷次
        translateAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(translateAnimation);
    }

    /**
     * 平移动画
     * @param x                 x轴方向位移
     * @param y                 y轴方向位移
     * @param time          一次动画的时间
     * @return 缩放动画
     */
    protected Animation getTranslateAnimation(float x, float y, long time) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, y);
        translateAnimation.setDuration(time);
        translateAnimation.setStartTime(0);
        translateAnimation.setRepeatCount(1);             //Integer.MAX_VALUE 可以设置无穷次
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    /**
     * 缩放动画
     * @param fromX 缩放开始的比例
     * @param toX      缩放结束的比例
     * @param fromY 缩放开始的比例
     * @param toY      缩放结束的比例
     * @param time    缩放一次的时间
     * @return 缩放动画
     */
    protected Animation  getScaleAnimation(float fromX, float toX, float fromY, float toY, long time) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY);
        scaleAnimation.setDuration(time);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setRepeatCount(1);
        return scaleAnimation;
    }
}
