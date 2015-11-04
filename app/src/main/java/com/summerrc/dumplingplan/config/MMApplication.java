package com.summerrc.dumplingplan.config;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by SummerRC on 2015/11/5.
 * description:
 */
public class MMApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
