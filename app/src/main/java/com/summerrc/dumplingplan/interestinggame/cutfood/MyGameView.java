package com.summerrc.dumplingplan.interestinggame.cutfood;

import java.io.IOException;
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
	private int cutId;						//音效资源id

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
		mPlayer.start();
		mSoundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,100);
		mExplodeSoundId = mSoundPool.load(context,R.raw.bomb_explode,1);
		cutId = mSoundPool.load(context,R.raw.cut,1);
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
		/** 炸弹是2-3秒钟出现一次 */
		if(mNextTimeBoom<System.currentTimeMillis()){
			drawBoom();
			nextGenTimeBoom();
		}

		drawTime(canvas);
		checkSpirites();
		checkBooms();
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
	 * 下一次生成炸弹的时间，间隔时间2-3秒
	 */
	private void nextGenTimeBoom(){
		mNextTimeBoom = System.currentTimeMillis()+1000;
		Random r = new Random();
		int interval = 2000+r.nextInt(1000);
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
		int randNum = 1 + rand.nextInt(9);
		int cakeId;
		switch(randNum){
			case 1:
				cakeId=R.mipmap.cut_food_cabbage;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 2:
				cakeId=R.mipmap.cut_food_cucumber;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 3:
				cakeId=R.mipmap.cut_food_eggplant;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 4:
				cakeId=R.mipmap.cut_food_tomato;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 5:
				cakeId=R.mipmap.cut_food_beef;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 6:
				cakeId=R.mipmap.cut_food_beef;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 7:
				cakeId=R.mipmap.cut_food_pork;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 8:
				cakeId=R.mipmap.cut_food_chicken;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 9:
				cakeId=R.mipmap.cut_food_crab;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
		}

		Random r = new Random();
		spirit.mCoord.x = PhoneWidth/4-300 + r.nextInt(400);
		spirit.mCoord.y = PhoneHeight;
		spirit.mV.x = 7+r.nextInt(5);
		spirit.mV.y = -(30 + (r.nextInt(10)));
		/*mSpirits.get(0).setmType(mType);*/
		mSpirits.add(spirit);
	}

	/**
	 * 画炸弹
	 */
	private void drawBoom(){
		Spirit spirit = new Spirit(mContext);
		spirit.loadBitmap(R.mipmap.cut_food_oil);
		Random r = new Random();

		Random rand = new Random();
		int randNum = 1 + rand.nextInt(3);
		int cakeId;
		switch(randNum) {
			case 1:
				cakeId = R.mipmap.cut_food_oil;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 2:
				cakeId = R.mipmap.cut_food_salt;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
			case 3:
				cakeId = R.mipmap.cut_food_sauce;
				spirit.loadBitmap(cakeId);
				spirit.setmType(cakeId);
				//t_num=randNum;
				break;
		}
		spirit.mCoord.x = PhoneWidth/4-300 + r.nextInt(400);
		spirit.mCoord.y = PhoneHeight;
		spirit.mV.x = 7+r.nextInt(5);
		spirit.mV.y = -(30 + (r.nextInt(10)));
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
	private void checkBooms(){
		for(int i=0; i<mBooms.size(); i++ ){
			if(isBoomValidate(i)){
				mBooms.remove(i);
				i -=1;
			}
		}
	}

	/**
	 * 具体检查精灵是否在屏幕内的方法
	 * @param i
	 * @return
	 */
	private boolean isBoomValidate(int i){
		PointF coord = mBooms.get(i).mCoord;
		if(coord.x<-mBooms.get(i).mDimention.x  || coord.x>PhoneWidth || coord.y>PhoneHeight){
			return true;
		}
		return false;
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
		int num0 = score/100;
		int num1 = score%100/10;
		int num2 = score%100%10;
		if(num0 != 0) {
			Bitmap bitmap_num0 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(num0));
			canvas.drawBitmap(bitmap_num0, 50, 50, mPaint);
		}
		Bitmap bitmap_num1 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(num1));
		Bitmap bitmap_num2 = BitmapFactory.decodeResource(getResources(), ScoreResourceManager.getScoreResource(num2));
		canvas.drawBitmap(bitmap_num1, 90, 50, mPaint);
		canvas.drawBitmap(bitmap_num2, 130, 50, mPaint);

		//canvas.drawText("Score:"+score, 100, 50, paint);
	}



	private void isHit(){
		synchronized (mTrack) {
			for(int i=0;i<mTrack.size();i++){
				for(int z=0;z<mSpirits.size();z++){
					if(mTrack.get(i).x>mSpirits.get(z).mCoord.x&&mTrack.get(i).x<mSpirits.get(z).mCoord.x+mSpirits.get(z).mDimention.x){
						if(mTrack.get(i).y>mSpirits.get(z).mCoord.y&&mTrack.get(i).y<mSpirits.get(z).mCoord.y+mSpirits.get(z).mDimention.y){
							switch(mSpirits.get(z).getmType()){
								case R.mipmap.cut_food_cabbage:
									initCutCake(R.mipmap.cut_food_cabbage_cut, R.mipmap.cut_food_cabbage_cut, z);
									score+=4;
									break;
								case R.mipmap.cut_food_cucumber:
									initCutCake(R.mipmap.cut_food_cucumber_cut, R.mipmap.cut_food_cucumber_cut, z);
									score+=4;
									break;
								case R.mipmap.cut_food_eggplant:
									initCutCake(R.mipmap.cut_food_eggplant_cut, R.mipmap.cut_food_eggplant_cut, z);
									score+=6;
									break;
								case R.mipmap.cut_food_tomato:
									initCutCake(R.mipmap.cut_food_tomato_cut, R.mipmap.cut_food_tomato_cut, z);
									score+=6;
									break;
								case R.mipmap.cut_food_beef:
									initCutCake(R.mipmap.cut_food_beef_cut, R.mipmap.cut_food_beef_cut, z);
									score+=8;
									break;
								case R.mipmap.cut_food_pork:
									initCutCake(R.mipmap.cut_food_pork_cut, R.mipmap.cut_food_pork_cut, z);
									score+=8;
									break;
								case R.mipmap.cut_food_chicken:
									initCutCake(R.mipmap.cut_food_chicken_cut, R.mipmap.cut_food_chicken_cut, z);
									score+=10;
									break;
								case R.mipmap.cut_food_crab:
									initCutCake(R.mipmap.cut_food_crab_cut, R.mipmap.cut_food_crab_cut, z);
									score+=10;
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
								switch (mBooms.get(z).getmType()) {
									case R.mipmap.cut_food_salt:
										initCutBoom(R.mipmap.cut_food_salt_cut, z);
										score -= 4;
										break;
									case R.mipmap.cut_food_oil:
										initCutBoom(R.mipmap.cut_food_oil_cut, z);
										score -= 6;
										break;
									case R.mipmap.cut_food_sauce:
										initCutBoom(R.mipmap.cut_food_sauce_cut, z);
										score -= 8;
										break;
								}
							}else{
								score = 0;
							}
							mSpirits.removeAll(mSpirits);
						}
					}
				}
			}
		}
	}

	private void initCutCake(int id1, int id2, int z) {
		Spirit spirit_left = new Spirit(mContext);
		spirit_left.loadBitmap(id1);
		spirit_left.mCoord.x=mSpirits.get(z).mCoord.x+60;
		spirit_left.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirit_left);

		/*Spirit spirit_right = new Spirit(mContext);
		spirit_right.loadBitmap(id2);
		spirit_right.mCoord.x=mSpirits.get(z).mCoord.x-60;
		spirit_right.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirit_right);*/

		mSpirits.remove(z);
	}

	private void initCutBoom(int id1, int z) {
		Spirit spirit_left = new Spirit(mContext);
		spirit_left.loadBitmap(id1);
		spirit_left.mCoord.x=mSpirits.get(z).mCoord.x+60;
		spirit_left.mCoord.y=mSpirits.get(z).mCoord.y;
		mBooms.add(spirit_left);
		mBooms.remove(z);
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
		mSoundPool.play(cutId, 1, 1, 1, 0, 1);
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
