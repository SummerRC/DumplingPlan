package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.utils.UIHelper;

/**
 * @author SummerRC on 2015.07.12
 * description : 选择食材界面
 */
public class OpenActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open);
        findViewById(R.id.bt_one).setOnClickListener(this);
        findViewById(R.id.bt_two).setOnClickListener(this);
        findViewById(R.id.bt_three).setOnClickListener(this);
        findViewById(R.id.bt_four).setOnClickListener(this);
        findViewById(R.id.bt_five).setOnClickListener(this);
        findViewById(R.id.bt_six).setOnClickListener(this);
        findViewById(R.id.bt_seven).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_one:
                UIHelper.openWelcomeActivity(this);
                break;
            case R.id.bt_two:
                UIHelper.openSelectFoodActivity(this);
                break;
            case R.id.bt_three:
                UIHelper.openSelectSeasoningActivity(this);
                break;
            case R.id.bt_four:
                UIHelper.openDoughActivity(this);
                break;
            case R.id.bt_five:
                UIHelper.openStuffingActivity(this);
                break;
            case R.id.bt_six:
                break;
            case R.id.bt_seven:
                break;
        }
    }
}
