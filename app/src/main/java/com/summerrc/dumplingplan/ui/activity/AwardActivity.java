package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.MMApplication;
import com.summerrc.dumplingplan.utils.UIHelper;

public class AwardActivity extends Activity implements View.OnClickListener{

    private ImageView iv_jiangbei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.soho_activity_award);
        setBackground();
        findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MMApplication.isSelected = !MMApplication.isSelected;
                setVisible();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openWelcomeActivity(AwardActivity.this);
            }
        });
        iv_jiangbei = (ImageView) findViewById(R.id.iv_jiangbei);
        iv_jiangbei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_jiangbei.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.iv_one).setOnClickListener(this);
        findViewById(R.id.iv_two).setOnClickListener(this);
        findViewById(R.id.iv_three).setOnClickListener(this);
        findViewById(R.id.iv_four).setOnClickListener(this);
        findViewById(R.id.iv_five).setOnClickListener(this);
        findViewById(R.id.iv_six).setOnClickListener(this);
        findViewById(R.id.iv_seven).setOnClickListener(this);
    }

    private void setVisible() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setVisibility(View.VISIBLE);
            findViewById(R.id.iv_yingyue).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.iv_yingxiao).setVisibility(View.GONE);
            findViewById(R.id.iv_yingyue).setVisibility(View.GONE);
            findViewById(R.id.iv_yingxiao).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinxiao = !MMApplication.isSelected_yinxiao;
                    if(MMApplication.isSelected_yinxiao) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });
            findViewById(R.id.iv_yingyue).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMApplication.isSelected_yinyue = !MMApplication.isSelected_yinyue;
                    if(MMApplication.isSelected_yinyue) {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
                    } else {
                        findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
                        findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
                    }
                }
            });

        }
    }

    private void setBackground() {
        if(MMApplication.isSelected) {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_selected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_selected);
        } else {
            findViewById(R.id.iv_yingxiao).setBackgroundResource(R.mipmap.soho_select_seasoning_yinxiao_unselected);
            findViewById(R.id.iv_yingyue).setBackgroundResource(R.mipmap.soho_select_seasoning_yinuser_unselected);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_one:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_one_desc);
                break;
            case R.id.iv_two:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_two_desc);
                break;
            case R.id.iv_three:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_three_desc);
                break;
            case R.id.iv_four:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_four_desc);
                break;
            case R.id.iv_five:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_five_desc);
                break;
            case R.id.iv_six:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_six_desc);
                break;
            case R.id.iv_seven:
                iv_jiangbei.setBackgroundResource(R.mipmap.soho_award_seven_desc);
                break;

        }
        iv_jiangbei.setVisibility(View.VISIBLE);
    }
}
