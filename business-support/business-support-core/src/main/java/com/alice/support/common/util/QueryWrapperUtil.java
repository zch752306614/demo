package com.alice.support.common.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description querywrapper工具类
 * @DateTime 2023/11/28 20:38
 */
public class QueryWrapperUtil {

    private QueryWrapperUtil() {
    }

    public static <T> QueryWrapper<T> initParams(T obj) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        List<Field> fieldList = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        // 获取父类全部字段
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        for (Field field : fieldList) {
            // 获取id
            TableId tableId = field.getAnnotation(TableId.class);
            // 获取普通字段
            TableField tableField = field.getAnnotation(TableField.class);
            // 设置可编辑
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != tableId && null != value) {
                if (value instanceof String) {
                    if (CharSequenceUtil.isNotEmpty(String.valueOf(value))) {
                        queryWrapper.eq(tableId.value(), value);
                    }
                } else {
                    queryWrapper.eq(tableId.value(), value);
                }
            } else if (null != tableField && null != value) {
                if (value instanceof String) {
                    if (CharSequenceUtil.isNotEmpty(String.valueOf(value))) {
                        queryWrapper.eq(tableField.value(), value);
                    }
                } else {
                    queryWrapper.eq(tableField.value(), value);
                }
            }
        }
        return queryWrapper;
    }

}
