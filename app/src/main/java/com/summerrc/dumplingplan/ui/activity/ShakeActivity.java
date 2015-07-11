package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * 切段
 */
public class ShakeActivity extends BaseActivity implements View.OnClickListener , View.OnTouchListener{
    private Bitmap bitmap_background_shake;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_shake);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_shake == null || bitmap_background_shake.isRecycled()) {
            bitmap_background_shake = BitmapFactory.decodeResource(getResources(), R.mipmap.background_shake);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_shake));
    }

    protected void initView() {
        super.initView();
        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openWelcomeActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap_background_shake != null && !bitmap_background_shake.isRecycled()) {
            bitmap_background_shake.isRecycled();
            bitmap_background_shake = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_strip:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.ll_hint_cut).setVisibility(View.GONE);
                        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return super.onTouch(v, event);
    }
}