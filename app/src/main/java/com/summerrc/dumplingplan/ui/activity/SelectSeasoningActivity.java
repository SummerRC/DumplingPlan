package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;

/**
 * @author SummerRC on 2015.07.12
 * description : 选择调料界面
 */
public class SelectSeasoningActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seasoning);
    }

    @Override
    public void onClick(View v) {

    }
}
