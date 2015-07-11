package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;

import java.util.ArrayList;

/**
 * 菜篮的弹出界面
 */
public class BasketActivity extends Activity implements View.OnClickListener{
    private Bitmap bitmap_background_basket_popup_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_basket);
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

    private void initView() {
        ArrayList<FoodTypeManager.Food> foodList = GameDataManager.init().getFoodList();
        for(int i=0; i<foodList.size(); i++) {
            switch (i) {
                case 0:
                    findViewById(R.id.iv_one).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_one).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_one).setOnClickListener(this);
                    findViewById(R.id.iv_one).setBackgroundResource(FoodTypeManager.getHashMap(foodList.get(i)).get(FoodTypeManager.UNLOCK));
                    break;
                case 1:
                    findViewById(R.id.iv_two).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_two).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_two).setOnClickListener(this);
                    findViewById(R.id.iv_two).setBackgroundResource(FoodTypeManager.getHashMap(foodList.get(i)).get(FoodTypeManager.UNLOCK));
                    break;
                case 2:
                    findViewById(R.id.iv_three).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_three).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_remove_three).setOnClickListener(this);
                    findViewById(R.id.iv_three).setBackgroundResource(FoodTypeManager.getHashMap(foodList.get(i)).get(FoodTypeManager.UNLOCK));
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
                GameDataManager.init().getFoodList().remove(0);
                findViewById(R.id.iv_remove_one).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_one).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_remove_two:
                GameDataManager.init().getFoodList().remove(1);
                findViewById(R.id.iv_remove_two).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_two).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_remove_three:
                GameDataManager.init().getFoodList().remove(2);
                findViewById(R.id.iv_remove_three).setVisibility(View.INVISIBLE);
                findViewById(R.id.iv_three).setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
