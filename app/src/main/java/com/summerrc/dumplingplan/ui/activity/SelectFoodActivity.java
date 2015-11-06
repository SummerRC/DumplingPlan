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
 *         description : 选择食材界面
 */
public class SelectFoodActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_food);
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
        findViewById(R.id.iv_six).setOnClickListener(this);
        findViewById(R.id.iv_seven).setOnClickListener(this);
        findViewById(R.id.iv_eight).setOnClickListener(this);
        findViewById(R.id.iv_nine).setOnClickListener(this);
        findViewById(R.id.iv_ten).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                UIHelper.openSelectSeasoningActivity(this);
                break;
            case R.id.iv_two:
                break;
            case R.id.iv_three:
                break;
            case R.id.iv_four:
                break;
            case R.id.iv_five:
                break;
            case R.id.iv_six:
                break;
            case R.id.iv_seven:
                break;
            case R.id.iv_eight:
                break;
            case R.id.iv_nine:
                break;
            case R.id.iv_ten:
                break;
        }
    }
}
