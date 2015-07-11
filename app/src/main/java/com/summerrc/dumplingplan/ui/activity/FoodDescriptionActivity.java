package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.UIHelper;

public class FoodDescriptionActivity extends Activity implements View.OnClickListener{
   private FoodTypeManager.Food food;
    private Bitmap bitmap_background_board_one;
    private Bitmap bitmap_select_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** 点击外围位置悬浮activity不消失,这里已经在style.xml中设置了 */
        //this.setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_food_description);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_board_one == null || bitmap_background_board_one.isRecycled()) {
            bitmap_background_board_one = BitmapFactory.decodeResource(getResources(), R.mipmap.board_one);
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
        food = (FoodTypeManager.Food)getIntent().getSerializableExtra(IntentConstant.Selected_food);
        bitmap_select_food = BitmapFactory.decodeResource(getResources(), FoodTypeManager.getFoodResourceId(food));
        findViewById(R.id.iv_food_description).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_select_food));
        /** 设置相关的监听 */
        findViewById(R.id.iv_yes).setOnClickListener(this);
        findViewById(R.id.iv_no).setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.iv_no).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_yes:
                if(GameDataManager.init().getFoodList().size() >= 3) {
                    Toast.makeText(this, "最多只能选择3种食材！", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0; i< GameDataManager.init().getFoodList().size(); i++) {
                    if(GameDataManager.init().getFoodList().get(i) == food){
                        Toast.makeText(this, "菜篮中已存在，请不要重复选取！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                GameDataManager.init().setFoodList(food);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentConstant.Selected_food, food);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_no:
                finish();
                break;
            case R.id.iv_add:
                Toast.makeText(this, "点我干啥？", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "说你呢！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_decrease:
                Toast.makeText(this, "还点？来来来，你再点啊", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

}
