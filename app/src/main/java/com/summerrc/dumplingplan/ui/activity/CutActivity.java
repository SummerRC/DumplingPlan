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
public class CutActivity extends BaseActivity implements View.OnClickListener , View.OnTouchListener{
    private Bitmap bitmap_background_cut;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cut);
        initView();
        translateAnimationStart(findViewById(R.id.ll_hint_cut), 0, 40,500, 1, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bitmap_background_cut == null || bitmap_background_cut.isRecycled()) {
            bitmap_background_cut = BitmapFactory.decodeResource(getResources(), R.mipmap.background_cut);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_cut));
    }

    protected void initView() {
        super.initView();
        findViewById(R.id.iv_strip).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openSkinActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bitmap_background_cut != null && !bitmap_background_cut.isRecycled()) {
            bitmap_background_cut.isRecycled();
            bitmap_background_cut = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_strip:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        translateAnimationStop(findViewById(R.id.ll_hint_cut));
                        findViewById(R.id.ll_hint_cut).setVisibility(View.GONE);
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