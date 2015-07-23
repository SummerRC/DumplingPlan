package com.summerrc.dumplingplan.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015/7/11 0011.
 * description : 包馅界面
 */
public class PackActivity extends BaseActivity {
    private Bitmap bitmap_background_pack;
    private View ll_hint_pack;
    private ImageView iv_dumpling;          //包好的饺子
    private Handler handler;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_pack);
        initView();
        handler = new Handler();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_pack==null || bitmap_background_pack.isRecycled()) {
            bitmap_background_pack = BitmapFactory.decodeResource(getResources(), R.mipmap.background_pack);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_pack));
    }

    protected void initView() {
        super.initView();
        ll_hint_pack = findViewById(R.id.ll_hint_pack);
        translateAnimationStart(ll_hint_pack, 0, -40, 800, Integer.MAX_VALUE, true);
        iv_dumpling = (ImageView) findViewById(R.id.iv_dumpling);
        iv_dumpling.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                UIHelper.openPutActivity(this);
                break;
            case R.id.iv_dumpling:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(ll_hint_pack.getVisibility()==View.VISIBLE) {
                            iv_dumpling.setVisibility(View.VISIBLE);
                            translateAnimationStop(ll_hint_pack);
                            ll_hint_pack.setVisibility(View.GONE);
                            AnimationDrawable anim_dumpling = (AnimationDrawable) findViewById(R.id.iv_dumpling).getBackground();
                            anim_dumpling.start();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animation(0);
                                }
                            }, 2200);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_pack != null && !bitmap_background_pack.isRecycled()) {
            bitmap_background_pack.isRecycled();
            bitmap_background_pack = null;
            System.gc();
        }
    }


    /**
     * 五个面皮的动画
     * @param position 索引标记移动五个小面皮的哪一个
     */
    private void animation(final int position) {
        SoundUtil.playSounds(SoundUtil.TWO_THREE, 0, getApplicationContext());
        View view;
        switch (position) {
            case 0:
                view = findViewById(R.id.iv_dumpling);
                break;
            case 1:
                view = findViewById(R.id.iv_skin_one);
                break;
            case 2:
                view = findViewById(R.id.iv_skin_two);
                break;
            case 3:
                view = findViewById(R.id.iv_skin_three);
                break;
            case 4:
                view = findViewById(R.id.iv_skin_four);
                break;
            case 5:
                view = findViewById(R.id.iv_skin_five);
                break;
            default:
                view = findViewById(R.id.iv_skin_five);
                break;
        }
        /** 直接将带馅的皮图片替换成包好的饺子 */
        if(position > 0) {
            view.setBackgroundResource(R.mipmap.little_dumpling);
        }
        /** 包好的饺子飞出屏幕 */
        ObjectAnimator skinAnimatorX = ObjectAnimator.ofFloat(view, "translationX", 0, (view.getX()==0)?PhoneHeight:(PhoneWidth-view.getX()));
        ObjectAnimator skinAnimatorY = ObjectAnimator.ofFloat(view, "translationY", 0, (view.getY()==0)?0:(PhoneHeight/2-view.getY()));
        skinAnimatorX.setDuration(1000);
        skinAnimatorY.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.play(skinAnimatorX).with(skinAnimatorY);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                /** 最后，皮移除屏幕 */
                switch (position) {
                    case 0:
                        animation(1);
                        break;
                    case 1:
                        animation(2);
                        break;
                    case 2:
                        animation(3);
                        break;
                    case 3:
                        animation(4);
                        break;
                    case 4:
                        animation(5);
                        break;
                    case 5:
                        hintToNext();
                        break;
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
