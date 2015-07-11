package com.summerrc.dumplingplan.config;

import java.util.ArrayList;

/**
 * Created by SummerRC on 2015/7/11 0011.
 * 将游戏的数据保存到内存中
 */
public class GameDataManager {
    private static GameDataManager gameDataManager;
    private GameDataManager() {
        foodList = new ArrayList<>();
    }
    public static GameDataManager init() {
        if(gameDataManager == null) {
            gameDataManager = new GameDataManager();
        }
        return gameDataManager;
    }

    private ArrayList<FoodTypeManager.Food> foodList;

    public ArrayList<FoodTypeManager.Food> getFoodList() {
        return foodList;
    }

    public  void setFoodList(FoodTypeManager.Food food) {
        foodList.add(food);
    }
}
