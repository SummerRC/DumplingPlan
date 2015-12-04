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

	private final static float ACCELERATION_Y = 1F;		//Y方向加速度
	private final static float ACCELERATION_X = 0F;		//X方向加速度
	public PointF mCoordinate;							//精灵的坐标
	private PointF mV;									//精灵的速度
	public PointF mDimension;							//精灵的长宽
	public enum Type {
		LEFT, RIGHT
	}

	public AnimationSpirit(Context context){
		mContext = context;
		mCoordinate = new PointF();
		mV = new PointF();
		mDimension = new PointF();
		mCoordinate.x = 300;
		mCoordinate.y = 0;
	}

	/**
	 * 导入该精灵的图片
	 * @param id	int
	 */
	public void loadBitmap(int id, Type type){
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
		mDimension.x = mBitmap.getWidth();
		mDimension.y = mBitmap.getHeight();
		switch (type) {
			case LEFT:
				setLeftMove();
				break;
			case RIGHT:
				setRightMove();
				break;
		}
	}

	private void setLeftMove() {
		mCoordinate.x = 300;
		mCoordinate.y = 0;
		mV.x = 7;
		mV.y = -30;
	}

	private void setRightMove() {
		mCoordinate.x = 600;
		mCoordinate.y = 0;
		mV.x = -7;
		mV.y = -30;
	}


	/**
	 * 把精灵画到画布上
	 * @param canvas	canvas
	 */
	public void draw(Canvas canvas){
		canvas.drawBitmap(mBitmap, mCoordinate.x, mCoordinate.y, new Paint());
		move();
	}

	/**
	 * 计算精灵的移动
	 */
	public void move(){
		mCoordinate.x += mV.x;
		mCoordinate.y += mV.y;

		mV.x += ACCELERATION_X;
		mV.y += ACCELERATION_Y;
	}
}
