package com.summerrc.dumplingplan.entity;

/**
 * @author : SummerRC on 2015/7/16 0016
 * @version :  V1.0 <当前版本>
 *          description : <在此填写描述信息>
 */
public class DumplingTypeEntity {
    public enum Type {
        SMALL, NOMAL, LARGE
    }

    private Type type;
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
