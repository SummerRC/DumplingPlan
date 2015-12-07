package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class AnimationSpirit {
    private Context mContext;
    private Bitmap mBitmap;

    private final static float ACCELERATION_Y = 2F;        //Y方向加速度
    private final static float ACCELERATION_X = 0F;        //X方向加速度
    public PointF mCoordinate;                            //精灵的坐标
    private PointF mV;                                    //精灵的速度
    public PointF mDimension;                            //精灵的长宽

    public enum Type {
        LEFT_ONE, LEFT_TWO, LEFT_THREE, LEFT_FOUR, LEFT_FIVE, RIGHT_ONE, RIGHT_TWO, RIGHT_THREE, RIGHT_FOUR, RIGHT_FIVE
    }

    public AnimationSpirit(Context context, PointF mCoordinate) {
        mContext = context;
        this.mCoordinate = new PointF();
        this.mCoordinate.x = mCoordinate.x;
        this.mCoordinate.y = mCoordinate.y;
        mV = new PointF();
        mDimension = new PointF();
    }

    /**
     * 导入该精灵的图片
     *
     * @param id int
     */
    public void loadBitmap(int id, Type type) {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
        mDimension.x = mBitmap.getWidth();
        mDimension.y = mBitmap.getHeight();
        switch (type) {
            case LEFT_ONE:
                setLeftOneMove();
                break;
            case LEFT_TWO:
                setLeftTwoMove();
                break;
            case LEFT_THREE:
                setLeftThreeMove();
                break;
            case LEFT_FOUR:
                setLeftFourMove();
                break;
            case LEFT_FIVE:
                setLeftFiveMove();
                break;
            case RIGHT_ONE:
                setRightOneMove();
                break;
            case RIGHT_TWO:
                setRightTwoMove();
                break;
            case RIGHT_THREE:
                setRightThreeMove();
                break;
            case RIGHT_FOUR:
                setRightFourMove();
                break;
            case RIGHT_FIVE:
                setRightFiveMove();
                break;
        }
    }

    private void setLeftOneMove() {
        mV.x = -30 * 3;
        mV.y = -10 * 3;
    }

    private void setLeftTwoMove() {
        mV.x = -15 * 2;
        mV.y = -25 * 2;
    }

    private void setLeftThreeMove() {
        mV.x = -10 * 2;
        mV.y = -40 * 2;
    }

    private void setLeftFourMove() {
        mV.x = -5 * 2;
        mV.y = -30 * 2;
    }

    private void setLeftFiveMove() {
        mV.x = -2 * 10;
        mV.y = -40 * 2;
    }

    private void setRightOneMove() {
        mV.x = 7 * 2;
        mV.y = -30 * 2;
        mCoordinate.x = mCoordinate.x + 400;
    }

    private void setRightTwoMove() {
        mV.x = 20 * 2;
        mV.y = -30 * 2;
        mCoordinate.x = mCoordinate.x + 400;
    }

    private void setRightThreeMove() {
        mV.x = 30 * 2;
        mV.y = -50 * 2;
        mCoordinate.x = mCoordinate.x + 400;
    }

    private void setRightFourMove() {
        mV.x = 40 * 2;
        mV.y = -10 * 2;
        mCoordinate.x = mCoordinate.x + 400;
    }

    private void setRightFiveMove() {
        mV.x = 2 * 2;
        mV.y = -40 * 2;
    }


    /**
     * 把精灵画到画布上
     *
     * @param canvas canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mCoordinate.x, mCoordinate.y, new Paint());
        move();
    }

    /**
     * 计算精灵的移动
     */
    public void move() {
        mCoordinate.x += mV.x;
        mCoordinate.y += mV.y;

        mV.x += ACCELERATION_X;
        mV.y += ACCELERATION_Y;
    }
}
