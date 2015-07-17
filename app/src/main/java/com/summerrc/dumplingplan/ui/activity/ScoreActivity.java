package com.summerrc.dumplingplan.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.StuffTypeManager;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 给饺子加馅界面
 */
public class ScoreActivity extends BaseActivity {
    private Bitmap bitmap_background_pack;
    private ImageView iv_spoon;                         //勺子
    private GameDataManager gameDataManager;
    private boolean TOUCH_ABLE = true;                  //可点击

    @Override
    protected void setView() {
        setContentView(R.layout.activity_score);
        initView();
        gameDataManager = GameDataManager.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_pack==null || bitmap_background_pack.isRecycled()) {
            bitmap_background_pack = BitmapFactory.decodeResource(getResources(), R.mipmap.background_add_stuffing);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_pack));
    }

    protected void initView() {
        super.initView();
        iv_spoon = (ImageView) findViewById(R.id.iv_spoon);
        iv_spoon.setOnTouchListener(this);
        findViewById(R.id.iv_skin_one).setOnTouchListener(this);
        findViewById(R.id.iv_skin_two).setOnTouchListener(this);
        findViewById(R.id.iv_skin_three).setOnTouchListener(this);
        findViewById(R.id.iv_skin_four).setOnTouchListener(this);
        findViewById(R.id.iv_skin_five).setOnTouchListener(this);
        findViewById(R.id.iv_skin_six).setOnTouchListener(this);
        /* 根据馅的种类来设置图片 */
        int stuffResourceId = StuffTypeManager.getStuffResourceId(GameDataManager.init().getStuffType());
        findViewById(R.id.iv_stuffing).setBackgroundResource(stuffResourceId);
        translateAnimationStart(findViewById(R.id.ll_hint_click_skin), 0, 50, 800, Integer.MAX_VALUE, false);
    }

    @Override
    public void onClick(View v) {
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                /** 记录每个饺子大小 */
                GameDataManager.init().setDumplingTypeEntities();
                UIHelper.openPackActivity(this);
                break;
            case R.id.iv_skin_one:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(1);
                }
                break;
            case R.id.iv_skin_two:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(2);
                }
                break;
            case R.id.iv_skin_three:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(3);
                }
                break;
            case R.id.iv_skin_four:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(4);
                }
                break;
            case R.id.iv_skin_five:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(5);
                }
                break;
            case R.id.iv_skin_six:
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    animatorSetStart(6);
                }
                break;
        }
        return true;
    }

    /**
     * 勺子移动的动画
     * @param position 标识皮
     */
    private void  animatorSetStart(final int position) {
        if(gameDataManager.getStuffNum(position) >= 3) {
            Toast.makeText(this, "太多了", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!TOUCH_ABLE) {
            return;
        } else {
            TOUCH_ABLE = false;
        }
        if(findViewById(R.id.ll_hint_click_skin).getVisibility()==View.VISIBLE) {
            translateAnimationStop(findViewById(R.id.ll_hint_click_skin));
            findViewById(R.id.ll_hint_click_skin).setVisibility(View.INVISIBLE);
        }
        gameDataManager.addStuffNum(position);
        final View view;
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
            case 6:
                view = findViewById(R.id.iv_skin_six);
                break;
            default:
                view = findViewById(R.id.iv_skin_one);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        final float x0 = iv_spoon.getX();
        final float y0 = iv_spoon.getY();
        /** 勺先移动到馅上然后勺子旋转30度 */
        float x1 = findViewById(R.id.iv_stuffing).getX() ;
        float y1 = findViewById(R.id.iv_stuffing).getY() ;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_spoon, "translationX", 0f , x1-50);
        animator1.setDuration(500);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_spoon, "translationY", 0f , y1+25);
        animator2.setDuration(500);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv_spoon, "rotationY", 0, 30f);
        animator3.setDuration(500);
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                iv_spoon.setBackgroundResource(R.mipmap.wood_spoon_full);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        /** 勺子移回到原位置 */
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(iv_spoon, "translationX", x1-50, 0f);
        animator4.setDuration(200);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(iv_spoon, "translationY", y1+25, 0f);
        animator5.setDuration(200);
        /** 勺子移动到皮上 */
        int x =  (int)view.getX() -   (int)iv_spoon.getX();
        int y = (int)iv_spoon.getY()  - (int)view.getY();
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(iv_spoon, "translationX", 0f , x+100);
        animator6.setDuration(1000);
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(iv_spoon, "translationY", 0f , y+70);
        animator7.setDuration(1000);
        /** 勺子旋转35度 */
        ObjectAnimator animator8 = ObjectAnimator.ofFloat(iv_spoon, "rotationY", 0, 30f);
        animator8.setDuration(400);
        animator8.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /** 皮中馅数量改变 */
                setBackground(position, view);
                /** 动画结束之后放回原位置 */
                iv_spoon.clearAnimation();
                iv_spoon.setBackgroundResource(R.mipmap.wood_spoon_empty);
                iv_spoon.setX(x0);
                iv_spoon.setY(y0);
                /** 是否显示下一关 */
                setNextShow();
                /** 设置按钮可以点击 */
                TOUCH_ABLE = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(animator1).with(animator2);
        animatorSet.play(animator3).after(animator1);
        animatorSet.play(animator4).after(animator3).with(animator5);
        animatorSet.play(animator6).after(animator5).with(animator7);
        animatorSet.play(animator8).after(animator7);
        animatorSet.start();
    }


    /**
     * @param position  第几个饺子皮
     * @param view     饺子皮
     */
    private void setBackground(int position, View view) {
        switch (gameDataManager.getStuffNum(position)) {
            case 0:
                view.setBackgroundResource(R.mipmap.skin_down_middle);
                break;
            case 1:
                view.setBackgroundResource(R.mipmap.pie_less);
                break;
            case 2:
                view.setBackgroundResource(R.mipmap.pie_normal);
                break;
            case 3:
                view.setBackgroundResource(R.mipmap.pie_more);
                break;
        }
    }

    /**
     * 判断是否显示下一步提示
     */
    private void setNextShow() {
        for (int i=1; i<7; i++) {
            if(gameDataManager.getStuffNum(i) == 0){
                return;
            }
        }
        super.hintToNext();
    }

    /**
     * 在动画开始之后设置按钮不可点击，结束之后设置为可点击
     * @param onClickable true : 可点击
     */
    private void setViewClickable(boolean onClickable) {
        findViewById(R.id.iv_skin_one).setFocusable(onClickable);
        findViewById(R.id.iv_skin_two).setFocusable(onClickable);
        findViewById(R.id.iv_skin_three).setFocusable(onClickable);
        findViewById(R.id.iv_skin_four).setFocusable(onClickable);
        findViewById(R.id.iv_skin_five).setFocusable(onClickable);
        findViewById(R.id.iv_skin_six).setFocusable(onClickable);

    }

}
