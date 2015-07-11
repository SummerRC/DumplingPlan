package com.summerrc.dumplingplan.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.ui.adapter.SelectFoodPagerAdapter;
import com.summerrc.dumplingplan.utils.UIHelper;
import java.util.ArrayList;

public class SelectFoodActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_select_food;
    private ViewPager viewPager;
    private int index = 0;
    private ImageView iv_eggplant;
    private ImageView iv_beef;
    private ImageView iv_lemon;
    private static final int SELECT_FOOD_ACTIVITY = 1;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_select_food);
        initView();
        translateAnimationStart(findViewById(R.id.ll_hint_select_food), 50, 60, 800, Integer.MAX_VALUE, false);
        translateAnimationStart(findViewById(R.id.iv_right_arrow), 30, 0, 500, Integer.MAX_VALUE, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_select_food==null || bitmap_background_select_food.isRecycled()) {
            bitmap_background_select_food = BitmapFactory.decodeResource(getResources(), R.mipmap.background_select_food);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_select_food));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_select_food != null && !bitmap_background_select_food.isRecycled()) {
            bitmap_background_select_food.isRecycled();
            bitmap_background_select_food = null;
            System.gc();
        }
    }

    protected void initView() {
        super.initView();
        findViewById(R.id.iv_basket).setOnClickListener(this);
        findViewById(R.id.iv_right_arrow).setOnClickListener(this);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setClickable(false);
        viewPager.setFocusable(false);
        ArrayList<View> list = new ArrayList<>();
        View view_one = View.inflate(this, R.layout.viewpager_item_one, null);
        View view_two = View.inflate(this, R.layout.viewpager_item_two, null);
        View view_three = View.inflate(this, R.layout.viewpager_item_three, null);
        View view_four = View.inflate(this, R.layout.viewpager_item_four, null);
        list.add(view_one);
        list.add(view_two);
        list.add(view_three);
        list.add(view_four);

        iv_eggplant = (ImageView)view_one.findViewById(R.id.iv_eggplant);
        iv_beef = (ImageView)view_two.findViewById(R.id.iv_beef);
        iv_lemon = (ImageView)view_three.findViewById(R.id.iv_lemon);
        iv_eggplant.setOnClickListener(this);
        iv_beef.setOnClickListener(this);
        iv_lemon.setOnClickListener(this);

        viewPager.setAdapter(new SelectFoodPagerAdapter(list, this));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                setLeftArrow(position % 4);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openSelectSeasoningActivity(this);
                break;
            case R.id.iv_eggplant:
                UIHelper.openFoodDescriptionActivity(this, SELECT_FOOD_ACTIVITY, FoodTypeManager.Food.EGGPLANT);
                break;
            case R.id.iv_beef:
                UIHelper.openFoodDescriptionActivity(this, SELECT_FOOD_ACTIVITY, FoodTypeManager.Food.BEEF);
               // animatorSetStart(iv_beef);
                break;
            case R.id.iv_lemon:
                UIHelper.openFoodDescriptionActivity(this, SELECT_FOOD_ACTIVITY, FoodTypeManager.Food.LEMON);
               //animatorSetStart(iv_lemon);
                break;
            case R.id.iv_left_arrow:
                //todo 有问题
                //viewPager.setCurrentItem((index-1) % 4);
                break;
            case R.id.iv_right_arrow:
                //viewPager.setCurrentItem((index+1) % 4);
                break;
            case R.id.iv_basket:
                UIHelper.openBasketActivity(this);
                break;
        }
    }

    /**
     *     ViewPager在加载页卡的时候设置加载的页卡的物体的背景图，并设置监听，
     * 这里并没有手动加载图片，留在以后做内存优化
     * //todo 手动加载图片并释放内存
     * @param k 加载的当前页卡的索引
     * @param view 加载的当前页卡
     */
    public void setListener(int k, View view) {
        switch (k) {
            case 0:         //蔬菜
                view.findViewById(R.id.iv_eggplant).setOnClickListener(this);
                break;
            case 1:         //肉类
                view.findViewById(R.id.iv_beef).setOnClickListener(this);
                break;
            case 2:         //水果
                view.findViewById(R.id.iv_lemon).setOnClickListener(this);
                break;
            case 3:         //空
                break;
        }
    }

    /**
     * 左箭头在第一页不显示
     * @param position ViewPager当前显示的页卡
     */
    public void setLeftArrow(int position) {
        if(position == 0) {
            findViewById(R.id.iv_left_arrow).setVisibility(View.GONE);
        } else {
            findViewById(R.id.iv_left_arrow).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_left_arrow).setOnClickListener(this);
        }
    }

    /**
     * 食材飞入菜篮，并且提示消失下一关按钮显示
     * @param view 选中的食材
     */
    private void animatorSetStart(View view) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SELECT_FOOD_ACTIVITY && resultCode==RESULT_OK) {
            Bundle bundle = data.getExtras();
            FoodTypeManager.Food food = (FoodTypeManager.Food)bundle.getSerializable(IntentConstant.Selected_food);
            switch (food) {
                case EGGPLANT:
                    animatorSetStart(iv_eggplant);
                    break;
                case BEEF:
                    animatorSetStart(iv_beef);
                    break;
                case LEMON:
                    animatorSetStart(iv_lemon);
                    break;
            }
        }
    }
}
