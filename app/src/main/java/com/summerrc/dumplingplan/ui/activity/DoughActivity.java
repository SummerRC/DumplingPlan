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
import com.summerrc.dumplingplan.activion.listener.PlayAnimClickListener.AnimStopCallBack;
import com.summerrc.dumplingplan.utils.UIHelper;

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
        initView();
    }

    private void initView() {
        final Handler handler = new Handler();
        final SimpleDraweeView sdv_soho_mixture_gif = (SimpleDraweeView) findViewById(R.id.sdv_soho_mixture_gif);
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                if (anim != null) {
                    sdv_soho_mixture_gif.setOnClickListener(new PlayAnimClickListener(anim, handler, 2000,
                            new AnimStopCallBack() {
                                @Override
                                public void afterAnimStop() {
                                    UIHelper.openRubActivity(DoughActivity.this);
                                }
                            }));
                }
            }
        };

        Uri uri = Uri.parse("res://com.summerrc.dumplingplan/" + R.mipmap.soho_mixture_gif);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)
                .setControllerListener(controllerListener)
                .build();

        /** 设置Controller */
        sdv_soho_mixture_gif.setController(controller);
    }
}
