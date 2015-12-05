package com.summerrc.dumplingplan.widget;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;

import java.util.ArrayList;

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
    private ArrayList<AnimationSpirit> animationSpirits;//用于容纳运动的精灵
    private ArrayList<StaticSpirit> staticSpirits;      //用于容纳静止的的精灵
    private long mNextTime = 0L;                        //计算下次生成精灵的时间
    private Drawable mBackground;                       //背景
    private boolean play = false;                       //播放动画

    public WelcomeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        PhoneWidth = wm.getDefaultDisplay().getWidth();
        PhoneHeight = wm.getDefaultDisplay().getHeight();

        mHolder = getHolder();
        mHolder.addCallback(this);

        /** 用于记录手指触摸点的轨迹 */
        mTrack = new ArrayList<>();
        /** 运动的精灵集合,注意运动出屏幕之后要及时从集合移除 */
        animationSpirits = new ArrayList<>();
        /** 初始化静止的精灵集合 */
        staticSpirits = new ArrayList<>();
        initStaticSpirit();
        /** 背景 */
        mBackground = mContext.getResources().getDrawable(R.mipmap.soho_background_welcome);
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
                /** 画背景 */
                drawBackground(canvas);
                /** 绘制静态精灵集合到屏幕上 */
                drawStaticSpirits(canvas);
                /** 检查动态精灵是否还在屏幕内 */
                checkSpirits();
                if(play) {
                    /** 到了计算好的时间就生成精灵 ，间隔时间0.1秒钟左右，用了一个随机数 */
                    if (mNextTime < System.currentTimeMillis()) {
                        initAnimationSpirit();
                        nextGenTime();
                    }
                    drawAnimationSpirits(canvas);
                }
                /** 捕获屏幕点击触摸事件 */
                isHit();
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
     * 修改画背景的方法
     */
    private void initStaticSpirit() {
        StaticSpirit setting = new StaticSpirit(mContext, StaticSpirit.Type.SETTING);
        StaticSpirit start = new StaticSpirit(mContext, StaticSpirit.Type.START);
        StaticSpirit logo = new StaticSpirit(mContext, StaticSpirit.Type.LOGO);
        StaticSpirit help = new StaticSpirit(mContext, StaticSpirit.Type.HELP);
        StaticSpirit award = new StaticSpirit(mContext, StaticSpirit.Type.AWARD);
        staticSpirits.add(setting);
        staticSpirits.add(start);
        staticSpirits.add(logo);
        staticSpirits.add(help);
        staticSpirits.add(award);
    }

    /**
     * 画精灵到画布上
     *
     * @param canvas 画布
     */
    private void drawStaticSpirits(Canvas canvas) {
        for (int i = 0; i < staticSpirits.size(); i++) {
            staticSpirits.get(i).draw(canvas);
        }
    }

    /**
     * 下一次生成精灵的时间，间隔时间1.几秒
     */
    private void nextGenTime() {
        mNextTime = System.currentTimeMillis();
        mNextTime += 300;
    }

    /**
     * 生成精灵，并添加到精灵管理列表
     */
    private void initAnimationSpirit() {
        /** 请修改此方法，使精灵从更多方向抛出 */
        PointF coordinate = new PointF();
        coordinate.x = staticSpirits.get(2).mCoordinate.x;
        coordinate.y = staticSpirits.get(2).mCoordinate.y;
        AnimationSpirit leftSpirit = new AnimationSpirit(mContext, coordinate);
        leftSpirit.loadBitmap(R.mipmap.soho_welcome_anim_dumpling, AnimationSpirit.Type.LEFT);
        AnimationSpirit rightSpirit = new AnimationSpirit(mContext, coordinate);
        rightSpirit.loadBitmap(R.mipmap.soho_welcome_anim_dumpling, AnimationSpirit.Type.RIGHT);
        animationSpirits.add(leftSpirit);
        animationSpirits.add(rightSpirit);
    }

    /**
     * 画精灵到画布上
     *
     * @param canvas 画布
     */
    private void drawAnimationSpirits(Canvas canvas) {
        for (int i = 0; i < animationSpirits.size(); i++) {
            animationSpirits.get(i).draw(canvas);
        }
    }

    /**
     * 检查精灵是否还在屏幕内，不在屏幕内则移除
     */
    private void checkSpirits() {
        for (int i = 0; i < animationSpirits.size(); i++) {
            if (isSpiritValidate(i)) {
                animationSpirits.remove(i);
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
        PointF coordinate = animationSpirits.get(i).mCoordinate;
        return (coordinate.x < -animationSpirits.get(i).mDimension.x || coordinate.x > PhoneWidth || coordinate.y > PhoneHeight);
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
    }

    private void isHit() {
        synchronized (mTrack) {
            for (int i = 0; i < mTrack.size(); i++) {
                for (int z = 0; z < staticSpirits.size(); z++) {
                    if (mTrack.get(i).x > staticSpirits.get(z).mCoordinate.x && mTrack.get(i).x < staticSpirits.get(z).mCoordinate.x + staticSpirits.get(z).mDimension.x) {
                        if (mTrack.get(i).y > staticSpirits.get(z).mCoordinate.y && mTrack.get(i).y < staticSpirits.get(z).mCoordinate.y + staticSpirits.get(z).mDimension.y) {
                            play = !play;
                        }
                    }
                }
            }
        }
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
