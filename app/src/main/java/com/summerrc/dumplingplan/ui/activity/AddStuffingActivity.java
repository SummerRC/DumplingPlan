package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.activion.listener.PlayAnimClickListener;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 给饺子加馅界面
 */
public class AddStuffingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_stuffing);
        initView();
    }

    private void initView() {
        final Handler handler = new Handler();
        final SimpleDraweeView sdv_soho_add_stuff = (SimpleDraweeView) findViewById(R.id.sdv_soho_add_stuff);
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (anim != null) {
                    sdv_soho_add_stuff.setOnClickListener(new PlayAnimClickListener(anim, handler, 2500,
                            new PlayAnimClickListener.AnimStopCallBack() {
                                @Override
                                public void afterAnimStop() {
                                    UIHelper.openPackActivity(AddStuffingActivity.this);
                                }
                            }));
                }
            }
        };

        Uri uri = Uri.parse("res://com.summerrc.dumplingplan/" + R.mipmap.soho_add_stuffing_gif);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)
                .setControllerListener(controllerListener)
                .build();

        /** 设置Controller */
        sdv_soho_add_stuff.setController(controller);
    }

}
