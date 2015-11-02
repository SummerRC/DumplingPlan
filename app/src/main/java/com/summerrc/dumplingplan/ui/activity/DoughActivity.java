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
 * description : 和面界面
 */
public class DoughActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dough);
        ImageView sdv_frame = (ImageView) findViewById(R.id.sdv_frame);
        sdv_frame.setImageResource(R.drawable.animation_dough);
        AnimationDrawable animationDrawable = (AnimationDrawable) sdv_frame.getDrawable();
        animationDrawable.start();
    }
}
