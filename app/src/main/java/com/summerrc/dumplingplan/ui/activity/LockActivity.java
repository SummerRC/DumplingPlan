package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 点击菜篮弹出这个Activity,背景透明位置居中
 */
public class LockActivity extends BaseActivity implements View.OnLongClickListener{
    private Bitmap bitmap_background_lock;
    private int unLock;
    private ImageView iv_one, iv_two, iv_three, iv_four, iv_five, iv_six, iv_seven, iv_eight, iv_nine;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_lock);
        GameDataManager.init(getApplicationContext()).clean();
        unLock = GameDataManager.init(getApplicationContext()).getUnLock();
        iv_one = (ImageView) findViewById(R.id.iv_one);
        iv_one.setOnClickListener(this);
        iv_one.setBackgroundResource(R.mipmap.one_pass);
        iv_two = (ImageView) findViewById(R.id.iv_two);
        iv_three = (ImageView) findViewById(R.id.iv_three);
        iv_four = (ImageView) findViewById(R.id.iv_four);
        iv_five = (ImageView) findViewById(R.id.iv_five);
        iv_six = (ImageView) findViewById(R.id.iv_six);
        iv_seven = (ImageView) findViewById(R.id.iv_seven);
        iv_eight = (ImageView) findViewById(R.id.iv_eight);
        iv_nine = (ImageView) findViewById(R.id.iv_nine);
        if(unLock >= 2) {
            iv_two.setBackgroundResource(R.mipmap.two_pass);
            iv_two.setOnClickListener(this);
        } else {
            iv_two.setBackgroundResource(R.mipmap.two_lock);
        }
        if(unLock >= 3) {
            iv_three.setBackgroundResource(R.mipmap.three_pass);
            iv_three.setOnClickListener(this);
        } else {
            iv_three.setBackgroundResource(R.mipmap.three_lock);
        }
        if(unLock >= 4) {
            iv_four.setBackgroundResource(R.mipmap.four_pass);
            iv_four.setOnClickListener(this);
        } else {
            iv_four.setBackgroundResource(R.mipmap.four_lock);
        }
        if(unLock >= 5) {
            iv_five.setBackgroundResource(R.mipmap.five_pass);
            iv_five.setOnClickListener(this);
        } else {
            iv_five.setBackgroundResource(R.mipmap.five_lock);
        }
        if(unLock >= 6) {
            iv_six.setBackgroundResource(R.mipmap.six_pass);
            iv_six.setOnClickListener(this);
        } else {
            iv_six.setBackgroundResource(R.mipmap.six_lock);
        }
        if(unLock >= 7) {
            iv_seven.setBackgroundResource(R.mipmap.seven_pass);
            iv_seven.setOnClickListener(this);
        } else {
            iv_seven.setBackgroundResource(R.mipmap.seven_lock);
        }
        if(unLock >= 8) {
            iv_eight.setBackgroundResource(R.mipmap.eight_pass);
            iv_eight.setOnClickListener(this);
        } else {
            iv_eight.setBackgroundResource(R.mipmap.eight_lock);
        }
        if(unLock >= 9) {
            iv_nine.setBackgroundResource(R.mipmap.nine_pass);
            iv_nine.setOnClickListener(this);
        } else {
            iv_nine.setBackgroundResource(R.mipmap.nine_lock);
        }
        iv_two.setOnLongClickListener(this);
        iv_three.setOnLongClickListener(this);
        iv_four.setOnLongClickListener(this);
        iv_five.setOnLongClickListener(this);
        iv_six.setOnLongClickListener(this);
        iv_seven.setOnLongClickListener(this);
        iv_eight.setOnLongClickListener(this);
        iv_nine.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int currentLock = 0;
        switch (v.getId()) {
            case R.id.iv_one:
                currentLock = 1;
                Toast.makeText(this, "so what", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_two:
                currentLock = 2;
                break;
            case R.id.iv_three:
                currentLock = 3;
                break;
            case R.id.iv_four:
                currentLock = 4;
                break;
            case R.id.iv_five:
                currentLock = 5;
                break;
            case R.id.iv_six:
                currentLock = 6;
                break;
            case R.id.iv_seven:
                currentLock = 7;
                break;
            case R.id.iv_eight:
                currentLock = 8;
                break;
            case R.id.iv_nine:
                currentLock = 9;
                break;
        }
        if(currentLock > 0) {
            GameDataManager.init(getApplicationContext()).setCurrentLock(currentLock);
            UIHelper.openSelectFoodActivity(this);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_lock==null || bitmap_background_lock.isRecycled()) {
            bitmap_background_lock = BitmapFactory.decodeResource(getResources(), R.mipmap.background_lock);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_lock));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_lock != null && !bitmap_background_lock.isRecycled()) {
            bitmap_background_lock.isRecycled();
            bitmap_background_lock = null;
            System.gc();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int currentLock = 0;
        switch (v.getId()) {
            case R.id.iv_one:
                break;
            case R.id.iv_two:
                currentLock = 2;
                break;
            case R.id.iv_three:
                currentLock = 4;
                break;
            case R.id.iv_four:
                currentLock = 4;
                break;
            case R.id.iv_five:
                currentLock = 5;
                break;
            case R.id.iv_six:
                currentLock = 7;
                break;
            case R.id.iv_seven:
                currentLock = 7;
                break;
            case R.id.iv_eight:
                currentLock = 8;
                break;
            case R.id.iv_nine:
                currentLock = 8;
                break;
        }
        GameDataManager.init(getApplicationContext()).setCurrentLock(currentLock);
        UIHelper.openSelectFoodActivity(this);
        return false;
    }
}
