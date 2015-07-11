package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

public class SelectSeasoningActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_salt;
    private ImageView iv_sauce;
    private ImageView iv_oil;
    private Bitmap bitmap_background_select_seasoning;

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
        translateAnimationStart(findViewById(R.id.ll_hint_select_food), 0, 100, 800, Integer.MAX_VALUE, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openStuffingActivity(this);
                break;
            case R.id.iv_salt:
                if(findViewById(R.id.ll_hint_select_food).getVisibility()==View.VISIBLE) {
                    translateAnimationStop(findViewById(R.id.ll_hint_select_food));
                    findViewById(R.id.ll_hint_select_food).setVisibility(View.GONE);
                }
                super.hintToNext();
                int x = PhoneWidth - (int)iv_salt.getX();
                int y = PhoneHeight - (int)iv_salt.getY();
                translateAnimationStart(iv_salt, x, -y, 1000, 0, true);
                iv_salt.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_sauce:
                if(findViewById(R.id.ll_hint_select_food).getVisibility()==View.VISIBLE) {
                    translateAnimationStop(findViewById(R.id.ll_hint_select_food));
                    findViewById(R.id.ll_hint_select_food).setVisibility(View.GONE);
                }
                super.hintToNext();
                int x1 = PhoneWidth - (int)iv_sauce.getX();
                int y1 = PhoneHeight - (int)iv_sauce.getY();
                translateAnimationStart(iv_sauce, x1, -y1, 1000, 0, true);
                iv_sauce.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_oil:
                if(findViewById(R.id.ll_hint_select_food).getVisibility()==View.VISIBLE) {
                    translateAnimationStop(findViewById(R.id.ll_hint_select_food));
                    findViewById(R.id.ll_hint_select_food).setVisibility(View.GONE);
                }
                super.hintToNext();
                int x2 = PhoneWidth - (int)iv_oil.getX();
                int y2 = PhoneHeight - (int)iv_oil.getY();
                translateAnimationStart(iv_oil, x2, -y2, 1000, 0, true);
                iv_oil.setVisibility(View.INVISIBLE);
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

}
