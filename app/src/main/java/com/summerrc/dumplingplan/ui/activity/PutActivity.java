package com.summerrc.dumplingplan.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 下饺子界面
 */
public class PutActivity extends BaseActivity  {
    private Bitmap bitmap_background_put;
    private ImageView iv_cover_pad;                     //放饺子的盖
    private ImageView iv_pod;                           //锅
    private AnimatorSet animatorSet;                    //饺子播放动画

    @Override
    protected void setView() {
        setContentView(R.layout.activity_put);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_put==null || bitmap_background_put.isRecycled()) {
            bitmap_background_put = BitmapFactory.decodeResource(getResources(), R.mipmap.background_put);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_put));
    }

    protected void initView() {
        super.initView();
        iv_cover_pad = (ImageView) findViewById(R.id.iv_cover_pad);
        iv_cover_pad.setOnTouchListener(this);
        iv_pod = (ImageView) findViewById(R.id.iv_pod);
        findViewById(R.id.iv_next).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_put != null && !bitmap_background_put.isRecycled()) {
            bitmap_background_put.isRecycled();
            bitmap_background_put = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openShakeActivity(this);
                break;
            case R.id.iv_cover_pad:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(findViewById(R.id.ll_hint_slide).getVisibility()==View.VISIBLE) {
                            findViewById(R.id.ll_hint_slide).setVisibility(View.GONE);
                            animation(1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                break;
            case R.id.iv_switch:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSet.pause();
                    findViewById(R.id.ll_hint_click_switch).setVisibility(View.GONE);
                    int count = ((ObjectAnimator)animatorSet.getChildAnimations().get(5)).getRepeatCount();
                    GameDataManager.init().setCount(count);
                    super.hintToNext();
                }
                break;
        }
        return true;
    }

    /**
     * 盖子移动的动画
     */
    private void animatorSetStart() {
        AnimatorSet animatorSet = new AnimatorSet();
        int x = (int)iv_pod.getX() - (int)iv_cover_pad.getX();
        int y = (int)iv_pod.getY() - (int)iv_cover_pad.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_cover_pad, "translationX", 0f , x+240);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_cover_pad, "translationY", 0f , y-60);
        animatorSet.play(anim1).with(anim2);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    /**
     * 六个饺子下水的动画
     * @param position 索引标记移动五个小面皮的哪一个
     */
    private void animation(final int position) {
        View view;
        switch (position) {
            case 1:
                view = findViewById(R.id.iv_dumpling_one);
                break;
            case 2:
                view = findViewById(R.id.iv_dumpling_two);
                break;
            case 3:
                view = findViewById(R.id.iv_dumpling_three);
                break;
            case 4:
                view = findViewById(R.id.iv_dumpling_four);
                break;
            case 5:
                view = findViewById(R.id.iv_dumpling_five);
                break;
            case 6:
                view = findViewById(R.id.iv_dumpling_six);
                break;
            default:
                view = findViewById(R.id.iv_dumpling_six);
        }
        /** 皮先移动到指定位置 */
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "translationX", 0, PhoneWidth/2-view.getX()-(position-2)*40);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "translationY", 0, PhoneHeight/2-view.getY());
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view, "rotation", 0f, 60);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(view, "translationY", PhoneHeight/2-view.getY(), PhoneHeight/2-view.getY()+20);
        anim1.setDuration(1000);
        anim2.setDuration(1000);
        anim3.setDuration(1000);
        anim4.setDuration(200);
        anim5.setDuration(200);
        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2).with(anim3);
        set.play(anim4).with(anim5).after(anim1);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                switch (position) {
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
                        animation(6);
                        break;
                    case 6:
                        findViewById(R.id.ll_hint_click_switch).setVisibility(View.VISIBLE);
                        findViewById(R.id.iv_switch).setVisibility(View.VISIBLE);
                        findViewById(R.id.iv_switch).setOnTouchListener(PutActivity.this);
                        PutActivity.this.AlphaAnimator();
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

    private View getView(int position) {
        View view;
        switch (position) {
            case 1:
                view = findViewById(R.id.iv_dumpling_one);
                break;
            case 2:
                view = findViewById(R.id.iv_dumpling_two);
                break;
            case 3:
                view = findViewById(R.id.iv_dumpling_three);
                break;
            case 4:
                view = findViewById(R.id.iv_dumpling_four);
                break;
            case 5:
                view = findViewById(R.id.iv_dumpling_five);
                break;
            case 6:
                view = findViewById(R.id.iv_dumpling_six);
                break;
            default:
                view = findViewById(R.id.iv_dumpling_six);
        }
       return view;
    }

    private void AlphaAnimator() {
        animatorSet = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(getView(1), "alpha", 0f, 1f);
        anim1.setDuration(1000);
        anim1.setRepeatCount(Integer.MAX_VALUE);
        anim1.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(getView(2), "alpha", 0f, 1f);
        anim2.setDuration(1000);
        anim2.setRepeatCount(Integer.MAX_VALUE);
        anim2.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(getView(3), "alpha", 0f, 1f);
        anim3.setDuration(1000);
        anim3.setRepeatCount(Integer.MAX_VALUE);
        anim3.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(getView(4), "alpha", 0f, 1f);
        anim4.setDuration(1000);
        anim4.setRepeatCount(Integer.MAX_VALUE);
        anim4.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(getView(5), "alpha", 0f, 1f);
        anim5.setDuration(1000);
        anim5.setRepeatCount(Integer.MAX_VALUE);
        anim5.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator anim6 = ObjectAnimator.ofFloat(getView(6), "alpha", 0f, 1f);
        anim6.setDuration(1000);
        anim6.setRepeatCount(Integer.MAX_VALUE);
        anim6.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet.play(anim1).with(anim2).with(anim3).with(anim4).with(anim5).with(anim6);
        animatorSet.start();
    }

}
