package com.summerrc.dumplingplan.config;

import com.summerrc.dumplingplan.R;

/**
 * @author : SummerRC on 2015/7/20 0020
 * @version :  V1.0 <当前版本>
 *          description : <在此填写描述信息>
 */
public class ScoreResourceManager {
    public static int getScoreResource(int i) {
        switch (i) {
            case 0:
                return R.mipmap.zero;
            case 1:
                return R.mipmap.one;
            case 2:
                return R.mipmap.two;
            case 3:
                return R.mipmap.three;
            case 4:
                return R.mipmap.four;
            case 5:
                return R.mipmap.five;
            case 6:
                return R.mipmap.six;
            case 7:
                return R.mipmap.seven;
            case 8:
                return R.mipmap.eight;
            case 9:
                return R.mipmap.nine;
        }
        return R.mipmap.zero;
    }
}
