package com.summerrc.dumplingplan.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 擀面界面
 */
public class SkinActivity extends BaseActivity{
    private Bitmap bitmap_background_skin;
    private ObjectAnimator rollPinAnimator;        //擀面动画
    private ImageView iv_skin_big;                         //面皮
    private ImageView iv_roll_pin;                         //面杆
    private boolean rollPinAnimatorIsStart = false;     //擀面动画是否开始了

    @Override
    protected void setView() {
        setContentView(R.layout.activity_skin);
        initView();
        initAnimator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_skin==null || bitmap_background_skin.isRecycled()) {
            bitmap_background_skin = BitmapFactory.decodeResource(getResources(), R.mipmap.background_skin);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_skin));
    }

    protected void initView() {
        super.initView();
        iv_roll_pin = (ImageView) findViewById(R.id.iv_rolling_pin);
        iv_roll_pin.setOnTouchListener(this);
        iv_skin_big = (ImageView) findViewById(R.id.iv_skin_big);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openPackActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_skin != null && !bitmap_background_skin.isRecycled()) {
            bitmap_background_skin.isRecycled();
            bitmap_background_skin = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_rolling_pin:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //hintToNext();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(findViewById(R.id.rl_hint_skin).getVisibility()==View.VISIBLE) {
                            findViewById(R.id.rl_hint_skin).setVisibility(View.GONE);
                        }
                        if(rollPinAnimatorIsStart) {
                            rollPinAnimator.resume();
                        } else {
                            rollPinAnimator.start();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        rollPinAnimator.pause();
                        break;
                }
        }
        return true;
    }

    /**
     *初始化动画：擀面动画
     */
    private void initAnimator() {
        rollPinAnimator = ObjectAnimator.ofFloat(iv_roll_pin, "translationY", -45f , 45f);
        rollPinAnimator.setDuration(1000);
        rollPinAnimator.setRepeatCount(Integer.MAX_VALUE);
        rollPinAnimator.setRepeatMode(ValueAnimator.REVERSE);
    }
}
