package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author SummerRC on 2015/12/3 0011.
 *         自定义的SurfaceView，用于管理生命周期,子类实现具体的绘制逻辑
 */
public class WelcomeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private DrawThread mDrawThread;
    public int PhoneWidth;
    public int PhoneHeight;
    private Context mContext;
    private final ArrayList<PointF> mTrack;             //运动轨迹:包含一系列坐标点
    private final static int POINT_LIMIT = 5;
    private Paint mPaint;
    private ArrayList<AnimationSpirit> mSpirits;                 //用于容纳食材精灵
    private long mNextTime = 0L;                        //计算下次生成精灵的时间
    private Drawable mBackground;                       //背景

    public WelcomeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mTrack = new ArrayList<>();
        mHolder = getHolder();
        mHolder.addCallback(this);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        PhoneWidth = wm.getDefaultDisplay().getWidth();
        PhoneHeight = wm.getDefaultDisplay().getHeight();
        mPaint = new Paint();
        mBackground = mContext.getResources().getDrawable(R.mipmap.soho_background_welcome);
        /** 实例化容纳精灵的列表，请自行做好管理精灵的工作 */
        mSpirits = new ArrayList<>();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawThread = new DrawThread();
        Thread thread = new Thread(mDrawThread);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDrawThread.stop();
    }

    private class DrawThread implements Runnable {
        private boolean mRun = true;

        @Override
        public void run() {
            while (mRun) {
                Canvas canvas = mHolder.lockCanvas();
                myDraw(canvas);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void stop() {
            mRun = false;
        }

        public void start() {
            mRun = true;
        }

    }

    /**
     * 提供子类的接口,有子类实现具体逻辑
     *
     * @param canvas 画布
     */
    protected void myDraw(Canvas canvas) {
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
        AnimationSpirit leftSpirit = new AnimationSpirit(mContext);
        leftSpirit.loadBitmap(R.mipmap.soho_welcome_anim_dumpling, AnimationSpirit.Type.LEFT);
        AnimationSpirit rightSpirit = new AnimationSpirit(mContext);
        rightSpirit.loadBitmap(R.mipmap.soho_welcome_anim_dumpling, AnimationSpirit.Type.RIGHT);
        mSpirits.add(leftSpirit);
        mSpirits.add(rightSpirit);
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
        PointF coordinate = mSpirits.get(i).mCoordinate;
        return (coordinate.x < -mSpirits.get(i).mDimension.x || coordinate.x > PhoneWidth || coordinate.y > PhoneHeight);
    }

    private void isHit() {
        synchronized (mTrack) {
            for (int i = 0; i < mTrack.size(); i++) {
                for (int z = 0; z < mSpirits.size(); z++) {
                    if (mTrack.get(i).x > mSpirits.get(z).mCoordinate.x && mTrack.get(i).x < mSpirits.get(z).mCoordinate.x + mSpirits.get(z).mDimension.x) {
                        if (mTrack.get(i).y > mSpirits.get(z).mCoordinate.y && mTrack.get(i).y < mSpirits.get(z).mCoordinate.y + mSpirits.get(z).mDimension.y) {
                            Log.e("isHit", "isHit");
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
