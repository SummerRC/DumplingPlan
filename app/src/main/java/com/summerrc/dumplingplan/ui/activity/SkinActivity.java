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
 * description : 擀面界面
 */
public class SkinActivity extends BaseActivity{
    private Bitmap bitmap_background_skin;
    private ObjectAnimator rollPinAnimator;               //擀面动画
    private AnimatorSet skinAnimatorSet;                  //面皮动画
    private ImageView iv_skin_big;                        //面皮
    private ImageView iv_roll_pin;                        //面杆
    private boolean rollPinAnimatorIsStart = false;       //擀面动画是否开始了
    private float targetX = 0;                            //面的横坐标
    private float targetY = 0;                            //面的纵坐标

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
            case R.id.iv_next:
                UIHelper.openAddStuffingActivity(this);
                break;
            case R.id.iv_rolling_pin:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(findViewById(R.id.rl_hint_skin).getVisibility()==View.VISIBLE) {
                            findViewById(R.id.rl_hint_skin).setVisibility(View.GONE);
                        }
                        if(rollPinAnimatorIsStart) {
                            rollPinAnimator.resume();
                            skinAnimatorSet.resume();
                        } else {
                            rollPinAnimator.start();
                            skinAnimatorSet.start();
                            rollPinAnimatorIsStart = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        rollPinAnimator.pause();
                        skinAnimatorSet.pause();
                        break;
                }
        }
        return true;
    }

    /**
     *初始化动画：擀面动画
     */
    private void initAnimator() {
        /** 擀面棍来回移动6次，每次1秒钟 */
        rollPinAnimator = ObjectAnimator.ofFloat(iv_roll_pin, "translationY", -40f , 40f);
        rollPinAnimator.setDuration(1000);
        rollPinAnimator.setRepeatCount(5);
        rollPinAnimator.setRepeatMode(ValueAnimator.REVERSE);
        rollPinAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSetStart(iv_skin_big);
                animation(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        /** 面皮从小变大 */
        skinAnimatorSet = new AnimatorSet();
        ObjectAnimator skinAnimatorX = ObjectAnimator.ofFloat(iv_skin_big, "scaleX", 0.2F, 0.9F);
        ObjectAnimator skinAnimatorY = ObjectAnimator.ofFloat(iv_skin_big, "scaleY", 0.2F, 0.9F);
        skinAnimatorSet.setDuration(6000);
        skinAnimatorSet.play(skinAnimatorX).with(skinAnimatorY);
    }

    /**
     * 五个面皮的动画
     * @param position 索引标记移动五个小面皮的哪一个
     */
    private void animation(final int position) {
        View view;
        switch (position) {
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
        }
        if(targetX == 0) {
            targetX = iv_skin_big.getX();
            targetY = iv_skin_big.getY();
        }
        /** 皮先移动到指定位置 */
        ObjectAnimator skinAnimatorX = ObjectAnimator.ofFloat(view, "translationX", 0, targetX-view.getX()+50);
        ObjectAnimator skinAnimatorY = ObjectAnimator.ofFloat(view, "translationY", 0, targetY-view.getY()+50);
        skinAnimatorX.setDuration(200);
        skinAnimatorY.setDuration(200);
        /** 然后擀面棍来回移动6次，每次0.2秒钟 */
        ObjectAnimator rollPinAnimatorXY = ObjectAnimator.ofFloat(iv_roll_pin, "translationY", -40f , 40f);
        rollPinAnimatorXY.setDuration(200);
        rollPinAnimatorXY.setRepeatCount(5);
        rollPinAnimatorXY.setRepeatMode(ValueAnimator.REVERSE);
        /** 同时皮由小变大 */
        ObjectAnimator skinAnimatorX1 = ObjectAnimator.ofFloat(view, "scaleX", 0.2F, 1.8F);
        ObjectAnimator skinAnimatorY1 = ObjectAnimator.ofFloat(view, "scaleY", 0.2F, 1.8F);
        skinAnimatorX1.setDuration(1000);
        skinAnimatorY1.setDuration(1000);
        /** 皮飞到右上角的碗里面最后淡出消失 */
        int x = (int)findViewById(R.id.iv_basket).getX() - (int)view.getX();
        int y = (int)findViewById(R.id.iv_basket).getY() - (int)view.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "translationX", targetX-view.getX()+50, x);
        anim1.setDuration(500);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "translationY", targetY-view.getY()+50, y);
        anim2.setDuration(500);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        anim3.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.play(skinAnimatorX).with(skinAnimatorY);
        set.play(rollPinAnimatorXY).with(skinAnimatorX1).with(skinAnimatorY1).after(skinAnimatorX);
        set.play(anim1).with(anim2).after(rollPinAnimatorXY);
        set.play(anim3).after(anim1);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /** 最后，皮移除屏幕 */
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

    /**
     * 皮飞入菜篮然后淡出消失，并且提示消失下一关按钮显示
     * @param view 选中的食材
     */
    private void animatorSetStart(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        int x = (int)findViewById(R.id.iv_basket).getX() - (int)view.getX();
        int y = (int)findViewById(R.id.iv_basket).getY() - (int)view.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "translationX", 0f , x);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "translationY", 0f , y);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        animatorSet.play(anim1).with(anim2);
        animatorSet.play(anim3).after(anim2);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

}
