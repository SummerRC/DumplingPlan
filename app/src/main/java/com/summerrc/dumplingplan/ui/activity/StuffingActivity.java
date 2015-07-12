package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 切馅界面
 */
public class StuffingActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_stuffing;
    private Handler mHandler;
    private ImageView iv_kitchen_knife_left;
    private ImageView iv_kitchen_knife_right;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_stuffing);
        initView();
        mHandler = new Handler();
        new Thread(new Runnable() {
                         @Override
                         public void run() {
                                 mHandler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                                 TranslateAnimation translateAnimation1 = new TranslateAnimation(
                                                                 TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                                                 TranslateAnimation.RELATIVE_TO_PARENT, 0 ,
                                                                 TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                                                 TranslateAnimation.RELATIVE_TO_PARENT, 0.2f);
                                                 translateAnimation1.setDuration(200);
                                                 translateAnimation1.setStartTime(0);
                                                 translateAnimation1.setRepeatCount(15);             //Integer.MAX_VALUE 可以设置无穷次
                                                 translateAnimation1.setRepeatMode(Animation.REVERSE);
                                                 iv_kitchen_knife_left.startAnimation(translateAnimation1);
                                             TranslateAnimation translateAnimation2 = new TranslateAnimation(
                                                     TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                                     TranslateAnimation.RELATIVE_TO_PARENT, 0 ,
                                                     TranslateAnimation.RELATIVE_TO_PARENT, 0,
                                                     TranslateAnimation.RELATIVE_TO_PARENT, 0.2f);
                                             translateAnimation2.setDuration(200);
                                             translateAnimation2.setStartTime(300);
                                             translateAnimation2.setRepeatCount(15);             //Integer.MAX_VALUE 可以设置无穷次
                                             translateAnimation2.setRepeatMode(Animation.REVERSE);
                                                 iv_kitchen_knife_right.startAnimation(translateAnimation2);
                                             }
                                 });
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        UIHelper.openDoughActivity(StuffingActivity.this);
                                    }
                                }, 3000);
                         }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_stuffing==null || bitmap_background_stuffing.isRecycled()) {
            bitmap_background_stuffing = BitmapFactory.decodeResource(getResources(), R.mipmap.background_stuffing);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_stuffing));
    }

    protected void initView() {
        super.initView();
        iv_kitchen_knife_left = (ImageView)findViewById(R.id.iv_kitchen_knife_left);
        iv_kitchen_knife_right = (ImageView)findViewById(R.id.iv_kitchen_knife_right);
        /** 由于动画的原因，刀在组件最上层，设置touch监听可以是动画播放过程中其它下层组件点击事件失效 */
        findViewById(R.id.ll_animation).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_stuffing != null && !bitmap_background_stuffing.isRecycled()) {
            bitmap_background_stuffing.isRecycled();
            bitmap_background_stuffing = null;
            System.gc();
        }
    }

}
