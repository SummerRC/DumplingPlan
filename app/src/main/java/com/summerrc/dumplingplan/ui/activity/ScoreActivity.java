package com.summerrc.dumplingplan.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.config.GameDataManager;
import com.summerrc.dumplingplan.config.ScoreResourceManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author SummerRC on 2015.07.12
 * description : 品——评分界面
 */
public class ScoreActivity extends Activity {
    private Bitmap bitmap_background_score;
    private GameDataManager gameDataManager;    //游戏数据管理类的实例
    private ImageView iv_time;                  //时间
    private RelativeLayout rl_seasoning;        //调料
    private ImageView iv_stuffing;              //馅多少
    private Handler handler;
    private int score_time;                     //煮的时间得分    多少 11  正好 22
    private int score_seasoning;                //调料得分  5  7   9
    private int score_stuffing;                 //馅的得分    3   6   4
    private int score;                          //总得分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** 去掉标题栏和信息栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);
        initView();
        initData();

        handler = new Handler();
        /** 显示煮的时间 */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_time.setVisibility(View.VISIBLE);
            }
        }, 1000);
        /** 显示调料 */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_seasoning.setVisibility(View.VISIBLE);
            }
        }, 3000);
        /** 显示馅的多少 */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_stuffing.setVisibility(View.VISIBLE);
            }
        }, 5000);
        /** 显示得分 */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.view).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_score).setVisibility(View.VISIBLE);
            }
        }, 9000);
    }

    private void initView() {
        iv_time = (ImageView) findViewById(R.id.iv_time);       //煮的时间
        rl_seasoning = (RelativeLayout) findViewById(R.id.rl_seasoning);        //调料种类多少
        iv_stuffing = (ImageView) findViewById(R.id.iv_stuffing);
    }

    private void initData() {
        gameDataManager = GameDataManager.init(this);
        /** 煮的时间长短 */
        int count = gameDataManager.getCount();
        if(count < 6) {
            score_time = 11;
            iv_time.setBackgroundResource(R.mipmap.time_less);
        } else if(count==6 || count==7) {
            score_time = 22;
            iv_time.setBackgroundResource(R.mipmap.time_normal);
        } else {
            score_time = 11;
            iv_time.setBackgroundResource(R.mipmap.time_more);
        }
        /** 调料 */
        setSeasoning(FoodTypeManager.Food.SALT);
        setSeasoning(FoodTypeManager.Food.SAUCE);
        setSeasoning(FoodTypeManager.Food.OIL);
        /** 饺子馅 */
        int n = 0;
        for (int i=1; i<7; i++) {
            Integer num = gameDataManager.getStuffNum(i);
            n += num;
            switch (num) {
                case 1:
                    score_stuffing += 3;
                    break;
                case 2:
                    score_stuffing += 6;
                    break;
                case 3:
                    score_stuffing += 4;
                    break;
            }
        }
        if(n > 15) {
            iv_stuffing.setBackgroundResource(R.mipmap.pie_more_score);
        } else if(n < 10) {
            iv_stuffing.setBackgroundResource(R.mipmap.pie_less_score);
        } else {
            iv_stuffing.setBackgroundResource(R.mipmap.pie_normal_score);
        }
        /** 总得分 */
        score = score_seasoning + score_time + score_stuffing;
        findViewById(R.id.iv_score_one).setBackgroundResource(ScoreResourceManager.getScoreResource(score / 10));
        findViewById(R.id.iv_score_two).setBackgroundResource(ScoreResourceManager.getScoreResource(score % 10));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(bitmap_background_score==null || bitmap_background_score.isRecycled()) {
            bitmap_background_score = BitmapFactory.decodeResource(getResources(), R.mipmap.background_score);
        }
        findViewById(R.id.rootView).setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap_background_score));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bitmap_background_score != null && !bitmap_background_score.isRecycled()) {
            bitmap_background_score.isRecycled();
            bitmap_background_score = null;
            System.gc();
        }
    }

    /**
     * 设置调料的背景图片
     * @param food 调料种类
     */
    private void setSeasoning(FoodTypeManager.Food food) {
        ImageView imageView;
        int count = gameDataManager.getSeasoningNumberMap(food);
        switch (food) {
            case SALT:
                imageView = (ImageView)findViewById(R.id.iv_salt);
                switch (count) {
                    case 0:
                        imageView.setBackgroundResource(R.mipmap.salt_no);
                        score_seasoning += 0;
                        break;
                    case 1:
                        imageView.setBackgroundResource(R.mipmap.salt_less);
                        score_seasoning += 5;
                        break;
                    case 2:
                        imageView.setBackgroundResource(R.mipmap.salt_normal);
                        score_seasoning += 9;
                        break;
                    case 3:
                        imageView.setBackgroundResource(R.mipmap.salt_more);
                        score_seasoning += 7;
                        break;
                }
                break;
            case OIL:
                imageView = (ImageView)findViewById(R.id.iv_oil);
                switch (count) {
                    case 0:
                        imageView.setBackgroundResource(R.mipmap.oil_no);
                        break;
                    case 1:
                        imageView.setBackgroundResource(R.mipmap.oil_less);
                        break;
                    case 2:
                        imageView.setBackgroundResource(R.mipmap.oil_normal);
                        break;
                    case 3:
                        imageView.setBackgroundResource(R.mipmap.oil_more);
                        break;
                }
                break;
            case SAUCE:
                imageView = (ImageView)findViewById(R.id.iv_sauce);
                switch (count) {
                    case 0:
                        imageView.setBackgroundResource(R.mipmap.sauce_no);
                        break;
                    case 1:
                        imageView.setBackgroundResource(R.mipmap.sauce_less);
                        break;
                    case 2:
                        imageView.setBackgroundResource(R.mipmap.sauce_normal);
                        break;
                    case 3:
                        imageView.setBackgroundResource(R.mipmap.sauce_more);
                        break;
                }
                break;
        }
    }

}
