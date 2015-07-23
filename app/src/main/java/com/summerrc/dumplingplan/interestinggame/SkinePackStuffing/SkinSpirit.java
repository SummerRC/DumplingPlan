package com.summerrc.dumplingplan.interestinggame.SkinePackStuffing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * @author SummerRC on 2015/7/22 0011.
 * 皮精灵类
 */
public class SkinSpirit {
	private Context mContext;
	private Bitmap mBitmap;

	public PointF mCoordinate;							//精灵的坐标

	public PointF mLength;								//精灵的长宽
	private Paint mPaint;								//画笔，可做特殊效果
	private int mType;									//精灵的类型

	public int getmType() {
		return mType;
	}

	public void setmType(int mType) {
		this.mType = mType;
	}

	public SkinSpirit(Context context){
		mPaint = new Paint();
		mContext = context;
		mCoordinate = new PointF();
		mLength = new PointF();
	}

	/**
	 * 导入该精灵的图片
	 * @param id
	 */
	public void loadBitmap(int id){
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
		mLength.x = mBitmap.getWidth();
		mLength.y = mBitmap.getHeight();
	}


	/**
	 * 把精灵画到画布上
	 * @param canvas
	 */
	public void draw(Canvas canvas){
		canvas.drawBitmap(mBitmap, mCoordinate.x, mCoordinate.y, mPaint);
	}

}
