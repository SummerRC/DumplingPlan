package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.widget.AnimationSpirit;
import com.summerrc.dumplingplan.widget.StaticSpirit;
import com.summerrc.dumplingplan.widget.WelcomeSurfaceView;

/**
 * 自定义FrameLayout文字飞入飞出效果
 * 游戏的欢迎画面
 * @author SummerRC
 */
public class WelcomeActivity extends Activity implements OnClickListener {

	private WelcomeSurfaceView welcomeSurfaceView;
	private EditText et_x;
	private EditText et_y;

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** 去掉标题栏和信息栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome);
		initView();
		welcomeSurfaceView = (WelcomeSurfaceView)findViewById(R.id.welcomeSurfaceView);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void initView() {
		findViewById(R.id.iv_stock).setOnClickListener(WelcomeActivity.this);			//原木
		findViewById(R.id.iv_ventilator).setOnClickListener(WelcomeActivity.this);		//抽油烟机
		et_x = (EditText) findViewById(R.id.et_x);
		et_y = (EditText) findViewById(R.id.et_y);
		et_x.addTextChangedListener(new MyTextWatcher(Type.TYPE_X));
		et_y.addTextChangedListener(new MyTextWatcher(Type.TYPE_Y));
		et_x.setVisibility(View.GONE);
		et_y.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_stock:         //原木
				Animation shake01 = AnimationUtils.loadAnimation(this, R.anim.shake);
				findViewById(R.id.iv_stock).startAnimation(shake01);
				break;
			case R.id.iv_ventilator:    //抽油烟机
				Animation shake02 = AnimationUtils.loadAnimation(this, R.anim.shake);
				findViewById(R.id.iv_ventilator).startAnimation(shake02);
				break;
			case R.id.et_x:
				AnimationSpirit.x = Integer.valueOf(et_x.getText().toString());
				break;
			case R.id.et_y:
				AnimationSpirit.y = Integer.valueOf(et_y.getText().toString());
				break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		welcomeSurfaceView.destroyDrawingCache();
	}

	private class MyTextWatcher implements TextWatcher {
		private Type type;
		private MyTextWatcher(Type type) {
			this.type = type;
		}

		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if(et_x.getText().toString().isEmpty() || et_y.getText().toString().isEmpty()) {
				return;
			}
			switch (type) {
				case TYPE_X:
					AnimationSpirit.x = Integer.valueOf(et_x.getText().toString());
					break;
				case TYPE_Y:
					AnimationSpirit.y = -Integer.valueOf(et_y.getText().toString());
					break;
			}
		}
	}

	private enum Type {
		TYPE_X, TYPE_Y
	}
}
