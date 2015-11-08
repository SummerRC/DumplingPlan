package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 揉面界面
 */
public class RubActivity extends Activity implements View.OnTouchListener{

    private AnimationDrawable animationDrawable;        // Frame动画
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rub);
        initView();
    }


    private void initView() {
        SimpleDraweeView sdv_soho_rub = (SimpleDraweeView) findViewById(R.id.sdv_soho_rub);
        animationDrawable = (AnimationDrawable) sdv_soho_rub.getBackground();
        sdv_soho_rub.setOnTouchListener(this);
        handler = new Handler();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.sdv_soho_rub:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        animationDrawable.start();
                        animationDrawable.setOneShot(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                animationDrawable.start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UIHelper.openCutActivity(RubActivity.this);
                    }
                }, 1800);
                break;
        }
        return true;            //返回false会导致只响应down事件，true导致事件不会传递给父view
    }


}