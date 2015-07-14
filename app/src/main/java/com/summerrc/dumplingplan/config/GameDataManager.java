package com.summerrc.dumplingplan.config;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author SummerRC on 2015/7/11 0011.
 * description : 管理游戏数据的类，将游戏的数据保存到内存中  单例模式
 */
public class GameDataManager {
    private static GameDataManager gameDataManager;                     //单例模式
    private ArrayList<FoodTypeManager.Food> foodList;                   //食材集合
    private ArrayList<FoodTypeManager.Food> seasoningList;              //调料集合
    private HashMap<FoodTypeManager.Food, Integer> seasoningNumberMap;  //调料份数
    private StuffTypeManager.StuffType stuffType;                       //馅种类

    /**
     * 私有化的构造函数
     */
    private GameDataManager() {
        foodList = new ArrayList<>();
        seasoningList = new ArrayList<>();
        seasoningNumberMap = new HashMap<>();
        seasoningNumberMap.put(FoodTypeManager.Food.OIL, 0);
        seasoningNumberMap.put(FoodTypeManager.Food.SALT, 0);
        seasoningNumberMap.put(FoodTypeManager.Food.SAUCE, 0);
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

    /**
     * @return 获取馅类型
     */
    public StuffTypeManager.StuffType getStuffType() {
        return stuffType;
    }

    /**
     * 设置馅的类型
     */
    public void setStuffType() {
        int vegetable = 1, fruit = 10, meat = 100, count = 0;
        for(int i=0; i<foodList.size(); i++) {
            if(StuffTypeManager.isVegetable(foodList.get(i))) {
                count += vegetable;
            }
            if(StuffTypeManager.isFruit(foodList.get(i))) {
                count += fruit;
            }
            if(StuffTypeManager.isMeat(foodList.get(i))) {
                count += meat;
            }
        }
        if(count<10) {
            stuffType = StuffTypeManager.StuffType.VEGETABLE;
        } else if(count==10 || count==20 || count==30) {
            stuffType = StuffTypeManager.StuffType.FRUIT;
        } else if(count==100 || count==200 ||count==300) {
            stuffType = StuffTypeManager.StuffType.MEAT;
        } else if(count==12 || count==21) {      //去除了水果的情况
            stuffType = StuffTypeManager.StuffType.VEGETABLE_FRUIT;
        } else if(count==102 || count==201){
            stuffType = StuffTypeManager.StuffType.VEGETABLE_MEAT;
        } else if(count==120 || count==210){
            stuffType = StuffTypeManager.StuffType.FRUIT_MEAT;
        } else if(count==111){
            stuffType = StuffTypeManager.StuffType.VEGETABLE_FRUIT_MEAT;
        }
    }

    /**
     * 设置对应调料的份数
     * @param food  调料
     * @param x  份数
     */
    public void setSeasoningNumberMap(FoodTypeManager.Food food, int x) {
        seasoningNumberMap.remove(food);
        seasoningNumberMap.put(food, x);
    }

    /**
     * 获取对应调料的份数
     * @param food  调料
     */
    public int getSeasoningNumberMap(FoodTypeManager.Food food) {
        return seasoningNumberMap.get(food);
    }
}
