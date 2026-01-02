package com.bird_forum.util;

public class BeanUtils {

    /**
     * 判断对象是否不为空
     *
     * @param obj 对象
     * @return true:不为空 false:为空
     */
    public static Boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return true:为空 false:不为空
     */
    public static Boolean isNull(Object obj) {
        return obj == null;
    }
}
