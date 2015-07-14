package com.summerrc.dumplingplan.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 下饺子界面
 */
public class PutActivity extends BaseActivity  {
    private Bitmap bitmap_background_put;
    private ImageView iv_cover_pad;                     //放饺子的盖
    private ImageView iv_pod;                           //锅

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
        iv_cover_pad = (ImageView) findViewById(R.id.iv_cover_pad);
        iv_cover_pad.setOnTouchListener(this);
        iv_pod = (ImageView) findViewById(R.id.iv_pod);
        findViewById(R.id.iv_next).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
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
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openShakeActivity(this);
                break;
            case R.id.iv_cover_pad:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        findViewById(R.id.iv_next).setVisibility(View.VISIBLE);
                        animatorSetStart();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return true;
    }

    /**
     * 盖子移动的动画
     */
    private void animatorSetStart() {
        AnimatorSet animatorSet = new AnimatorSet();
        int x = (int)iv_pod.getX() - (int)iv_cover_pad.getX();
        int y = (int)iv_pod.getY() - (int)iv_cover_pad.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_cover_pad, "translationX", 0f , x+240);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_cover_pad, "translationY", 0f , y-60);
        animatorSet.play(anim1).with(anim2);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
