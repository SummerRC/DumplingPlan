package com.summerrc.dumplingplan.config;

import com.summerrc.dumplingplan.R;

/**
 * @author : SummerRC on 2015/7/14 0014
 * @version :  V1.0 <当前版本>
 * description : 根据食材不同形成不同的馅
 */
public class StuffTypeManager {
    /** 馅类型 */
    public enum StuffType {
        VEGETABLE, FRUIT, MEAT, VEGETABLE_FRUIT, VEGETABLE_MEAT, FRUIT_MEAT, VEGETABLE_FRUIT_MEAT
    }

    /**
     * @param food 食材
     * @return   true : 是蔬菜
     */
    public static boolean isVegetable(FoodTypeManager.Food food) {
        return(food== FoodTypeManager.Food.CABBAGE || food== FoodTypeManager.Food.CUCUMBER || food== FoodTypeManager.Food.EGGPLANT || food== FoodTypeManager.Food.PIMIENTO || food== FoodTypeManager.Food.TOMATO);
    }

    /**
     * @param food  食材
     * @return   true  : 是肉类
     */
    public static boolean isMeat(FoodTypeManager.Food food) {
        return(food== FoodTypeManager.Food.BEEF || food== FoodTypeManager.Food.CHICKEN || food== FoodTypeManager.Food.CRAB || food== FoodTypeManager.Food.PORK || food== FoodTypeManager.Food.SHRIMP);
    }

    /**
     * @param food  食材
     * @return   true  : 是水果
     */
    public static boolean isFruit(FoodTypeManager.Food food) {
        return !(isVegetable(food) || isMeat(food));
    }

    /**
     * 获取指定类型馅的资源id
     * @param stuffType   馅类型
     * @return            指定类型馅的资源id
     */
    public static int getStuffResourceId(StuffType stuffType) {
        switch (stuffType) {
            case VEGETABLE:
                return R.mipmap.vegetable;
            case FRUIT:
                return R.mipmap.fruit;
            case MEAT:
                return R.mipmap.meat;
            case VEGETABLE_FRUIT:
                return R.mipmap.fruit_vegetable;
            case VEGETABLE_MEAT:
                return R.mipmap.meat_vegetable;
            case FRUIT_MEAT:
                return R.mipmap.meat_fruit;
            case VEGETABLE_FRUIT_MEAT:
                return R.mipmap.meat_fruit_vegetable;
            default:
                return R.mipmap.vegetable;
        }
    }
}
