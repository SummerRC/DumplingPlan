package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class WelcomeActivity extends Activity implements OnClickListener {
	private GifView gif;
	private Handler handler;
	private Bitmap bitmap;
	private int PhoneWidth;
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

		setContentView(R.layout.activity_welcome);
		handler = new Handler();
		initView();
		GameDataManager.init(getApplicationContext()).clean();
		GameDataManager.init(getApplicationContext());
		MusicPlayer.init(getApplicationContext());
		SoundUtil.initSounds(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		MusicPlayer.startMusic();
	}

	public void initView() {
		if(bitmap == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.background_welcome);
		}
		gif = (GifView)findViewById(R.id.gif);
		gif.setShowDimension(PhoneWidth, PhoneHeight);
		gif.setGifImage(R.mipmap.welcome);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				WelcomeActivity.this.gif.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
				WelcomeActivity.this.findViewById(R.id.rl).setVisibility(View.VISIBLE);
				WelcomeActivity.this.findViewById(R.id.iv_student_mode).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_cook_mode).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_about).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_setting).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_help).setOnClickListener(WelcomeActivity.this);
			}
		}, 5000l);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_student_mode:		//学徒模式
				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
				UIHelper.openSelectFoodActivity(this);
				break;
			case R.id.iv_cook_mode:			//厨神之路
				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
				UIHelper.openLockActivity(this);
				break;
			case R.id.iv_about:				//关于我们
				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				UIHelper.openAboutUsActivity(this);
				break;
			case R.id.iv_help:				//帮助
				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				break;
			case R.id.iv_setting:			//设置
				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
				UIHelper.openSettingActivity(this);
				break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(bitmap != null && !bitmap.isRecycled()) {
			bitmap.isRecycled();
			bitmap = null;
			System.gc();
		}
	}
}
