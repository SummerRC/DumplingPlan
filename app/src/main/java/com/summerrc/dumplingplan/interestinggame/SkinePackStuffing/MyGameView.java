package com.summerrc.dumplingplan.interestinggame.SkinePackStuffing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.config.ScoreResourceManager;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author SummerRC on 2015/7/22 0011.
 * 继承自自定义的SurfaceView，皮接馅游戏的主界面
 */
public class MyGameView extends MySurfaceView {
	private Context mContext;
	private Paint mPaint;
	private int score = 0;

	private ArrayList<PieSpirit> mPieSpirits;			//用于容纳馅的精灵
	private long mNextTime = 0L;						//计算下次生成精灵的时间
	private long mTimeCount;							//显示右上角时间
	public int count = 100;

	private MediaPlayer mPlayer;						//背景音乐播放器

	private Drawable mBackground;						//背景
	private ArrayList<Drawable> skinLit;				//pi
	private int SKIN_Y;
	private Handler handler;

	private static int times;							//饺子的个数

	protected MyGameView(Context context, Handler handler) {
		super(context);
		this.handler = handler;
		mContext = context;
		mPaint = new Paint();
		SKIN_Y = PhoneHeight-250;
		skinLit = new ArrayList<>();
		mBackground = mContext.getResources().getDrawable(R.mipmap.background_cut_food);
		/** 实例化容纳精灵的列表，请自行做好管理精灵的工作 */
		mPieSpirits = new ArrayList<>();
		times = 0;
		/** 初始化播放器 */
		mPlayer=MediaPlayer.create(context, R.raw.buyudaren_bg);
		mPlayer.setLooping(true);
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
		if(mNextTime<System.currentTimeMillis()){
			generateSpirit();
			nextGenTime();
		}
		drawTime(canvas);
		checkSpirits();
		drawSpirits(canvas);
		//isHit();
		drawScore(canvas);
	}

	/**
	 * 下一次生成精灵的时间，间隔时间1.几秒
	 */
	private void nextGenTime(){
		mNextTime = System.currentTimeMillis();
		Random r = new Random();
		int interval = 1000 + r.nextInt(100);
		mNextTime += interval;
	}

	/**
	 * 生成精灵，并添加到精灵管理列表
	 */
	private void generateSpirit(){
		/** 请修改此方法，使精灵从更多方向抛出 */
		PieSpirit pieSpirit = new PieSpirit(mContext);
		pieSpirit.loadBitmap(R.mipmap.pie);

		switch(times%4){
			case 0:
				pieSpirit.setmType(1);
				pieSpirit.mCoordinate.x = PhoneWidth/5-40;
				pieSpirit.mCoordinate.y = 0;
				break;
			case 1:
				pieSpirit.setmType(2);
				pieSpirit.mCoordinate.x = PhoneWidth*2/5-40;
				pieSpirit.mCoordinate.y = 0;
				break;
			case 2:
				pieSpirit.setmType(3);
				pieSpirit.mCoordinate.x = PhoneWidth*3/5-40;
				pieSpirit.mCoordinate.y = 0;
				break;
			case 3:
				pieSpirit.setmType(4);
				pieSpirit.mCoordinate.x = PhoneWidth*4/5-40;
				pieSpirit.mCoordinate.y = 0;
				break;
			default:
				pieSpirit.setmType(1);
				pieSpirit.mCoordinate.x = PhoneWidth/5;
				pieSpirit.mCoordinate.y = 0;
		}

		times++;
		Random r = new Random();
		pieSpirit.mV.x = 0;
		pieSpirit.mV.y = 2 + r.nextInt(5);
		mPieSpirits.add(pieSpirit);
	}

	/**
	 * 开始和停止背景音乐
	 * @param holder 持有Surface
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		mPlayer.start();
	}

	/**
	 * 开始和停止背景音乐
	 * @param holder 持有Surface
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
		mPlayer.stop();
	}


	/**
	 * 画精灵到画布上
	 * @param canvas 画布
	 */
	private void drawSpirits(Canvas canvas){
		for(int i=0;i< mPieSpirits.size();i++){
			mPieSpirits.get(i).draw(canvas);
		}
	}

	/**
	 * 检查精灵是否还在屏幕内，不在屏幕内则移除
	 */
	private void checkSpirits(){
		for(int i=0; i< mPieSpirits.size(); i++ ){
			if(isSpiritValidate(i)){
				mPieSpirits.remove(i);
				i -=1;
			}
		}
	}

	/**
	 * 具体检查精灵是否在屏幕内的方法
	 * @param i 第几个精灵
	 * @return true 无效
	 */
	private boolean isSpiritValidate(int i){
		PointF coordinate = mPieSpirits.get(i).mCoordinate;
		return coordinate.y>PhoneHeight;
	}

	private void drawScore(Canvas canvas){
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		Bitmap bitmap_num1 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(score/10));
		Bitmap bitmap_num2 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(score%10));
		canvas.drawBitmap(bitmap_num1, 50, 50, mPaint);
		canvas.drawBitmap(bitmap_num2, 90, 50, mPaint);
	}



	/**
	 * 修改画背景的方法
	 * @param canvas 画布
	 */
	private void drawBackground(Canvas canvas){
		canvas.drawColor(0xFF000000);
		mBackground.setBounds(0, 0, PhoneWidth, PhoneHeight);
		mBackground.draw(canvas);
		Bitmap bitmap_clock = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
		canvas.drawBitmap(bitmap_clock, PhoneWidth - 210, 35, mPaint);
		/** 四个皮 */
		Bitmap bitmap_skin = BitmapFactory.decodeResource(getResources(), R.mipmap.skin);
		for(int i=1; i<5; i++) {
			canvas.drawBitmap(bitmap_skin, (i*PhoneWidth/5-100), SKIN_Y, mPaint);
		}
	}

	/**
	 * 右上角时间和页面判断跳转
	 * @param canvas 画布
	 */
	private void drawTime(Canvas canvas){
		if(System.currentTimeMillis()-mTimeCount>1000){
			mTimeCount = System.currentTimeMillis();
			count--;
			if(count == 1){
				super.mDrawThread.stop();
				Message message = new Message();
				if(score > 70) {
					message.what = IntentConstant.WIN;
				} else {
					message.what = IntentConstant.LOSE;
				}

				handler.sendMessage(message);
			}
		}

		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);

		Bitmap bitmap_num1 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(count/10));
		Bitmap bitmap_num2 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(count%10));
		canvas.drawBitmap(bitmap_num1, PhoneWidth - 140, 50, mPaint);
		canvas.drawBitmap(bitmap_num2, PhoneWidth - 100, 50, mPaint);
	}

	/**
	 * 屏幕点击事件的响应方法
	 * @param event 事件
	 * @return true 处理掉了
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN) {
			handleActionDown(event);
		}
		return true;
	}

	/**
	 * 手指按下的响应方法
	 * @param event  事件
	 */
	private void handleActionDown(MotionEvent event){
		float x = event.getX();
		float y = event.getY();
		if(!(y<SKIN_Y+200 && y>SKIN_Y-200)) {			//手指点击事件不发生在皮所在的线上就过滤掉
			return;
		}
		int skinType = 0;
		if(x<(PhoneWidth/5+50) && x>(PhoneWidth/5-50)) {
			skinType = 1;
		}
		if(x<(2*PhoneWidth/5+50) && x>(2*PhoneWidth/5-50)) {
			skinType = 2;
		}
		if(x<(3*PhoneWidth/5+50) && x>(3*PhoneWidth/5-50)) {
			skinType = 3;
		}
		if(x<(4*PhoneWidth/5+50) && x>(4*PhoneWidth/5-50)) {
			skinType = 4;
		}
		for(int i=0; i< mPieSpirits.size(); i++){
			if(mPieSpirits.get(i).mCoordinate.y<SKIN_Y+50 && mPieSpirits.get(i).mCoordinate.y>SKIN_Y-50){
				if(mPieSpirits.get(i).getmType() == skinType) {
					score += skinType;
					mPieSpirits.get(i).loadBitmap(R.mipmap.little_dumpling);
				}
			}
		}
	}


}
