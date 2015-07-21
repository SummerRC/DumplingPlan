package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.utils.MusicPlayer;
import com.summerrc.dumplingplan.utils.SoundUtil;

import java.util.ArrayList;

/**
 * @author SummerRC on 2015.07.12
 * description : 点击菜篮弹出这个Activity,背景透明位置居中
 */
public class SettingActivity extends Activity implements CompoundButton.OnCheckedChangeListener{

    private ToggleButton tb_music, tb_sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        tb_music = (ToggleButton) findViewById(R.id.tb_music);
        tb_music.setOnCheckedChangeListener(this);
        tb_music.setChecked(GameDataManager.init(getApplicationContext()).isMusicST());
        tb_sound = (ToggleButton) findViewById(R.id.tb_sound);
        tb_sound.setOnCheckedChangeListener(this);
        tb_sound.setChecked(GameDataManager.init(getApplicationContext()).isSoundST());

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundUtil.playSounds(SoundUtil.BACK, 0, getApplicationContext());
                SettingActivity.this.finish();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tb_sound:
                GameDataManager.init(getApplicationContext()).setSoundST(isChecked);
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                break;
            case R.id.tb_music:
                MusicPlayer.setMusicSt(isChecked);
                GameDataManager.init(getApplicationContext()).setMusicST(isChecked);
                SoundUtil.playSounds(SoundUtil.ONE_ONE, 0, getApplicationContext());
                break;
        }

    }


}
