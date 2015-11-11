package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.IntentConstant;

/**
 * @author SummerRC on 2015.07.12
 * description : 点击食材或者调料弹出这个Activity,背景透明位置居中
 */
public class FoodDescriptionActivity extends Activity implements View.OnClickListener{
    private FoodTypeManager.Food food;
    private String ACTIVITY_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** 点击外围位置悬浮activity不消失,这里已经在style.xml中设置了 */
        //this.setFinishOnTouchOutside(false);
        food = (FoodTypeManager.Food)getIntent().getSerializableExtra(IntentConstant.SELECTED_FOOD);
        ACTIVITY_TYPE = getIntent().getStringExtra(IntentConstant.ACTIVITY_TYPE);
        setContentView(R.layout.activity_food_description);
        initView();
    }




    private void initView() {
        /** 显示被介绍的食材 */
        Bitmap bitmap_select_food = BitmapFactory.decodeResource(getResources(), FoodTypeManager.getFoodResourceId(food));
        findViewById(R.id.viewContainer).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_select_food));
        /** 设置相关的监听 */
        findViewById(R.id.iv_yes).setOnClickListener(this);
        findViewById(R.id.iv_no).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_yes:
//                SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
                /*if(isAdd()) {
                    if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_FOOD)) {               //食材
                        if(gameDataManager.getFoodList().size() >= 3) {
                            Toast.makeText(this, "最多一次只能选择三种食材！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        gameDataManager.setFoodList(food);
                    } else if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)) {   //调料
                        if(number == 0) {
                            Toast.makeText(this, "请至少选择一份调料！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        gameDataManager.setSeasoningNumberMap(food, number);
                        gameDataManager.setSeasoningList(food);
                    }
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(IntentConstant.SELECTED_FOOD, food);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }*/

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConstant.SELECTED_FOOD, food);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_no:
//                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                finish();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }
}
