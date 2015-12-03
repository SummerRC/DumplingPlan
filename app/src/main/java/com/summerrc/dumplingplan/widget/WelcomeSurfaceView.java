package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * @author SummerRC on 2015/12/3 0011.
 * 自定义的SurfaceView，用于播放弹出动画
 */
public class WelcomeSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mHolder;
	public DrawThread mDrawThread;
	public int PhoneWidth;
	public int PhoneHeight;

	public WelcomeSurfaceView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
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
	
	protected void myDraw(Canvas canvas){
		
	}
	
	public class DrawThread implements Runnable{
		private boolean mRun = true;
		@Override
		public void run() {
			while(mRun){
				Canvas canvas = mHolder.lockCanvas();
				myDraw(canvas);
				mHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void stop(){
			mRun = false;
		}
	}
}
