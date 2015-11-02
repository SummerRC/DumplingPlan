package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * 自定义FrameLayout文字飞入飞出效果
 * 游戏的欢迎画面
 * @author SummerRC
 */
public class WelcomeActivity extends Activity implements OnClickListener {
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 去掉标题栏和信息栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome);
		initView();
		GameDataManager.init(getApplicationContext()).clean();
		GameDataManager.init(getApplicationContext());
//		MusicPlayer.init(getApplicationContext());
//		SoundUtil.initSounds(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
//		MusicPlayer.startMusic();
	}

	public void initView() {
		findViewById(R.id.iv_stock).setOnClickListener(WelcomeActivity.this);
		findViewById(R.id.iv_ventilator).setOnClickListener(WelcomeActivity.this);
		findViewById(R.id.iv_setting).setOnClickListener(WelcomeActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_stock:
//				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
				UIHelper.openSelectFoodActivity(this);
				break;
			case R.id.iv_cook_mode:
//				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
//				UIHelper.openLockActivity(this);
				break;
			case R.id.iv_about:				//关于我们
//				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
//				UIHelper.openAboutUsActivity(this);
				break;
			case R.id.iv_help:				//帮助
//				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				break;
			case R.id.iv_setting:			//设置
//				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
//				UIHelper.openSettingActivity(this);
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
