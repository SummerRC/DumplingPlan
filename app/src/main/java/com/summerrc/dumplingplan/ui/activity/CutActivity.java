package com.summerrc.dumplingplan.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 切段界面
 */
public class CutActivity extends BaseActivity implements View.OnClickListener , View.OnTouchListener{
    private Bitmap bitmap_background_cut;
    private int cut_times = 0;          //切割图片的次数
    private ImageView iv_strip;         //面条ImageView
    private ImageView iv_knife_cut;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cut);
        initView();
        translateAnimationStart(findViewById(R.id.ll_hint_cut), 0, 40, 500, Integer.MAX_VALUE, true);
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
        iv_strip = (ImageView)findViewById(R.id.iv_strip);
        iv_knife_cut = (ImageView)findViewById(R.id.iv_knife_cut);
        iv_knife_cut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                UIHelper.openSkinActivity(this);
                break;
            case R.id.iv_knife_cut:
                SoundUtil.playSounds(SoundUtil.CUT, 0, getApplicationContext());
                if(findViewById(R.id.ll_hint_cut).getVisibility() == View.VISIBLE) {
                    translateAnimationStop(findViewById(R.id.ll_hint_cut));
                    findViewById(R.id.ll_hint_cut).setVisibility(View.GONE);
                }
                cut_times++;
                showPiece();
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

    /**
     * 显示用户切的段,并平移切割线
     */
    private void showPiece()  {
        if(cut_times >= 6) {
            return;
        }
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_knife_cut, "translationY", 0f, 40);
        anim1.setDuration(50);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_knife_cut, "translationY", 40f, 0);
        anim2.setDuration(2);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv_knife_cut, "translationX", 0f , 80*cut_times);
        anim3.setDuration(400);
        AnimatorSet set = new AnimatorSet();
        set.play(anim2).after(anim1);
        set.play(anim3).after(anim2);
        set.start();
        switch (cut_times) {
            case 1:
                findViewById(R.id.iv_piece_one).setVisibility(View.VISIBLE);
                setWidth();
                break;
            case 2:
                findViewById(R.id.iv_piece_two).setVisibility(View.VISIBLE);
                setWidth();
                break;
            case 3:
                findViewById(R.id.iv_piece_three).setVisibility(View.VISIBLE);
                setWidth();
                break;
            case 4:
                findViewById(R.id.iv_piece_four).setVisibility(View.VISIBLE);
                setWidth();
                break;
            case 5:
                findViewById(R.id.iv_piece_five).setVisibility(View.VISIBLE);
                iv_strip.setVisibility(View.GONE);
                iv_knife_cut.setVisibility(View.GONE);
                findViewById(R.id.iv_piece_six).setVisibility(View.VISIBLE);
                setWidth();
                super.hintToNext();
                break;
        }
    }

    /**
     * 改变面条的宽度
     */
    private void setWidth() {
        int width = iv_strip.getMeasuredWidth();
        ViewGroup.LayoutParams params = iv_strip.getLayoutParams();
        params.width = width - 85;
        iv_strip.setLayoutParams(params);
    }


    /**
     * 设置控件所在的位置X，并且不改变宽高，
     * @param view 控件
     * @param x       绝对位置，此时Y可能归0
     */
    public static void setLayoutX(View view, int x) {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
}