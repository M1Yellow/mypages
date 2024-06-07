package cn.m1yellow.mypages.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class ObjectUtil {

    /**
     * 获取对象字符串
     *
     * @param obj 操作对象
     * @return 对象字符串
     */
    public static String getString(Object obj) {
        if (null == obj) return null;
        return obj.toString().trim();
    }

    /**
     * 对象中的 String 字段去两边空格
     *
     * @param obj 操作对象
     */
    public static void stringFiledTrim(Object obj) {
        if (null == obj) return;
        try {
            //Field[] fields = ReflectUtil.getFields(obj.getClass()); // 包括父类字段
            // TODO 实体类通常是单个类，不用取父类字段
            Field[] fields = getFieldsOfObj(obj);
            for (Field field : fields) {
                if (field.getType().equals(String.class)) {
                    field.setAccessible(true);
                    Object objVal = field.get(obj);
                    if (null != objVal) {
                        //String newVal = String.valueOf(objVal).trim();
                        field.set(obj, String.valueOf(objVal).trim());
                    }
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
            log.error(">>>> stringFiledTrim error: {}", e.getMessage());
        }
    }


    /**
     * 通过反射获取对象本身内部所有的字段，不包括父类继承的
     *
     * @param obj 操作对象
     * @return 字段列表
     */
    public static Field[] getFieldsOfObj(Object obj) {
        if (null == obj) return null;
        return obj.getClass().getDeclaredFields();
    }

}
