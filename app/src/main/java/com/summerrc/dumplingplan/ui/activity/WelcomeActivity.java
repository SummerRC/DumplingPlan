package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.MusicPlayer;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;
import com.summerrc.dumplingplan.ui.widget.KeywordsFlow;
import java.util.Random;

/**
 * 自定义FrameLayout文字飞入飞出效果
 * 游戏的欢迎画面
 * @author SummerRC
 */
public class WelcomeActivity extends Activity implements OnClickListener {
	public static final String[] keywords = { "木木", "婷女神", "齐凤梨",
			"高小姐", "湖建人","皇上", "夏雨荷", "马总", "妖", "姚","晓冬" };
	private KeywordsFlow keywordsFlow;
	private Handler handler;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 去掉标题栏和信息栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome);
		handler = new Handler();
		initView();
		GameDataManager.init(getApplicationContext()).clean();
		GameDataManager.init(getApplicationContext());
		MusicPlayer.init(getApplicationContext());
		MusicPlayer.startMusic();
		SoundUtil.initSounds(getApplicationContext());
	}

	public void initView() {
		keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsFlow);
		keywordsFlow.setDuration(800l);
		if(bitmap == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.background_welcome);
		}
		keywordsFlow.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				WelcomeActivity.this.findViewById(R.id.rl).setVisibility(View.VISIBLE);
				WelcomeActivity.this.findViewById(R.id.iv_student_mode).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_cook_mode).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_about).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_setting).setOnClickListener(WelcomeActivity.this);
				WelcomeActivity.this.findViewById(R.id.iv_help).setOnClickListener(WelcomeActivity.this);
			}
		}, 1000l);
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
				/*findViewById(R.id.rl).setVisibility(View.GONE);
				if(tag) {
					in();
					tag = false;
				} else {
					out();
					tag = true;
				}
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						findViewById(R.id.rl).setVisibility(View.VISIBLE);
					}
				}, 1000l);*/
				UIHelper.openLockActivity(this);
				break;
			case R.id.iv_about:						//关于我们
				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				break;
			case R.id.iv_help:						//帮助
				SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
				break;
			case R.id.iv_setting:					//设置
				SoundUtil.playSounds(SoundUtil.SETTING, 0, getApplicationContext());
				UIHelper.openSettingActivity(this);
				break;
		}
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	private void in() {
		keywordsFlow.rubAllViews();									// keywordsFlow.rubKeywords();
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
	}

	private void out() {
		keywordsFlow.rubAllViews();									// keywordsFlow.rubKeywords();
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
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
