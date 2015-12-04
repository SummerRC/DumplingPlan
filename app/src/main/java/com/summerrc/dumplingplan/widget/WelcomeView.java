package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.interestinggame.cutfood.Spirit;


import java.util.ArrayList;
import java.util.Random;

/**
 * @author SummerRC on 2015/7/21 0011.
 *         继承自自定义的WelcomeSurfaceView，切食材游戏的主界面
 */
public class WelcomeView extends WelcomeSurfaceView {
    private Context mContext;
    private final ArrayList<PointF> mTrack;             //运动轨迹:包含一系列坐标点
    private final static int POINT_LIMIT = 5;
    private Paint mPaint;

    private ArrayList<Spirit> mSpirits;                    //用于容纳食材精灵
    private ArrayList<Spirit> mBooms;                    //用于容纳炸弹精灵
    private long mNextTime = 0L;                        //计算下次生成精灵的时间

    private Drawable mBackground;                        //背景

    protected WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mBackground = mContext.getResources().getDrawable(R.mipmap.soho_background_welcome);
        mTrack = new ArrayList<>();
        /** 实例化容纳精灵的列表，请自行做好管理精灵的工作 */
        mSpirits = new ArrayList<>();
        mBooms = new ArrayList<>();
    }

    /**
     * 专用于绘制屏幕，继承父类，在父类的线程中循环被调用
     */
    @Override
    protected void myDraw(Canvas canvas) {
        super.myDraw(canvas);
        /** 用户画背景 */
        drawBackground(canvas);

        /** 到了计算好的时间就生成精灵 ，间隔时间1秒钟左右，用了一个随机数 */
        if (mNextTime < System.currentTimeMillis()) {
            generateSpirit();
            nextGenTime();
        }

        checkSpirits();
        drawSpirits(canvas);
        isHit();
    }

    /**
     * 下一次生成精灵的时间，间隔时间1.几秒
     */
    private void nextGenTime() {
        mNextTime = System.currentTimeMillis();
        Random r = new Random();
        int interval = 1000 + r.nextInt(100);
        mNextTime += interval;
    }

    /**
     * 生成精灵，并添加到精灵管理列表
     */
    private void generateSpirit() {
        /** 请修改此方法，使精灵从更多方向抛出 */
        Spirit spirit = new Spirit(mContext);
        spirit.loadBitmap(R.mipmap.ic_launcher);

        Random rand = new Random();
        int randNum = 1 + rand.nextInt(9);
        int cakeId;
        switch (randNum) {
            case 1:
                cakeId = R.mipmap.cut_food_cabbage;
                spirit.loadBitmap(cakeId);
                spirit.setmType(cakeId);
                //t_num=randNum;
                break;
            case 2:
                cakeId = R.mipmap.cut_food_cucumber;
                spirit.loadBitmap(cakeId);
                spirit.setmType(cakeId);
                //t_num=randNum;
                break;
        }

        Random r = new Random();
        spirit.mCoord.x = PhoneWidth / 4 - 300 + r.nextInt(400);
        spirit.mCoord.y = PhoneHeight;
        spirit.mV.x = 7 + r.nextInt(5);
        spirit.mV.y = -(30 + (r.nextInt(10)));
        mSpirits.add(spirit);
    }


    /**
     * 开始和停止背景音乐
     *
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    /**
     * 开始和停止背景音乐
     *
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
    }


    /**
     * 画精灵到画布上
     *
     * @param canvas 画布
     */
    private void drawSpirits(Canvas canvas) {
        for (int i = 0; i < mSpirits.size(); i++) {
            mSpirits.get(i).draw(canvas);
        }
        for (int i = 0; i < mBooms.size(); i++) {
            mBooms.get(i).draw(canvas);
        }
    }

    /**
     * 检查精灵是否还在屏幕内，不在屏幕内则移除
     */
    private void checkSpirits() {
        for (int i = 0; i < mSpirits.size(); i++) {
            if (isSpiritValidate(i)) {
                mSpirits.remove(i);
                i -= 1;
            }
        }
    }

    /**
     * 具体检查精灵是否在屏幕内的方法
     *
     * @param i index
     * @return boolean
     */
    private boolean isSpiritValidate(int i) {
        PointF coordinate = mSpirits.get(i).mCoord;
        return (coordinate.x < -mSpirits.get(i).mDimention.x || coordinate.x > PhoneWidth || coordinate.y > PhoneHeight);
    }

    private void isHit() {
        synchronized (mTrack) {
            for (int i = 0; i < mTrack.size(); i++) {
                for (int z = 0; z < mSpirits.size(); z++) {
                    if (mTrack.get(i).x > mSpirits.get(z).mCoord.x && mTrack.get(i).x < mSpirits.get(z).mCoord.x + mSpirits.get(z).mDimention.x) {
                        if (mTrack.get(i).y > mSpirits.get(z).mCoord.y && mTrack.get(i).y < mSpirits.get(z).mCoord.y + mSpirits.get(z).mDimention.y) {
                            switch (mSpirits.get(z).getmType()) {
                                case R.mipmap.cut_food_cabbage:
                                    break;
                                case R.mipmap.cut_food_cucumber:
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 修改画背景的方法
     *
     * @param canvas 画布
     */
    private void drawBackground(Canvas canvas) {
        canvas.drawColor(0xFF000000);
        mBackground.setBounds(0, 0, PhoneWidth, PhoneHeight);
        mBackground.draw(canvas);
        Bitmap bitmap_clock = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
        canvas.drawBitmap(bitmap_clock, PhoneWidth - 210, 35, mPaint);
    }


    /**
     * 屏幕点击事件的响应方法
     *
     * @param event event
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handleActionDown(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            handleActionMove(event);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            handleActionUp();
        }
        return true;
    }

    /**
     * 手指按下的响应方法
     *
     * @param event event
     */
    private void handleActionDown(MotionEvent event) {
        PointF point = new PointF(event.getX(), event.getY());
        synchronized (mTrack) {
            mTrack.add(point);
        }
    }

    /**
     * 手指拖动的响应方法
     *
     * @param event event
     */
    private void handleActionMove(MotionEvent event) {
        PointF point = new PointF(event.getX(), event.getY());
        synchronized (mTrack) {
            if (mTrack.size() >= POINT_LIMIT) {
                mTrack.remove(0);
            }
            mTrack.add(point);
        }
    }

    /**
     * 手指抬起的响应方法
     */
    private void handleActionUp() {
        synchronized (mTrack) {
            mTrack.clear();
        }
    }

}
