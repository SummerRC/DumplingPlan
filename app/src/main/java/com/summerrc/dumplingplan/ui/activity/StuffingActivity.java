package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.summerrc.dumplingplan.R;

/**
 * @author SummerRC on 2015.07.12
 * description : 切馅界面
 */
public class StuffingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stuffing);
        ImageView iv_frame = (ImageView) findViewById(R.id.iv_frame);
        iv_frame.setImageResource(R.drawable.animation_cut_food);
        final AnimationDrawable animationDrawable = (AnimationDrawable) iv_frame.getDrawable();
        animationDrawable.start();
    }
}
