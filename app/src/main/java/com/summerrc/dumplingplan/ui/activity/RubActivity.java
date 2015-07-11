package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

public class RubActivity extends BaseActivity implements View.OnClickListener , View.OnTouchListener{
    private Bitmap bitmap_background_rub;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_rub);
        initView();
        scaleAnimation(findViewById(R.id.iv_hint_rub), 0.6f, 1.2f, 0.6f, 1.2f, 1000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_rub == null || bitmap_background_rub.isRecycled()) {
            bitmap_background_rub = BitmapFactory.decodeResource(getResources(), R.mipmap.background_rub);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_rub));
    }

    protected void initView() {
        super.initView();
        findViewById(R.id.iv_dough_big).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openCutActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap_background_rub != null && !bitmap_background_rub.isRecycled()) {
            bitmap_background_rub.isRecycled();
            bitmap_background_rub = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_dough_big:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scaleAnimation(findViewById(R.id.iv_hint_rub), 0, 0, 0, 0, 0);
                        findViewById(R.id.iv_hint_rub).setVisibility(View.GONE);
                        hintToNext();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return false;
    }
}