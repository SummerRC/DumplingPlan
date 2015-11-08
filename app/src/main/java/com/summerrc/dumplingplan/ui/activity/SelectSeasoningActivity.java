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
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 选择调料界面
 */
public class SelectSeasoningActivity extends Activity implements View.OnClickListener{
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seasoning);
        initView();
        handler = new Handler();
    }

    private void initView() {
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_ONE);
                break;
            case R.id.iv_two:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_TWO);
                break;
            case R.id.iv_three:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_THREE);
                break;
            case R.id.iv_four:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_FOUR);
                break;
            case R.id.iv_five:
                UIHelper.openFoodDescriptionActivity(this, IntentConstant.ACTIVITY_FROM_SELECT_SEASONING, 0, FoodTypeManager.Food.O_FIVE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            FoodTypeManager.Food food = (FoodTypeManager.Food)data.getSerializableExtra(IntentConstant.SELECTED_FOOD);
            switch (food) {
                case O_ONE:
                    findViewById(R.id.iv_one).setVisibility(View.INVISIBLE);
                    break;
                case O_TWO:
                    findViewById(R.id.iv_two).setVisibility(View.INVISIBLE);
                    break;
                case O_THREE:
                    findViewById(R.id.iv_three).setVisibility(View.INVISIBLE);
                    break;
                case O_FOUR:
                    findViewById(R.id.iv_four).setVisibility(View.INVISIBLE);
                    break;
                case O_FIVE:
                    findViewById(R.id.iv_five).setVisibility(View.INVISIBLE);
                    break;
                default:
                    return;
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    UIHelper.openStuffingActivity(SelectSeasoningActivity.this);
                }
            }, 300);
        }
    }
}
