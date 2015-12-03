package com.summerrc.dumplingplan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.interestinggame.cutfood.Spirit;
import com.summerrc.dumplingplan.utils.MusicPlayer;
import com.summerrc.dumplingplan.utils.SoundUtil;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author SummerRC on 2015/7/21 0011.
 * 继承自自定义的WelcomeSurfaceView，切食材游戏的主界面
 */
public class PlayAnimationSurfaceView extends WelcomeSurfaceView {
	private Context mContext;
	private ArrayList<PointF> mTrack;
	private final static int POINT_LIMIT = 5;
	private Paint mPaint;

	private int mBladeColor = 0xFFFFFFFF;				//此变量用于修改刀光的颜色
	private ArrayList<Spirit> mSpirits;				    //用于容纳食材精灵
	private ArrayList<Spirit> mBooms;					//用于容纳炸弹精灵
	private long mNextTime = 0L;						//计算下次生成精灵的时间

	private Drawable mBackground;						//背景
	private Handler handler;

	protected PlayAnimationSurfaceView(Context context, Handler handler) {
		super(context);
		this.handler = handler;
		mContext = context;
		mPaint = new Paint();
		mBackground = mContext.getResources().getDrawable(R.mipmap.background_cut_food);
		mTrack = new ArrayList<>();
		/** 实例化容纳精灵的列表，请自行做好管理精灵的工作 */
		mSpirits = new ArrayList<>();
		mBooms = new ArrayList<>();
		MusicPlayer.startMusic();
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

		checkSpirites();
		drawSpirits(canvas);
		/** 画刀光 */
		drawBlade(canvas);
		isHit();
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
		mSpirits.add(spirit);
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

	private void isHit(){
		synchronized (mTrack) {
			for(int i=0;i<mTrack.size();i++){
				for(int z=0;z<mSpirits.size();z++){
					if(mTrack.get(i).x>mSpirits.get(z).mCoord.x&&mTrack.get(i).x<mSpirits.get(z).mCoord.x+mSpirits.get(z).mDimention.x){
						if(mTrack.get(i).y>mSpirits.get(z).mCoord.y&&mTrack.get(i).y<mSpirits.get(z).mCoord.y+mSpirits.get(z).mDimention.y){
							switch(mSpirits.get(z).getmType()){
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


	private void initCutCake(int id1, int id2, int z) {
		Spirit spirit_left = new Spirit(mContext);
		spirit_left.loadBitmap(id1);
		spirit_left.mCoord.x=mSpirits.get(z).mCoord.x+60;
		spirit_left.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirit_left);
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
		SoundUtil.playSounds(SoundUtil.CUT, 0, mContext.getApplicationContext());
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
