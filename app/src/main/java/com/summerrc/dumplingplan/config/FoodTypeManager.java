package com.summerrc.dumplingplan.config;

import com.summerrc.dumplingplan.R;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author SummerRC on 2015/7/11 0011.
 * 系统中所有的食材种类
 */
public  class FoodTypeManager implements Serializable{
    public enum Food{
        CABBAGE, CUCUMBER, EGGPLANT, PIMIENTO, TOMATO, BEEF, CHICKEN, CRAB, PORK, SHRIMP, BANANA,DRAGON_FRUIT, LEMON, MANGO_STEAN, WATERMELON
        , OIL, SALT, SAUCE
    }
    public static final String UNLOCK = "unlock";
    public static final String LOCK = "lock";

    /**
     * 获取指定食材的信息：资源id
     * @param food 指定食材
     * @return HashMap
     */
    public static HashMap<String, Integer> getHashMap(Food food) {
        HashMap<String, Integer> map = new HashMap<>();
        switch (food) {
            case CABBAGE:
                map.put(UNLOCK, R.mipmap.cabbage);
                map.put(LOCK, R.mipmap.cabbage_lock);
                break;
            case CUCUMBER:
                map.put(UNLOCK, R.mipmap.cucumber);
                map.put(LOCK, R.mipmap.cucumber_lock);
                break;
            case EGGPLANT:
                map.put(UNLOCK, R.mipmap.eggplant);
                map.put(LOCK, R.mipmap.eggplant);               //Lock无
                break;
            case PIMIENTO:
                map.put(UNLOCK, R.mipmap.pimiento);
                map.put(LOCK, R.mipmap.pimiento_lock);
                break;
            case TOMATO:
                map.put(UNLOCK, R.mipmap.tomato);
                map.put(LOCK, R.mipmap.tomato_lock);
                break;
            case BEEF:
                map.put(UNLOCK, R.mipmap.beef);
                map.put(LOCK, R.mipmap.beef_lock);
                break;
            case CHICKEN:
                map.put(UNLOCK, R.mipmap.chicken);
                map.put(LOCK, R.mipmap.chicken_lock);
                break;
            case CRAB:
                map.put(UNLOCK, R.mipmap.crab);
                map.put(LOCK, R.mipmap.crab_lock);
                break;
            case PORK:
                map.put(UNLOCK, R.mipmap.pork);
                map.put(LOCK, R.mipmap.pork_lock);
                break;
            case SHRIMP:
                map.put(UNLOCK, R.mipmap.shrimp);
                map.put(LOCK, R.mipmap.shrimp_lock);
                break;
            case BANANA:
                map.put(UNLOCK, R.mipmap.banana);
                map.put(LOCK, R.mipmap.banana_lock);
                break;
            case DRAGON_FRUIT:
                map.put(UNLOCK, R.mipmap.dragon_fruit);
                map.put(LOCK, R.mipmap.dragon_fruit_lock);
                break;
            case LEMON:
                map.put(UNLOCK, R.mipmap.lemon);
                map.put(LOCK, R.mipmap.lemon_lock);
                break;
            case MANGO_STEAN:
                map.put(UNLOCK, R.mipmap.mangosteen);
                map.put(LOCK, R.mipmap.mangosteen_lock);
                break;
            case WATERMELON:
                map.put(UNLOCK, R.mipmap.watermelon);
                map.put(LOCK, R.mipmap.watermelon_lock);
                break;
            case OIL:
                map.put(UNLOCK, R.mipmap.oil);
                break;
            case SALT:
                map.put(UNLOCK, R.mipmap.salt);
                break;
            case SAUCE:
                map.put(UNLOCK, R.mipmap.sauce);
                break;
        }
        return map;
    }

    /**
     * 获取描述食材信息的资源id
     * @param food 指定食材
     * @return 资源id
     */
    public static Integer getFoodResourceId(Food food) {
        int resourceId = R.mipmap.ic_launcher;
        switch (food) {
            case CABBAGE:
                break;
            case CUCUMBER:
                break;
            case EGGPLANT:
                resourceId = R.mipmap.cabbage_description;
                break;
            case PIMIENTO:
                break;
            case TOMATO:
                break;
            case BEEF:
                resourceId = R.mipmap.beef_description;
                break;
            case CHICKEN:
                break;
            case CRAB:
                break;
            case PORK:
                break;
            case SHRIMP:
                break;
            case BANANA:
                break;
            case DRAGON_FRUIT:
                break;
            case LEMON:
                resourceId = R.mipmap.lemon_description;
                break;
            case MANGO_STEAN:
                break;
            case WATERMELON:
                break;
            case OIL:
                resourceId = R.mipmap.oil_description;
                break;
            case SALT:
                resourceId = R.mipmap.salt_description;
                break;
            case SAUCE:
                resourceId = R.mipmap.sauce_description;
                break;
        }
        return resourceId;
    }
}
