package com.summerrc.dumplingplan.interestinggame.cutfood;

import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.config.ScoreResourceManager;

/**
 * @author SummerRC on 2015/7/21 0011.
 * 继承自自定义的SurfaceView，切食材游戏的主界面
 */
public class MyGameView extends MySurfaceView {
	private Context mContext;
	private ArrayList<PointF> mTrack;
	private final static int POINT_LIMIT = 5;
	private Paint mPaint;
	private int score = 0;

	private int mBladeColor = 0xFFFFFFFF;				//此变量用于修改刀光的颜色
	private ArrayList<Spirit> mSpirits;				    //用于容纳食材精灵
	private ArrayList<Spirit> mBooms;					//用于容纳炸弹精灵
	private long mNextTime = 0L;						//计算下次生成精灵的时间
	private long mNextTimeBoom = 0L;
	private long mTimeCount;							//显示右上角时间
	public int count = 60;

	private MediaPlayer mPlayer;						//背景音乐播放器
	private SoundPool mSoundPool;						//音效
	private int mExplodeSoundId;						//音效资源id

	private Drawable mBackground;						//背景
	private Handler handler;

	protected MyGameView(Context context, Handler handler) {
		super(context);
		this.handler = handler;
		mContext = context;
		mPaint = new Paint();
		mBackground = mContext.getResources().getDrawable(R.mipmap.background_cut_food);
		mTrack = new ArrayList<>();
		/** 实例化容纳精灵的列表，请自行做好管理精灵的工作 */
		mSpirits = new ArrayList<>();
		mBooms = new ArrayList<>();
		/** 初始化播放器 */
		mPlayer=MediaPlayer.create(context, R.raw.lianliankan_bg_two);
		mPlayer.setLooping(true);
		mSoundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,100);
		mExplodeSoundId = mSoundPool.load(context,R.raw.bomb_explode,1);
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
			mSoundPool.play(mExplodeSoundId, 1, 1, 1, 0, 1);
			nextGenTime();
		}
		/** 炸弹是4-6秒钟出现一次 */
		if(mNextTimeBoom<System.currentTimeMillis()){
			drawBoom();
			nextGenTimeBoom();
		}

		drawTime(canvas);
		checkSpirites();
		drawSpirits(canvas);
		/** 画刀光 */
		drawBlade(canvas);
		isHit();
		isHitBomb();
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
	 * 下一次生成炸弹的时间，间隔时间4-6秒
	 */
	private void nextGenTimeBoom(){
		mNextTimeBoom = System.currentTimeMillis()+1000;
		Random r = new Random();
		int interval = 3000+r.nextInt(2000);
		mNextTimeBoom += interval;
	}

	/**
	 * 生成精灵，并添加到精灵管理列表
	 */
	private void generateSpirit(){
		/** 请修改此方法，使精灵从更多方向抛出 */
		Spirit spirit = new Spirit(mContext);
		spirit.loadBitmap(R.mipmap.ic_launcher);

		Random rand = new Random();
		int randNum = 1 + rand.nextInt(5);
		int cakeId;
		switch(randNum){
			case 1:
				cakeId=R.drawable.orange;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 2:
				cakeId=R.drawable.papaya;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 3:
				cakeId=R.drawable.peach;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 4:
				cakeId=R.drawable.watermellon;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 5:
				cakeId=R.drawable.strawberry;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
		}

		Random r = new Random();
		spirit.mCoord.x = PhoneWidth/4-300 + r.nextInt(400);
		spirit.mCoord.y = PhoneHeight;
		spirit.mV.x = 10+r.nextInt(5);
		spirit.mV.y = -(45 + (r.nextInt(20)+10));
		/*mSpirits.get(0).setmType(mType);*/
		mSpirits.add(spirit);
	}

	/**
	 * 画炸弹
	 */
	private void drawBoom(){
		Spirit spirit = new Spirit(mContext);
		spirit.loadBitmap(R.drawable.boom);
		Random r = new Random();

		spirit.mCoord.x = PhoneWidth/4-300 + r.nextInt(400);
		spirit.mCoord.y = PhoneHeight;
		spirit.mV.x = 10+r.nextInt(5);
		spirit.mV.y = -(45 + (r.nextInt(20)+10));
		/*mSpirits.get(0).setmType(mType);*/
		mBooms.add(spirit);
	}

	/**
	 * 开始和停止背景音乐
	 * @param holder
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
		mPlayer.start();
	}

	/**
	 * 开始和停止背景音乐
	 * @param holder
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
		mPlayer.stop();
		mSoundPool.release();
	}


	/**
	 * 画精灵到画布上
	 * @param canvas 画布
	 */
	private void drawSpirits(Canvas canvas){
		for(int i=0;i<mSpirits.size();i++){
			mSpirits.get(i).draw(canvas);
		}
		for(int i=0;i<mBooms.size();i++){
			mBooms.get(i).draw(canvas);
		}
	}

	/**
	 * 检查精灵是否还在屏幕内，不在屏幕内则移除
	 */
	private void checkSpirites(){
		for(int i=0; i<mSpirits.size(); i++ ){
			if(isSpiriteValidate(i)){
				mSpirits.remove(i);
				i -=1;
			}
		}
	}

	/**
	 * 具体检查精灵是否在屏幕内的方法
	 * @param i
	 * @return
	 */
	private boolean isSpiriteValidate(int i){
		PointF coord = mSpirits.get(i).mCoord;
		if(coord.x<-mSpirits.get(i).mDimention.x  || coord.x>PhoneWidth || coord.y>PhoneHeight){
			return true;
		}
		return false;
	}

	private void drawScore(Canvas canvas){
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		Bitmap bitmap_num1 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(score/10));
		Bitmap bitmap_num2 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(score%10));
		canvas.drawBitmap(bitmap_num1, 50, 50, mPaint);
		canvas.drawBitmap(bitmap_num2, 90, 50, mPaint);

		//canvas.drawText("Score:"+score, 100, 50, paint);
	}



	private void isHit(){
		synchronized (mTrack) {
			for(int i=0;i<mTrack.size();i++){
				for(int z=0;z<mSpirits.size();z++){
					if(mTrack.get(i).x>mSpirits.get(z).mCoord.x&&mTrack.get(i).x<mSpirits.get(z).mCoord.x+mSpirits.get(z).mDimention.x){
						if(mTrack.get(i).y>mSpirits.get(z).mCoord.y&&mTrack.get(i).y<mSpirits.get(z).mCoord.y+mSpirits.get(z).mDimention.y){
							switch(mSpirits.get(z).getmType()){
								case R.drawable.orange:
									initCutCake(R.drawable.orangep1, R.drawable.orangep2, z);
									score+=10;
									break;
								case R.drawable.papaya:
									initCutCake(R.drawable.papayap1, R.drawable.papayap2, z);
									score+=10;
									break;
								case R.drawable.peach:
									initCutCake(R.drawable.peachp1, R.drawable.peachp2, z);
									score+=1;
									break;
								case R.drawable.strawberry:
									initCutCake(R.drawable.strawberryp1, R.drawable.strawberryp2, z);
									score+=1;
									break;
								case R.drawable.watermellon:
									initCutCake(R.drawable.watermellonp1, R.drawable.watermellonp2, z);
									score+=1;
									break;
							}
						}
					}
				}
			}
		}
	}


	private void isHitBomb(){
		synchronized (mTrack) {
			for(int i=0;i<mTrack.size();i++){
				for(int z=0;z<mBooms.size();z++){
					if(mTrack.get(i).x>mBooms.get(z).mCoord.x && mTrack.get(i).x<mBooms.get(z).mCoord.x+mBooms.get(z).mDimention.x){
						if(mTrack.get(i).y>mBooms.get(z).mCoord.y && mTrack.get(i).y<mBooms.get(z).mCoord.y+mBooms.get(z).mDimention.y){
							if(score >= 10){
								score -= 10;
							}else{
								score = 0;
							}
							mBooms.remove(z);
							mSpirits.removeAll(mSpirits);
						}
					}
				}
			}
		}
	}

	private void initCutCake(int id1, int id2, int z) {
		Spirit spirit_left = new Spirit(mContext);
		Spirit spirit_right = new Spirit(mContext);
		spirit_left.loadBitmap(id1);
		spirit_left.mCoord.x=mSpirits.get(z).mCoord.x+60;
		spirit_left.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirit_left);

		spirit_right.loadBitmap(id2);
		spirit_right.mCoord.x=mSpirits.get(z).mCoord.x-60;
		spirit_right.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirit_right);
		mSpirits.remove(z);
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
		canvas.drawBitmap(bitmap_clock, PhoneWidth-210, 35, mPaint);
	}

	/**
	 * 画刀光到画布上
	 * @param canvas 画布
	 */
	private void drawBlade(Canvas canvas){
		mPaint.setColor(0xFFFFFFFF);
		synchronized(mTrack){
			Path path = new Path();
			Float startX, startY;
			Float controlX,controlY;
			Float endX, endY;

			int strokeWidth = 8;
			mPaint.setStyle(Style.STROKE);

			if(mTrack.size()>1){
				endX =  mTrack.get(0).x;
				endY =  mTrack.get(0).y;

				for(int i=0;i<mTrack.size()-1;i++){
					startX = endX;
					startY = endY;
					controlX = mTrack.get(i).x;
					controlY = mTrack.get(i).y;
					endX = (controlX + mTrack.get(i+1).x)/2;
					endY = (controlY + mTrack.get(i+1).y)/2;
					path.moveTo(startX, startY);
					path.quadTo(controlX, controlY, endX, endY);
					mPaint.setColor(mBladeColor);
					mPaint.setStrokeWidth(strokeWidth++);
					canvas.drawPath(path, mPaint);

					path.reset();
				}

				startX = endX;
				startY = endY;
				endX = mTrack.get(mTrack.size()-1).x;
				endY = mTrack.get(mTrack.size()-1).y;
				path.moveTo(startX, startY);
				path.lineTo(endX, endY);
				mPaint.setStrokeWidth(strokeWidth++);
				mPaint.setColor(mBladeColor);
				canvas.drawPath(path, mPaint);

				mTrack.remove(0);
			}
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
				/*Intent intent = new Intent(mContext, GameStopActivity.class);
				intent.putExtra("Score", ""+ scroe);
				mContext.startActivity(intent);*/
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

		//canvas.drawText("Time:" + count, PhoneWidth - 200, 100, paint);
	}

	/**
	 * 屏幕点击事件的响应方法
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			handleActionDown(event);
		}else if(event.getAction()==MotionEvent.ACTION_MOVE){
			handleActionMove(event);
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			handleActionUp();
		}
		return true;
	}

	/**
	 * 手指按下的响应方法
	 * @param event
	 */
	private void handleActionDown(MotionEvent event){
		PointF point = new PointF(event.getX(),event.getY());
		synchronized(mTrack){
			mTrack.add(point);
		}
	}

	/**
	 * 手指拖动的响应方法
	 * @param event
	 */
	private void handleActionMove(MotionEvent event){
		PointF point = new PointF(event.getX(),event.getY());

		synchronized(mTrack){
			if(mTrack.size()>=POINT_LIMIT){
				mTrack.remove(0);
			}
			mTrack.add(point);
		}
	}

	/**
	 * 手指抬起的响应方法
	 */
	private void handleActionUp(){
		synchronized(mTrack){
			mTrack.clear();
		}
	}

}
