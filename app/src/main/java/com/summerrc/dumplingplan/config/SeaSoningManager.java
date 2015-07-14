package com.summerrc.dumplingplan.config;

import com.summerrc.dumplingplan.R;

import java.util.HashMap;

/**
 * @author SummerRC on 2015/7/11 0011.
 * 系统中所有的调料种类
 */
public class SeaSoningManager {
    public enum Seasoning {
        OIL, SALT, SAUCE
    }

    private static int getRecesourceId(Seasoning food) {
        int resourceId;
        switch (food) {
            case OIL:
                resourceId =  R.mipmap.oil;
                break;
            case SALT:
                resourceId =  R.mipmap.salt;
                break;
            case SAUCE:
                resourceId =  R.mipmap.sauce;
                break;
            default:
                resourceId = R.mipmap.oil;
        }
        return resourceId;
    }
}
