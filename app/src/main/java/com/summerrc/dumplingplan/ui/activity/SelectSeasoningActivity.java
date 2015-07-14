package com.summerrc.dumplingplan.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 选择调料界面
 */
public class SelectSeasoningActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_salt;
    private ImageView iv_sauce;
    private ImageView iv_oil;
    private Bitmap bitmap_background_select_seasoning;
    private static final int SELECT_SEASONING_ACTIVITY = 1;          //onActivityForResult方法回调的标识符
    private static final int BASKET_ACTIVITY = 2;                    //onActivityForResult方法回调的标识符
    private float x_location;
    private float y_location;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_select_seasoning);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_select_seasoning==null || bitmap_background_select_seasoning.isRecycled()) {
            bitmap_background_select_seasoning = BitmapFactory.decodeResource(getResources(), R.mipmap.background_select_food);
        }
        findViewById(R.id.rl_background_select_seasoning).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_select_seasoning));
    }

    protected void initView() {
        super.initView();
        findViewById(R.id.iv_basket).setOnClickListener(this);
        iv_oil = (ImageView)findViewById(R.id.iv_oil);
        iv_salt = (ImageView)findViewById(R.id.iv_salt);
        iv_sauce = (ImageView)findViewById(R.id.iv_sauce);
        iv_oil.setOnClickListener(this);
        iv_salt.setOnClickListener(this);
        iv_sauce.setOnClickListener(this);
        findViewById(R.id.iv_basket).setOnClickListener(this);
        translateAnimationStart(findViewById(R.id.ll_hint_select_food), 0, 50, 800, Integer.MAX_VALUE, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openStuffingActivity(this);
                break;
            case R.id.iv_salt:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, SELECT_SEASONING_ACTIVITY, FoodTypeManager.Food.SALT);
                break;
            case R.id.iv_sauce:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, SELECT_SEASONING_ACTIVITY, FoodTypeManager.Food.SAUCE);
                break;
            case R.id.iv_oil:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, SELECT_SEASONING_ACTIVITY, FoodTypeManager.Food.OIL);
                break;
            case R.id.iv_basket:
                UIHelper.openBasketActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, BASKET_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_select_seasoning != null && !bitmap_background_select_seasoning.isRecycled()) {
            bitmap_background_select_seasoning.isRecycled();
            bitmap_background_select_seasoning = null;
            System.gc();
        }
    }

    /**
     * 调料飞入菜篮然后淡出消失，并且提示消失下一关按钮显示
     * @param view 选中的调料
     */
    private void animatorSetStart(final View view) {
        x_location = view.getX();
        y_location = view.getY();
        if(findViewById(R.id.ll_hint_select_food).getVisibility()==View.VISIBLE) {
            translateAnimationStop(findViewById(R.id.ll_hint_select_food));
            findViewById(R.id.ll_hint_select_food).setVisibility(View.GONE);
        }
        super.hintToNext();

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
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                /** 动画结束之后放回原位置 */
                view.clearAnimation();
                view.setX(x_location);
                view.setY(y_location);
                ObjectAnimator.ofFloat(view,"alpha",0f,1f).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * FoodDescriptionActivity(食材详情)结束后的回调
     * @param requestCode   标示符，标识启动哪个Activity
     * @param resultCode      标示符 启动结果成功还是失败
     * @param data                 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SELECT_SEASONING_ACTIVITY && resultCode==RESULT_OK) {
            Bundle bundle = data.getExtras();
            FoodTypeManager.Food food = (FoodTypeManager.Food)bundle.getSerializable(IntentConstant.SELECTED_FOOD);
            switch (food) {
                case SALT:
                    animatorSetStart(iv_salt);
                    break;
                case OIL:
                    animatorSetStart(iv_oil);
                    break;
                case SAUCE:
                    animatorSetStart(iv_sauce);
                    break;
                case DEFAULT:
                    if(GameDataManager.init().getSeasoningList().size()==0) {
                        Toast.makeText(this, "亲，你一种调料都不选取，皇上会生气的哦", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.iv_next).setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        } else if(requestCode==BASKET_ACTIVITY) {
            if(GameDataManager.init().getSeasoningList().size() == 0) {
                Toast.makeText(this, "亲，你一种调料都不选取，皇上会生气的哦", Toast.LENGTH_SHORT).show();
                findViewById(R.id.iv_next).setVisibility(View.INVISIBLE);
            }
        }
    }

}
