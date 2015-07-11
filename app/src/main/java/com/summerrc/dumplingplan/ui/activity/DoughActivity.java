package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

public class DoughActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_dough;
    private ImageView iv_kettle;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_dough);
        initView();
        translateAnimationStart(findViewById(R.id.ll_tip_click_kettle), 10, 100, 800, Integer.MAX_VALUE, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_dough==null || bitmap_background_dough.isRecycled()) {
            bitmap_background_dough = BitmapFactory.decodeResource(getResources(), R.mipmap.background_dough);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_dough));
    }

    protected void initView() {
        super.initView();
        iv_kettle = (ImageView)findViewById(R.id.iv_kettle);
        iv_kettle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openRubActivity(this);
                break;
            case R.id.iv_kettle:
                translateAnimationStop(findViewById(R.id.ll_tip_click_kettle));
                findViewById(R.id.ll_tip_click_kettle).setVisibility(View.GONE);
                hintToNext();
                int  x = (int)findViewById(R.id.iv_basin).getX() - (int)iv_kettle.getX();
                int y = (int)findViewById(R.id.iv_basin).getY() - (int)iv_kettle.getY();
                translateAnimationStart(iv_kettle, x-10, y-200, 800, 0, true);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_dough != null && !bitmap_background_dough.isRecycled()) {
            bitmap_background_dough.isRecycled();
            bitmap_background_dough = null;
            System.gc();
        }
    }

}
