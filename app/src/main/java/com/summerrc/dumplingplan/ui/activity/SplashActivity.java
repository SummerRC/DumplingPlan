package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.ant.liao.GifView;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.MusicPlayer;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * 自定义FrameLayout文字飞入飞出效果
 * 游戏的欢迎画面
 * @author SummerRC
 */
public class SplashActivity extends Activity {
	private Handler handler;
	private int PhoneWidth;
	GifView gif;
	private int PhoneHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 去掉标题栏和信息栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		WindowManager wm=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		PhoneWidth = wm.getDefaultDisplay().getWidth();
		PhoneHeight = wm.getDefaultDisplay().getHeight();

		setContentView(R.layout.activity_splash);
		handler = new Handler();
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void initView() {
		gif = (GifView)findViewById(R.id.gif);
		gif.setShowDimension(PhoneWidth, PhoneHeight);
		gif.setGifImage(R.mipmap.welcome);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				UIHelper.openWelcomeActivity(SplashActivity.this);
			}
		}, 3000l);
	}

	@Override
	protected void onPause() {
		super.onPause();
		gif.destroyDrawingCache();
	}
}
