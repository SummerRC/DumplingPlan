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
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, O_ONE, O_TWO, O_THREE, O_FOUR, O_FIVE, DEFAULT
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
        /*HashMap<String, Integer> map = new HashMap<>();
        switch (food) {
            case ONE:
                map.put(UNLOCK, R.mipmap.cabbage);
                map.put(LOCK, R.mipmap.cabbage_lock);
                break;
            case TWO:
                map.put(UNLOCK, R.mipmap.cucumber);
                map.put(LOCK, R.mipmap.cucumber_lock);
                break;
            case THREE:
                map.put(UNLOCK, R.mipmap.eggplant);
                map.put(LOCK, R.mipmap.eggplant);               //Lock无
                break;
            case FOUR:
                map.put(UNLOCK, R.mipmap.pimiento);
                map.put(LOCK, R.mipmap.pimiento_lock);
                break;
            case FIVE:
                map.put(UNLOCK, R.mipmap.tomato);
                map.put(LOCK, R.mipmap.tomato_lock);
                break;
            case SIX:
                map.put(UNLOCK, R.mipmap.beef);
                map.put(LOCK, R.mipmap.beef_lock);
                break;
            case SEVEN:
                map.put(UNLOCK, R.mipmap.chicken);
                map.put(LOCK, R.mipmap.chicken_lock);
                break;
            case EIGHT:
                map.put(UNLOCK, R.mipmap.crab);
                map.put(LOCK, R.mipmap.crab_lock);
                break;
            case NINE:
                map.put(UNLOCK, R.mipmap.pork);
                map.put(LOCK, R.mipmap.pork_lock);
                break;
            case TEN:
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
        }*/
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
            case ONE:
                resourceId = R.mipmap.soho_food_one_desc;
                break;
            case TWO:
                resourceId = R.mipmap.soho_food_two_desc;
                break;
            case THREE:
                resourceId = R.mipmap.soho_food_three_desc;
                break;
            case FOUR:
                resourceId = R.mipmap.soho_food_four_desc;
                break;
            case FIVE:
                resourceId = R.mipmap.soho_food_five_desc;
                break;
            case SIX:
                resourceId = R.mipmap.soho_food_six_desc;
                break;
            case SEVEN:
                resourceId = R.mipmap.soho_food_seven_desc;
                break;
            case EIGHT:
                resourceId = R.mipmap.soho_food_eight_desc;
                break;
            case NINE:
                resourceId = R.mipmap.soho_food_nine_desc;
                break;
            case TEN:
                resourceId = R.mipmap.soho_food_ten_desc;
                break;
            case O_ONE:
                resourceId = R.mipmap.soho_select_seasoning_one_desc;
                break;
            case O_TWO:
                resourceId = R.mipmap.soho_select_seasoning_two_desc;
                break;
            case O_THREE:
                resourceId = R.mipmap.soho_select_seasoning_three_desc;
                break;
            case O_FOUR:
                resourceId = R.mipmap.soho_select_seasoning_four_desc;
                break;
            case O_FIVE:
                resourceId = R.mipmap.soho_select_seasoning_five_desc;
                break;
            case DEFAULT:
                break;
        }
        return resourceId;
    }
}
