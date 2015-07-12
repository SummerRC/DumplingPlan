package com.summerrc.dumplingplan.utils;

import android.graphics.Bitmap;

/**
 * @author : SummerRC on 2015/7/12 0012
 * @version :  V1.0 <当前版本>
 *          description : 切割图片的工具类
 */
public class ImageSplitUtils {
    /**
     * 切割图片 的方法
     * @param bitmap 被切割的图片
     * @param start     切割的开始位置
     * @param end      切割的结束位置
     * @return               新的bitmap
     */
    public static Bitmap split(Bitmap bitmap, int start, int end) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, start, end, width, height);
    }

}
