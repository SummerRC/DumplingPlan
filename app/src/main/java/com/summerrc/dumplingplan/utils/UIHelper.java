package com.summerrc.dumplingplan.utils;

import android.app.Activity;
import android.content.Intent;

import com.summerrc.dumplingplan.config.IntentConstant;
import com.summerrc.dumplingplan.config.FoodTypeManager;
import com.summerrc.dumplingplan.ui.activity.BasketActivity;
import com.summerrc.dumplingplan.ui.activity.CutActivity;
import com.summerrc.dumplingplan.ui.activity.DoughActivity;
import com.summerrc.dumplingplan.ui.activity.FoodDescriptionActivity;
import com.summerrc.dumplingplan.ui.activity.AddStuffingActivity;
import com.summerrc.dumplingplan.ui.activity.PutActivity;
import com.summerrc.dumplingplan.ui.activity.RubActivity;
import com.summerrc.dumplingplan.ui.activity.SelectFoodActivity;
import com.summerrc.dumplingplan.ui.activity.SelectSeasoningActivity;
import com.summerrc.dumplingplan.ui.activity.ShakeActivity;
import com.summerrc.dumplingplan.ui.activity.SkinActivity;
import com.summerrc.dumplingplan.ui.activity.StuffingActivity;
import com.summerrc.dumplingplan.ui.activity.WelcomeActivity;

/**
 * Created by SummerRC on 2015/7/3 0003.
 * 统一管理Activity的跳转
 */
public class UIHelper {
    /**
     * 跳转到选择欢迎界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openWelcomeActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, WelcomeActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }


    /**
     * 跳转到选择食材界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openSelectFoodActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, SelectFoodActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到选择调料界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openSelectSeasoningActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, SelectSeasoningActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到制作馅界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openStuffingActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, StuffingActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到和面界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openDoughActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, DoughActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到和面界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openRubActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, RubActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到切段界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openCutActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, CutActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到擀面界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openSkinActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, SkinActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到包饺子界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openAddStuffingActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, AddStuffingActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到下饺子界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openPutActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, PutActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 跳转到筛水界面
     * @param currentActivity 当前运行的Activity的实例
     */
    public static void openShakeActivity(Activity currentActivity) {
        Intent intent = new Intent(currentActivity, ShakeActivity.class);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /**
     * 启动食材详情界面
     * @param currentActivity 当前运行的Activity的实例
     * @param activity_type     用于标识由哪个Activity来启动
     * @param requestCode    回调的请求类型，也是起标示符作用
     * @param food  被点中的食材或者调料
     */
    public static void openFoodDescriptionActivity(Activity currentActivity, String activity_type, int requestCode, FoodTypeManager.Food food) {
        Intent intent = new Intent(currentActivity, FoodDescriptionActivity.class);
        intent.putExtra(IntentConstant.SELECTED_FOOD, food);
        intent.putExtra(IntentConstant.ACTIVITY_TYPE, activity_type);
        currentActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动菜篮详情界面
     * @param currentActivity 当前运行的Activity的实例
     * @param activity_type   用于标识由哪个Activity来启动
     * @param requestCode     用于标识由哪个Activity来启动
     */
    public static void openBasketActivity(Activity currentActivity, String activity_type, int requestCode) {
        Intent intent = new Intent(currentActivity, BasketActivity.class);
        intent.putExtra(IntentConstant.ACTIVITY_TYPE, activity_type);
        currentActivity.startActivityForResult(intent, requestCode);
    }
}
