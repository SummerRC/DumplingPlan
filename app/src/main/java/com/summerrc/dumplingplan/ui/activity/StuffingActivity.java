package com.summerrc.dumplingplan.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.StuffTypeManager;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 切馅界面
 */
public class StuffingActivity extends BaseActivity implements View.OnClickListener{
    private Bitmap bitmap_background_stuffing;
    private Handler mHandler;
    private ImageView iv_kitchen_knife_left;        //左边小刀
    private ImageView iv_kitchen_knife_right;       //右边小刀

    @Override
    protected void setView() {
        setContentView(R.layout.activity_stuffing);
        initView();
        SoundUtil.playSounds(SoundUtil.FOUR_ONE, 0, getApplicationContext());
        mHandler = new Handler();
        /** 左边小刀的移动动画 */
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation1 = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, -0.15f,
                        TranslateAnimation.RELATIVE_TO_SELF, 0.15f);
                translateAnimation1.setDuration(200);
                translateAnimation1.setStartTime(0);
                translateAnimation1.setRepeatCount(15);             //Integer.MAX_VALUE 可以设置无穷次
                translateAnimation1.setRepeatMode(Animation.REVERSE);
                iv_kitchen_knife_left.startAnimation(translateAnimation1);
            }
        });
        /** 右边小刀的移动动画 */
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_PARENT, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, 0.15f,
                        TranslateAnimation.RELATIVE_TO_SELF, -0.15f);
                translateAnimation.setDuration(200);
                translateAnimation.setStartTime(0);
                translateAnimation.setRepeatCount(15);             //Integer.MAX_VALUE 可以设置无穷次
                translateAnimation.setRepeatMode(Animation.REVERSE);
                iv_kitchen_knife_right.startAnimation(translateAnimation);
            }
        },0);
        /** 动画结束之后自动进入下一个界面 */
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UIHelper.openDoughActivity(StuffingActivity.this);
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_stuffing==null || bitmap_background_stuffing.isRecycled()) {
            bitmap_background_stuffing = BitmapFactory.decodeResource(getResources(), R.mipmap.background_stuffing);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_stuffing));
    }

    protected void initView() {
        super.initView();
        iv_kitchen_knife_left = (ImageView)findViewById(R.id.iv_kitchen_knife_left);
        iv_kitchen_knife_right = (ImageView)findViewById(R.id.iv_kitchen_knife_right);
        int stuffResourceId = StuffTypeManager.getStuffResourceId(GameDataManager.init(getApplicationContext()).getStuffType());
        findViewById(R.id.iv_stuff).setBackgroundResource(stuffResourceId);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 使是动画播放过程中其它组件点击事件失效
     * */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_stuffing != null && !bitmap_background_stuffing.isRecycled()) {
            bitmap_background_stuffing.isRecycled();
            bitmap_background_stuffing = null;
            System.gc();
        }
    }

}
