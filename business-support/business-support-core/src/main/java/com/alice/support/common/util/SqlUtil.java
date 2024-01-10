package com.alice.support.common.util;

import cn.hutool.core.text.CharSequenceUtil;
import com.alice.support.common.exception.BusinessException;

/**
 * @Description sql操作工具类
 * @DateTime 2023/11/28 17:35
 */
public class SqlUtil {

    private SqlUtil() {
    }

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static final String SQL_PATTERN = "[a-zA-Z0-9_ ,.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (CharSequenceUtil.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new BusinessException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

}