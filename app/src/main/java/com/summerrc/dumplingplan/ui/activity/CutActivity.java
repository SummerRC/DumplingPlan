package com.summerrc.dumplingplan.ui.activity;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 切馅界面
 */
public class CutActivity extends BaseActivity implements View.OnClickListener , View.OnTouchListener{
    private Bitmap bitmap_background_cut;
    private int cut_times = 0;          //切割图片的次数
    private ImageView iv_strip;      //面条ImageView
    private ImageView iv_cut_line;

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
        iv_cut_line = (ImageView)findViewById(R.id.iv_cut_line);
        iv_cut_line.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openSkinActivity(this);
                break;
            case R.id.iv_cut_line:
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
            super.hintToNext();
            return;
        }
        ObjectAnimator.ofFloat(iv_cut_line, "translationX", 0f , 80*cut_times).start();
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
                findViewById(R.id.iv_piece_six).setVisibility(View.VISIBLE);
                setWidth();
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
    public static void setLayoutX(View view, int x)
    {
        ViewGroup.MarginLayoutParams margin=new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }
}