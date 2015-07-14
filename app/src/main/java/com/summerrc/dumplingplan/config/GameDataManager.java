package com.summerrc.dumplingplan.config;

import java.util.ArrayList;

/**
 * @author SummerRC on 2015/7/11 0011.
 * description : 管理游戏数据的类，将游戏的数据保存到内存中  单例模式
 */
public class GameDataManager {
    private static GameDataManager gameDataManager;            //单例模式
    private ArrayList<FoodTypeManager.Food> foodList;          //食材集合
    private ArrayList<FoodTypeManager.Food> seasoningList;     //调料集合

    /**
     * 私有化的构造函数
     */
    private GameDataManager() {
        foodList = new ArrayList<>();
        seasoningList = new ArrayList<>();
    }

    /**
     * 单例模式，保证游戏数据内存中只有一份
     * @return 自身对象，单利模式
     */
    public static GameDataManager init() {
        if(gameDataManager == null) {
            gameDataManager = new GameDataManager();
        }
        return gameDataManager;
    }

    /**
     * @return 食材集合
     */
    public ArrayList<FoodTypeManager.Food> getFoodList() {
        return foodList;
    }

    /**
     * 存储用户选取的食材
     * @param food 食材
     */
    public  void setFoodList(FoodTypeManager.Food food) {
        foodList.add(food);
    }

    /**
     * @return 调料集合
     */
    public ArrayList<FoodTypeManager.Food> getSeasoningList() {
        return seasoningList;
    }

    /**
     * 存储用户选取的调料
     * @param food 调料
     */
    public  void setSeasoningList(FoodTypeManager.Food food) {
        seasoningList.add(food);
    }

    /**
     * 重新开始游戏或者游戏结束后记得清空内存中的数据
     */
    public void clean() {
         gameDataManager = null;
    }
}
