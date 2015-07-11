package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * 擀面
 */
public class SkinActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_skin;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_skin);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_skin==null || bitmap_background_skin.isRecycled()) {
            bitmap_background_skin = BitmapFactory.decodeResource(getResources(), R.mipmap.background_skin);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_skin));
    }

    protected void initView() {
        super.initView();
       findViewById(R.id.iv_rolling_pin).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openPackActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_skin != null && !bitmap_background_skin.isRecycled()) {
            bitmap_background_skin.isRecycled();
            bitmap_background_skin = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_rolling_pin:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.rl_hint_skin).setVisibility(View.GONE);
                        hintToNext();
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
