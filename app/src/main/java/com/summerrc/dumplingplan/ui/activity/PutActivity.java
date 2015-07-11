package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * 包饺子
 */
public class PutActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_put;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_put);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_put==null || bitmap_background_put.isRecycled()) {
            bitmap_background_put = BitmapFactory.decodeResource(getResources(), R.mipmap.background_put);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_put));
    }

    protected void initView() {
        super.initView();
       findViewById(R.id.iv_cover_pad).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openShakeActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_put != null && !bitmap_background_put.isRecycled()) {
            bitmap_background_put.isRecycled();
            bitmap_background_put = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_cover_pad:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
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
