package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
		findViewById(R.id.iv_stock).setOnClickListener(WelcomeActivity.this);			//原木
		findViewById(R.id.iv_ventilator).setOnClickListener(WelcomeActivity.this);		//抽油烟机
		findViewById(R.id.iv_setting).setOnClickListener(WelcomeActivity.this);			//设置
		findViewById(R.id.iv_start).setOnClickListener(WelcomeActivity.this);			//开始
		findViewById(R.id.iv_logo).setOnClickListener(WelcomeActivity.this);			//logo
		findViewById(R.id.iv_help).setOnClickListener(WelcomeActivity.this);			//帮助
		findViewById(R.id.iv_jiangbei).setOnClickListener(WelcomeActivity.this);		//奖杯
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_stock:         //原木
//				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
				Animation shake01 = AnimationUtils.loadAnimation(this, R.anim.shake);
				findViewById(R.id.iv_stock).startAnimation(shake01);
				break;
			case R.id.iv_ventilator:    //抽油烟机
//				SoundUtil.playSounds(SoundUtil.ONE_TWO, 0, getApplicationContext());
				Animation shake02 = AnimationUtils.loadAnimation(this, R.anim.shake);
				findViewById(R.id.iv_ventilator).startAnimation(shake02);
				break;
			case R.id.iv_setting:		//设置
//				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
//				UIHelper.openAboutUsActivity(this);
				break;
			case R.id.iv_start:			//开始
                UIHelper.openSelectFoodActivity(this);
//				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				break;
			case R.id.iv_logo:			//logo
//				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
//				UIHelper.openSettingActivity(this);
				break;
            case R.id.iv_help:			//帮助
//				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
//				UIHelper.openSettingActivity(this);
                break;
            case R.id.iv_jiangbei:		//奖杯
//				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
				UIHelper.openAwardActivity(this);
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
