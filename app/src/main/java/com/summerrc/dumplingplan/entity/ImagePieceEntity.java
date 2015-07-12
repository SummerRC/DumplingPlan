package com.summerrc.dumplingplan.entity;

import android.graphics.Bitmap;

/**
 * @author : SummerRC on 2015/7/12 0012
 * @version :  V1.0 <当前版本>
 *          description : 切段时用到的实体类，每一个对象代表每一段
 */
public class ImagePieceEntity {
    private int index = 0;
    private Bitmap bitmap = null;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
