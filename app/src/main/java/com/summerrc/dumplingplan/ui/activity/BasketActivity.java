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
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.SoundUtil;

import java.util.ArrayList;

/**
 * @author SummerRC on 2015.07.12
 * description : 点击菜篮弹出这个Activity,背景透明位置居中
 */
public class BasketActivity extends Activity implements View.OnClickListener{
    private Bitmap bitmap_background_basket_popup_box;
    private String ACTIVITY_TYPE;                       //启动菜篮Activity的Activity
    private ArrayList<FoodTypeManager.Food> list;       //食材集合或者调料集合，有启动Activity的类型决定
    private GameDataManager gameDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basket);
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_basket_popup_box==null || bitmap_background_basket_popup_box.isRecycled()) {
            bitmap_background_basket_popup_box = BitmapFactory.decodeResource(getResources(), R.mipmap.background_basket_popup_box);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_basket_popup_box));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_basket_popup_box != null && !bitmap_background_basket_popup_box.isRecycled()) {
            bitmap_background_basket_popup_box.isRecycled();
            bitmap_background_basket_popup_box = null;
            System.gc();
        }
    }

    private void initData() {
        gameDataManager = GameDataManager.init(getApplicationContext());
        ACTIVITY_TYPE = getIntent().getStringExtra(IntentConstant.ACTIVITY_TYPE);
        if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_FOOD)) {                //食材
            list =gameDataManager.getFoodList();
        } else if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)){     //调料
            list = gameDataManager.getSeasoningList();
        }
    }

    private void initView() {
        for(int i=0; i<list.size(); i++) {
            switch (i) {
                case 0:
                    findViewById(R.id.iv_one).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_one).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_one).setOnClickListener(this);
                    findViewById(R.id.iv_one).setBackgroundResource(FoodTypeManager.getHashMap(list.get(i)).get(FoodTypeManager.UNLOCK));
                    break;
                case 1:
                    findViewById(R.id.iv_two).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_two).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_two).setOnClickListener(this);
                    findViewById(R.id.iv_two).setBackgroundResource(FoodTypeManager.getHashMap(list.get(i)).get(FoodTypeManager.UNLOCK));
                    break;
                case 2:
                    findViewById(R.id.iv_three).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_three).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_three).setOnClickListener(this);
                    findViewById(R.id.iv_three).setBackgroundResource(FoodTypeManager.getHashMap(list.get(i)).get(FoodTypeManager.UNLOCK));
                    break;
            }
        }
        findViewById(R.id.iv_remove_one).setOnClickListener(this);
        findViewById(R.id.iv_remove_two).setOnClickListener(this);
        findViewById(R.id.iv_remove_three).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_remove_one:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)){     //调料
                    gameDataManager.setSeasoningNumberMap(list.get(0), 0);
                }
                list.remove(0);
                findViewById(R.id.iv_remove_one).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_one).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_remove_two:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)){     //调料
                    gameDataManager.setSeasoningNumberMap(list.get(1), 0);
                }
                list.remove(1);
                findViewById(R.id.iv_remove_two).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_two).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_remove_three:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)){     //调料
                    gameDataManager.setSeasoningNumberMap(list.get(2), 0);
                }
                list.remove(2);
                findViewById(R.id.iv_remove_three).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_three).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_close:
                SoundUtil.playSounds(SoundUtil.BACK, 0, getApplicationContext());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
