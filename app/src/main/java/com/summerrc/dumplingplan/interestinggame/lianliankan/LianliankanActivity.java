package com.summerrc.dumplingplan.interestinggame.lianliankan;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.utils.SoundUtil;
import com.summerrc.dumplingplan.utils.UIHelper;

import java.lang.ref.WeakReference;

public class LianliankanActivity extends Activity
		implements OnClickListener,OnTimerListener,OnStateListener,OnToolsChangeListener{

	private ImageButton btnPlay;
	private ImageButton btnRefresh;
	private ImageButton btnTip;
	private ImageView imgTitle;
	private GameView gameView;
	private SeekBar progress;
	private MyDialog dialog;
	private ImageView clock;
	private TextView textRefreshNum;
	private TextView textTipNum;

	private MediaPlayer player;

	private MyHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_lianliankan);
		handler = new MyHandler(this);
		btnPlay = (ImageButton) findViewById(R.id.play_btn);
		btnRefresh = (ImageButton) findViewById(R.id.refresh_btn);
		btnTip = (ImageButton) findViewById(R.id.tip_btn);
		imgTitle = (ImageView) findViewById(R.id.title_img);
		gameView = (GameView) findViewById(R.id.game_view);
		clock = (ImageView) findViewById(R.id.clock);
		progress = (SeekBar) findViewById(R.id.timer);
		textRefreshNum = (TextView) findViewById(R.id.text_refresh_num);
		textTipNum = (TextView) findViewById(R.id.text_tip_num);
		progress.setMax(gameView.getTotalTime());

		btnPlay.setOnClickListener(this);
		btnRefresh.setOnClickListener(this);
		btnTip.setOnClickListener(this);
		gameView.setOnTimerListener(this);
		gameView.setOnStateListener(this);
		gameView.setOnToolsChangedListener(this);
		GameView.initSound(this);

		Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale_anim);
		imgTitle.startAnimation(scale);
		btnPlay.startAnimation(scale);

		player = MediaPlayer.create(this, R.raw.lianliankan_bg_one);
		player.setLooping(true);//设置循环播放
		player.start();

//        GameView.soundPlay.play(GameView.ID_SOUND_BACK2BG, -1);
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameView.setMode(GameView.PAUSE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		gameView.setMode(GameView.QUIT);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
			case R.id.play_btn:
				Animation scaleOut = AnimationUtils.loadAnimation(this,R.anim.scale_anim_out);
				Animation transIn = AnimationUtils.loadAnimation(this,R.anim.trans_in);

				btnPlay.startAnimation(scaleOut);
				btnPlay.setVisibility(View.GONE);
				imgTitle.setVisibility(View.GONE);
				gameView.setVisibility(View.VISIBLE);

				btnRefresh.setVisibility(View.VISIBLE);
				btnTip.setVisibility(View.VISIBLE);
				progress.setVisibility(View.VISIBLE);
				clock.setVisibility(View.VISIBLE);
				textRefreshNum.setVisibility(View.VISIBLE);
				textTipNum.setVisibility(View.VISIBLE);

				btnRefresh.startAnimation(transIn);
				btnTip.startAnimation(transIn);
				gameView.startAnimation(transIn);
				player.pause();
				gameView.startPlay();
				break;
			case R.id.refresh_btn:
				Animation shake01 = AnimationUtils.loadAnimation(this,R.anim.shake);
				btnRefresh.startAnimation(shake01);
				gameView.refreshChange();
				break;
			case R.id.tip_btn:
				Animation shake02 = AnimationUtils.loadAnimation(this,R.anim.shake);
				btnTip.startAnimation(shake02);
				gameView.autoClear();
				break;
		}
	}

	@Override
	public void onTimer(int leftTime) {
		Log.i("onTimer", leftTime+"");
		progress.setProgress(leftTime);
	}

	@Override
	public void OnStateChanged(int StateMode) {
		switch(StateMode){
			case GameView.WIN:
				if(GameDataManager.init(getApplicationContext()).getUnLock() <= 3) {
					GameDataManager.init(getApplicationContext()).setUnLock(4);
				}
				handler.sendEmptyMessage(0);
				break;
			case GameView.LOSE:
				handler.sendEmptyMessage(1);
				break;
			case GameView.PAUSE:
				player.stop();
				gameView.player.stop();
				gameView.stopTimer();
				break;
			case GameView.QUIT:
				player.release();
				gameView.player.release();
				gameView.stopTimer();
				break;
		}
	}

	@Override
	public void onRefreshChanged(int count) {
		textRefreshNum.setText(""+gameView.getRefreshNum());
	}

	@Override
	public void onTipChanged(int count) {
		textTipNum.setText(""+gameView.getTipNum());
	}

	public void quit(){
		UIHelper.openLockActivity(this);
	}

	static class MyHandler extends Handler {
		WeakReference<LianliankanActivity> mActivity;
		MyHandler(LianliankanActivity activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final LianliankanActivity activity = mActivity.get();
			if(activity == null) {
				return;
			}
			activity.setContentView(R.layout.activity_cut_food);
			switch(msg.what){
				case 0:
					SoundUtil.initSounds(activity.getApplicationContext());
					SoundUtil.playSounds(SoundUtil.WIN, 0, activity.getApplicationContext());
					activity.dialog = new MyDialog(activity, activity.gameView,"胜利！", activity.gameView.getTotalTime() - activity.progress.getProgress());
					activity.dialog.show();
					break;
				case 1:
					SoundUtil.initSounds(activity.getApplicationContext());
					SoundUtil.playSounds(SoundUtil.LOSE, 0, activity.getApplicationContext());
					activity.dialog = new MyDialog(activity, activity.gameView,"失败！", activity.gameView.getTotalTime() - activity.progress.getProgress());
					activity.dialog.show();
			}
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}