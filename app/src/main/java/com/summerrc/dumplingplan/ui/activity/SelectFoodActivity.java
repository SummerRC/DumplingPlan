package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.config.MMApplication;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 *         description : 选择食材界面
 */
public class SelectFoodActivity extends Activity implements View.OnClickListener {
    private Handler handler;
    private int num = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_food);
        initView();
        handler = new Handler();
    }

    private void initView() {
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
        findViewById(R.id.iv_six).setOnClickListener(this);
        findViewById(R.id.iv_seven).setOnClickListener(this);
        findViewById(R.id.iv_eight).setOnClickListener(this);
        findViewById(R.id.iv_nine).setOnClickListener(this);
        findViewById(R.id.iv_ten).setOnClickListener(this);
        setBackground();
        setVisible();
        findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMApplication.isSelected = !MMApplication.isSelected;
                setVisible();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openWelcomeActivity(SelectFoodActivity.this);
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
                    MMApplication.isSelected_yinyue = !MMApplication.isSelected_yinyue;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.ONE);
                break;
            case R.id.iv_two:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.TWO);
                break;
            case R.id.iv_three:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.THREE);
                break;
            case R.id.iv_four:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.FOUR);
                break;
            case R.id.iv_five:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.FIVE);
                break;
            case R.id.iv_six:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.SIX);
                break;
            case R.id.iv_seven:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.SEVEN);
                break;
            case R.id.iv_eight:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.EIGHT);
                break;
            case R.id.iv_nine:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.NINE);
                break;
            case R.id.iv_ten:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_FOOD, 0, FoodTypeManager.Food.TEN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            FoodTypeManager.Food food = (FoodTypeManager.Food)data.getSerializableExtra(IntentConstant.SELECTED_FOOD);
            switch (food) {
                case ONE:
                    findViewById(R.id.iv_one).setVisibility(View.INVISIBLE);
                    break;
                case TWO:
                    findViewById(R.id.iv_two).setVisibility(View.INVISIBLE);
                    break;
                case THREE:
                    findViewById(R.id.iv_three).setVisibility(View.INVISIBLE);
                    break;
                case FOUR:
                    findViewById(R.id.iv_four).setVisibility(View.INVISIBLE);
                    break;
                case FIVE:
                    findViewById(R.id.iv_five).setVisibility(View.INVISIBLE);
                    break;
                case SIX:
                    findViewById(R.id.iv_six).setVisibility(View.INVISIBLE);
                    break;
                case SEVEN:
                    findViewById(R.id.iv_seven).setVisibility(View.INVISIBLE);
                    break;
                case EIGHT:
                    findViewById(R.id.iv_eight).setVisibility(View.INVISIBLE);
                    break;
                case NINE:
                    findViewById(R.id.iv_nine).setVisibility(View.INVISIBLE);
                    break;
                case TEN:
                    findViewById(R.id.iv_ten).setVisibility(View.INVISIBLE);
                    break;
                default:
                    return;
            }
            num++;
            if(num == 2) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UIHelper.openSelectSeasoningActivity(SelectFoodActivity.this);
                    }
                }, 500);
            }
        }
    }
}
