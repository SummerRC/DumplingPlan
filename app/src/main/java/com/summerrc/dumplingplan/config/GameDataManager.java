package com.summerrc.dumplingplan.config;

import android.content.Context;

import com.summerrc.dumplingplan.entity.DumplingTypeEntity;
import com.summerrc.dumplingplan.utils.SharedPreferenceUtils;
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
    private HashMap<Integer, Integer> stuffNumMap;                      //馅的多少
    private ArrayList<DumplingTypeEntity> dumplingTypeEntities;         //饺子集合
    private int count;                                                  //饺子煮的时间
    private int currentLock = 0;                                        //当前关卡
    private int unLock = 1;                                             //以解锁的关卡
    private Context context;
    public static final int ONE = 60;
    public static final int TWO = 67;
    public static final int FOUR = 74;
    public static final int FIVE = 81;
    public static final int SEVEN = 87;
    public static final int EIGHT = 93;
    private boolean musicST = true;                                     //音乐开关
    private boolean soundST = true;                                     //音效开关

    /**
     * 私有化的构造函数
     */
    private GameDataManager(Context context) {
        this.context = context;
        foodList = new ArrayList<>();
        seasoningList = new ArrayList<>();
        seasoningNumberMap = new HashMap<>();
        seasoningNumberMap.put(FoodTypeManager.Food.OIL, 0);
        seasoningNumberMap.put(FoodTypeManager.Food.SALT, 0);
        seasoningNumberMap.put(FoodTypeManager.Food.SAUCE, 0);
        stuffNumMap = new HashMap<>();
        stuffNumMap.put(1, 0);
        stuffNumMap.put(2, 0);
        stuffNumMap.put(3, 0);
        stuffNumMap.put(4, 0);
        stuffNumMap.put(5, 0);
        stuffNumMap.put(6, 0);
        dumplingTypeEntities = new ArrayList<>();
        unLock = SharedPreferenceUtils.getIntDate(context, IntentConstant.UN_LOCK, 1);
        soundST = SharedPreferenceUtils.getBooleanDate(context, IntentConstant.SOUND, true);
        musicST = SharedPreferenceUtils.getBooleanDate(context, IntentConstant.MUSIC, true);
    }


    /**
     * 单例模式，保证游戏数据内存中只有一份
     * @return 自身对象，单利模式
     */
    public static GameDataManager init(Context context) {
        if(gameDataManager == null) {
            gameDataManager = new GameDataManager(context);
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
        clear();
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
        int vegetable = 0, fruit = 0, meat = 0, count = 0;
        for(int i=0; i<foodList.size(); i++) {
            if(StuffTypeManager.isVegetable(foodList.get(i))) {
                vegetable = 1;
            }
            if(StuffTypeManager.isFruit(foodList.get(i))) {
                fruit = 10;
            }
            if(StuffTypeManager.isMeat(foodList.get(i))) {
                meat = 100;
            }
        }
        count = vegetable + fruit + meat;
        switch (count) {
            case 1:
                stuffType = StuffTypeManager.StuffType.VEGETABLE;
                break;
            case 10:
                stuffType = StuffTypeManager.StuffType.FRUIT;
                break;
            case 100:
                stuffType = StuffTypeManager.StuffType.MEAT;
                break;
            case 11:
                stuffType = StuffTypeManager.StuffType.VEGETABLE_FRUIT;
                break;
            case 101:
                stuffType = StuffTypeManager.StuffType.VEGETABLE_MEAT;
                break;
            case 110:
                stuffType = StuffTypeManager.StuffType.FRUIT_MEAT;
                break;
            case 111:
                stuffType = StuffTypeManager.StuffType.VEGETABLE_FRUIT_MEAT;
                break;
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

    /**
     * 设置馅的多少
     * @param key       第几个馅
     * @param value     值
     */
    public void setStuffNum(int key, int value) {
        stuffNumMap.remove(key);
        stuffNumMap.put(key, value);
    }

    /**
     * 获取馅的多少
     * @param key       第几个馅
     */
    public Integer getStuffNum(int key) {
        return stuffNumMap.get(key);
    }

    /**
     *  添加馅
     * @param key       第几个馅
     */
    public void addStuffNum(int key) {
        int value = stuffNumMap.get(key)+1;
        stuffNumMap.remove(key);
        stuffNumMap.put(key, value);
    }

    /**
     * @return  饺子集合：大 小  正好
     */
    public ArrayList<DumplingTypeEntity> getDumplingTypeEntities() {
        return dumplingTypeEntities;
    }

    /**
     * 设置饺子集合
     */
    public void setDumplingTypeEntities() {
        dumplingTypeEntities.clear();
        for(int i=1; i<7; i++) {
            DumplingTypeEntity entity = new DumplingTypeEntity();
            switch (stuffNumMap.get(i)) {
                case 1:
                    entity.setType(DumplingTypeEntity.Type.SMALL);
                    break;
                case 2:
                    entity.setType(DumplingTypeEntity.Type.NOMAL);
                    break;
                case 3:
                    entity.setType(DumplingTypeEntity.Type.LARGE);
                    break;
                default:
                    entity.setType(DumplingTypeEntity.Type.NOMAL);
            }
            dumplingTypeEntities.add(entity);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentLock() {
        return currentLock;
    }

    public void setCurrentLock(int currentLock) {
        this.currentLock = currentLock;
    }

    public int getUnLock() {
        return unLock;
    }

    public void setUnLock(int unLock) {
        this.unLock = unLock;
        SharedPreferenceUtils.saveIntDate(context, IntentConstant.UN_LOCK, unLock);
    }

    public void clear() {
        foodList = null;
        seasoningList = null;
        seasoningNumberMap = null;
        stuffNumMap = null;
        dumplingTypeEntities = null;
        unLock = 1;
        musicST = true;
        soundST = true;
        gameDataManager = null;
    }

    public boolean isMusicST() {
        return musicST;
    }

    public void setMusicST(boolean musicST) {
        this.musicST = musicST;
        SharedPreferenceUtils.saveBooleanDate(context, IntentConstant.MUSIC, musicST);
    }

    public boolean isSoundST() {
        return soundST;
    }

    public void setSoundST(boolean soundST) {
        this.soundST = soundST;
        SharedPreferenceUtils.saveBooleanDate(context, IntentConstant.SOUND, soundST);
    }
}
