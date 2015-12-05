package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;

public class StaticSpirit {
    private Bitmap mBitmap;

    public PointF mCoordinate;                            //精灵的坐标
    public PointF mDimension;                            //精灵的长宽

    public enum Type {
        SETTING, START, LOGO, HELP, AWARD
    }

    public Type type;

    public StaticSpirit(Context mContext, Type type) {
        mCoordinate = new PointF();
        mDimension = new PointF();
        this.type = type;

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int PhoneWidth = wm.getDefaultDisplay().getWidth();
        int PhoneHeight = wm.getDefaultDisplay().getHeight();

        int y = 4 * PhoneHeight / 5;
        switch (type) {
            case SETTING:
                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.soho_welcome_shezhi);
                mCoordinate.x = 60;
                mCoordinate.y = y;
                break;
            case START:
                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.soho_welcome_kaishi);
                mCoordinate.x = PhoneWidth / 4 - mBitmap.getWidth() / 2;
                mCoordinate.y = y;
                break;
            case LOGO:
                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.soho_welcome_logo);
                mCoordinate.x = PhoneWidth / 2 - mBitmap.getWidth() / 2;
                mCoordinate.y = y - mBitmap.getHeight() / 2;
                break;
            case HELP:
                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.soho_welcome_bangzhu);
                mCoordinate.x = 3 * PhoneWidth / 4 - mBitmap.getWidth() / 2;
                mCoordinate.y = y;
                break;
            case AWARD:
                mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.soho_welcome_jiangbei);
                mCoordinate.x = PhoneWidth - mBitmap.getWidth() - 60;
                mCoordinate.y = y;
                break;
        }
        if (mBitmap != null) {
            mDimension.x = mBitmap.getWidth();
            mDimension.y = mBitmap.getHeight();
        }
    }


    /**
     * 把精灵画到画布上
     *
     * @param canvas canvas
     */
    public void draw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, mCoordinate.x, mCoordinate.y, new Paint());
        }
    }
}
