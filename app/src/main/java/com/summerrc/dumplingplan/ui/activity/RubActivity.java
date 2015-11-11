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
import com.summerrc.dumplingplan.config.MMApplication;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rub);
        initView();
    }


    private void initView() {
        SimpleDraweeView sdv_soho_rub = (SimpleDraweeView) findViewById(R.id.sdv_soho_rub);
        animationDrawable = (AnimationDrawable) sdv_soho_rub.getBackground();
        sdv_soho_rub.setOnTouchListener(this);
        handler = new Handler();
        setBackground();
        setVisible();
        findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMApplication.isSelected = !MMApplication.isSelected;
                setVisible();
            }
        });
    }

    private void setVisible() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_yingyue).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.iv_yingxiao).setVisibility(View.GONE);
            findViewById(R.id.iv_yingyue).setVisibility(View.GONE);
            findViewById(R.id.iv_yingxiao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinxiao = !MMApplication.isSelected_yinxiao;
                    if(MMApplication.isSelected_yinxiao) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });
            findViewById(R.id.iv_yingyue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication. isSelected_yinyue = !MMApplication.isSelected_yinyue;
                    if(MMApplication.isSelected_yinyue) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });

        }
    }

    private void setBackground() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
        } else {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
        }
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