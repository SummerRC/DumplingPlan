package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * @author SummerRC on 2015/12/3 0011.
 *         自定义的SurfaceView，用于管理生命周期,子类实现具体的绘制逻辑
 */
public class WelcomeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private DrawThread mDrawThread;
    public int PhoneWidth;
    public int PhoneHeight;

    public WelcomeSurfaceView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        PhoneWidth = wm.getDefaultDisplay().getWidth();
        PhoneHeight = wm.getDefaultDisplay().getHeight();
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
    }

    /**
     * 提供子类的接口,有子类实现具体逻辑
     * @param canvas    画布
     */
    protected void myDraw(Canvas canvas) {

    }
}
