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
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.StuffTypeManager;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 包饺子界面
 */
public class AddStuffingActivity extends BaseActivity {
    private Bitmap bitmap_background_pack;
    private ImageView iv_spoon;                         //勺子

    @Override
    protected void setView() {
        setContentView(R.layout.activity_add_stuffing);
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
        iv_spoon = (ImageView) findViewById(R.id.iv_spoon);
        iv_spoon.setOnTouchListener(this);
        findViewById(R.id.iv_skin_one).setOnTouchListener(this);
        findViewById(R.id.iv_skin_two).setOnTouchListener(this);
        findViewById(R.id.iv_skin_three).setOnTouchListener(this);
        findViewById(R.id.iv_skin_four).setOnTouchListener(this);
        findViewById(R.id.iv_skin_five).setOnTouchListener(this);
        findViewById(R.id.iv_skin_six).setOnTouchListener(this);
        int stuffResourceId = StuffTypeManager.getStuffResourceId(GameDataManager.init().getStuffType());
        findViewById(R.id.iv_stuffing).setBackgroundResource(stuffResourceId);
    }

    @Override
    public void onClick(View v) {
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
        super.onTouch(v, event);
        switch (v.getId()) {
            case R.id.iv_next:
                UIHelper.openPutActivity(this);
                break;
            case R.id.iv_spoon:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.ll_hint_click_spoon).setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            case R.id.iv_skin_one:
                animatorSetStart(1);
                break;
            case R.id.iv_skin_two:
                animatorSetStart(2);
                break;
            case R.id.iv_skin_three:
                animatorSetStart(3);
                break;
            case R.id.iv_skin_four:
                animatorSetStart(4);
                break;
            case R.id.iv_skin_five:
                animatorSetStart(5);
                break;
            case R.id.iv_skin_six:
                animatorSetStart(6);
                hintToNext();
                break;
        }
        return true;
    }

    /**
     * 勺子移动的动画
     * @param position 标识皮
     */
    private void animatorSetStart(int position) {
        View view;
        switch (position) {
            case 1:
                view = findViewById(R.id.iv_skin_one);
                break;
            case 2:
                view = findViewById(R.id.iv_skin_two);
                break;
            case 3:
                view = findViewById(R.id.iv_skin_three);
                break;
            case 4:
                view = findViewById(R.id.iv_skin_four);
                break;
            case 5:
                view = findViewById(R.id.iv_skin_five);
                break;
            case 6:
                view = findViewById(R.id.iv_skin_six);
                break;
            default:
                view = findViewById(R.id.iv_skin_one);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        int x =  (int)view.getX() -   (int)iv_spoon.getX();
        int y = (int)iv_spoon.getY()  - (int)view.getY();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_spoon, "translationX", 0f , x+100);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_spoon, "translationY", 0f , y+70);
        animatorSet.play(anim1).with(anim2);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
