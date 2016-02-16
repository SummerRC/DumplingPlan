package com.summerrc.dumplingplan.config;

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
        return(food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE);
    }

    /**
     * @param food  食材
     * @return   true  : 是肉类
     */
    public static boolean isMeat(FoodTypeManager.Food food) {
        return(food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE || food== FoodTypeManager.Food.O_ONE);
    }

    /**
     * @param food  食材
     * @return   true  : 是水果
     */
    public static boolean isFruit(FoodTypeManager.Food food) {
        return false;
    }

    /**
     * 获取指定类型馅的资源id
     * @param stuffType   馅类型
     * @return            指定类型馅的资源id
     */
    public static int getStuffResourceId(StuffType stuffType) {
        return 1;
    }
}
