package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 揉面界面
 */
public class RubActivity extends BaseActivity {
    private Bitmap bitmap_background_rub;
    private ImageView iv_dough;     //面团控件

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
        iv_dough = (ImageView)findViewById(R.id.iv_dough_big);
        iv_dough.setOnTouchListener(this);
        /*final ViewTreeObserver vto = iv_dough.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                 vto.removeOnPreDrawListener(this);
                 height = iv_dough.getMeasuredHeight();
                 width = iv_dough.getMeasuredWidth();
                return true;
            }
        });*/
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
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_dough_big:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        scaleAnimation(findViewById(R.id.iv_hint_rub), 0, 0, 0, 0, 0);
                        findViewById(R.id.iv_hint_rub).setVisibility(View.GONE);
                        setWidthAndHeight();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                break;
            case R.id.iv_next:
                UIHelper.openCutActivity(this);
                break;
        }
        return true;            //返回false会导致只响应down事件，true导致事件不会传递给父view
    }

    /**
     * 改变ImageView的宽和高
     */
    private void setWidthAndHeight() {
        int height = iv_dough.getMeasuredHeight();
        int width = iv_dough.getMeasuredWidth();
        ((TextView) findViewById(R.id.iv_hint)).setText(width+"_"+height);
        if(width>670) {
            if(findViewById(R.id.iv_next).getVisibility() == View.VISIBLE) return;
            hintToNext();
        }
        ViewGroup.LayoutParams params = iv_dough.getLayoutParams();
        params.height = --height;
        params.width = width + 3;
        iv_dough.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {}
}