package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.summerrc.dumplingplan.config.MMApplication;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                    sdv_soho_mixture_gif.setOnClickListener(new PlayAnimClickListener(anim, handler, 2500,
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
        setBackground();
        setVisible();
        findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMApplication.isSelected = !MMApplication.isSelected;
                setVisible();
            }
        });
    }

    private void setVisible() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_yingyue).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.iv_yingxiao).setVisibility(View.GONE);
            findViewById(R.id.iv_yingyue).setVisibility(View.GONE);
            findViewById(R.id.iv_yingxiao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinxiao = !MMApplication.isSelected_yinxiao;
                    if(MMApplication.isSelected_yinxiao) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });
            findViewById(R.id.iv_yingyue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinyue = !MMApplication.isSelected_yinyue;
                    if(MMApplication.isSelected_yinyue) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });

        }
    }

    private void setBackground() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
        } else {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
        }
    }

}
