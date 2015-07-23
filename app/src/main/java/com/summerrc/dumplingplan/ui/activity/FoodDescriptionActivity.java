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
import android.widget.TextView;
import android.widget.Toast;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.SoundUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author SummerRC on 2015.07.12
 * description : 点击食材或者调料弹出这个Activity,背景透明位置居中
 */
public class FoodDescriptionActivity extends Activity implements View.OnClickListener{
    private FoodTypeManager.Food food;
    private Bitmap bitmap_background_board_one;
    private Bitmap bitmap_select_food;
    private String ACTIVITY_TYPE;
    private int number = 0;                 //选择调料的份数
    private GameDataManager gameDataManager;

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
        gameDataManager = GameDataManager.init(getApplicationContext());
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_board_one == null || bitmap_background_board_one.isRecycled()) {
            if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_FOOD)) {
                bitmap_background_board_one = BitmapFactory.decodeResource(getResources(), R.mipmap.food_description);
            } else {
                bitmap_background_board_one = BitmapFactory.decodeResource(getResources(), R.mipmap.seasoning_description);
            }
        }
        findViewById(R.id.viewContainer).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_board_one));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap_background_board_one != null && !bitmap_background_board_one.isRecycled()) {
            bitmap_background_board_one.isRecycled();
            bitmap_background_board_one = null;
        }
        if (bitmap_select_food != null && !bitmap_select_food.isRecycled()) {
            bitmap_select_food.isRecycled();
            bitmap_select_food = null;
        }
        System.gc();
    }

    private void initView() {
        /** 显示被介绍的食材 */
        bitmap_select_food = BitmapFactory.decodeResource(getResources(), FoodTypeManager.getFoodResourceId(food));
        findViewById(R.id.iv_food_description).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_select_food));
        /** 设置相关的监听 */
        findViewById(R.id.iv_yes).setOnClickListener(this);
        findViewById(R.id.iv_no).setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.iv_decrease).setOnClickListener(this);
        if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)) {
            findViewById(R.id.ll_number).setVisibility(View.VISIBLE);
            number = gameDataManager.getSeasoningNumberMap(food);
            ((TextView)findViewById(R.id.tv_number)).setText(number + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_yes:
                SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
                if(isAdd()) {
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
                }
                break;
            case R.id.iv_no:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConstant.SELECTED_FOOD, FoodTypeManager.Food.DEFAULT);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_add:
                SoundUtil.playSounds(SoundUtil.MINUS, 0, getApplicationContext());
                number++;
                ((TextView)findViewById(R.id.tv_number)).setText(number+"");
                break;
            case R.id.iv_decrease:
                SoundUtil.playSounds(SoundUtil.MINUS, 0, getApplicationContext());
                if(number>0) {
                    number--;
                    ((TextView)findViewById(R.id.tv_number)).setText(number+"");
                }
                break;
        }
    }

    /**
     * @return true : 可以添加   false ：存在或已满3种
     */
    private boolean isAdd() {
        ArrayList<FoodTypeManager.Food> list = new ArrayList<>();
        if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_FOOD)) {                    //食材
            list = gameDataManager.getFoodList();
        } else if(ACTIVITY_TYPE.equals(IntentConstant.ACTIVITY_FROM_SELECT_SEASONING)) {        //调料
            list = gameDataManager.getSeasoningList();
        }
        if(list.size() >= 3) {
            Toast.makeText(this, "最多只能选择3种食材！", Toast.LENGTH_SHORT).show();
            return false;
        }
        for(int i=0; i< list.size(); i++) {
            if(list.get(i) == food){
                Toast.makeText(this, "菜篮中已存在，请不要重复选取！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
