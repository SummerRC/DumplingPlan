package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.summerrc.dumplingplan.R;

/**
 * @author SummerRC on 2015.07.12
 *         description : 和面界面
 */
public class DoughActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dough);
        SimpleDraweeView sdv_frame = (SimpleDraweeView) findViewById(R.id.sdv_frame);


        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.mipmap.soho_mixture_gif);
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(null)    //设置Uri
                .build();
        /** 设置Controller */
        sdv_frame.setController(draweeController);
        Animatable animatable = draweeController.getAnimatable();
        animatable.start();
    }
}
