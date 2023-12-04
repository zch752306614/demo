package com.alice.support.common.util;

import com.alice.support.common.consts.ExceptionConstants;
import com.alice.support.common.exception.BusinessException;

/**
 * @Description 统一异常处理工具类
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 17:39
 */
public class BusinessExceptionUtil {

    /**
     * 组合异常信息头,长度固定6位
     */
    private static final String HEAD_FLAG = "SBEXB0";
    /**
     * 组合信息分割符号
     */
    private static final String SPLIT_SIGN = "|";
    /**
     * 组合信息切割符号
     */
    private static final String CUT_SIGN = "\\|";
    /**
     * 条件信息拼装前置符号
     */
    private static final String CONDITION_PRE_SIGN = "[";
    /**
     * 条件信息拼装后置符号
     */
    private static final String CONDITION_AFTER_SIGN = "]";
    /**
     * 条件信息拼装分割符号
     */
    private static final String CONDITION_SPLIT_SIGN = ";";

    public static void dataEx(String bMsg) {
        dataEx(ExceptionConstants.BUSINESS_EX_CODE_DATA_DEFAULT, bMsg);
    }

    public static void dataEx(String bCode, String bMsg) {
        throwEx(ExceptionConstants.BUSINESS_EX_TYPE_DATA, bCode, bMsg);
    }

    public static void logicEx(String bCode, String bMsg) {
        throwEx(ExceptionConstants.BUSINESS_EX_TYPE_LOGIC, bCode, bMsg);
    }

    public static void sysEx(String bMsg) {
        sysEx(ExceptionConstants.BUSINESS_EX_CODE_SYSTEM_DEFAULT, bMsg);
    }

    public static void sysEx(String bCode, String bMsg) {
        throwEx(ExceptionConstants.BUSINESS_EX_TYPE_SYSTEM, bCode, bMsg);
    }

    private static void throwEx(String bType, String bCode, String bMsg) {
        throw new BusinessException(businessExMsgJoin(bType, bCode, bMsg));
    }

    private static String businessExMsgJoin(String bType, String bCode, String bMsg) {
        return HEAD_FLAG + SPLIT_SIGN + bType + SPLIT_SIGN + bCode + SPLIT_SIGN + bMsg;
    }

}
