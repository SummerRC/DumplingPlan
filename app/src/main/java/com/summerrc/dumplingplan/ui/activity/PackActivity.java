package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 包饺子界面
 */
public class PackActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_pack;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_pack);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_pack==null || bitmap_background_pack.isRecycled()) {
            bitmap_background_pack = BitmapFactory.decodeResource(getResources(), R.mipmap.background_pack);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_pack));
    }

    protected void initView() {
        super.initView();
       findViewById(R.id.iv_spoon).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openPutActivity(this);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_pack != null && !bitmap_background_pack.isRecycled()) {
            bitmap_background_pack.isRecycled();
            bitmap_background_pack = null;
            System.gc();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_spoon:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.ll_hint_click_spoon).setVisibility(View.GONE);
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
