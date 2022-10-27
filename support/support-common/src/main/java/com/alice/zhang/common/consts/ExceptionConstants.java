package com.alice.zhang.common.consts;

/**
 * @Description 系统异常类型编码
 * @Author ZhangChenhuang
 * @DateTime 2022/9/21 0021 16:50
 */
public class ExceptionConstants {

    /**
     * 业务异常类型
     */
    public static final String BUSINESS_EX_TYPE_QUERY = "业务查询异常";
    public static final String BUSINESS_EX_TYPE_DATA = "业务数据异常";
    public static final String BUSINESS_EX_TYPE_LOGIC = "业务逻辑异常";
    public static final String BUSINESS_EX_TYPE_SYSTEM = "业务系统异常";

    public static final String DEFAULT_ERR_MESSAGE = "系统异常，请与系统管理员联系";
    public static final String DEFAULT_WARM_MESSAGE = "当前操作存在风险，请确认是否继续？";
    public static final String EXCEPTION_NULL_POINTER = "空指针异常";
    public static final String EXCEPTION_MISS_PARAM_POINTER = "缺少必填参数";
    public static final String EXCEPTION_ILLEGAL_PARAM_POINTER = "参数格式不合法";

    /**
     * 业务查询异常编码
     */
    public static final String BUSINESS_EX_CODE_QUERY_DEFAULT = "BEQ00000";
    public static final String BUSINESS_EX_CODE_QUERY_NONE = "BEQ00001";
    public static final String BUSINESS_EX_CODE_QUERY_OVER = "BEQ00002";
    public static final String BUSINESS_EX_CODE_QUERY_NOT_ONE = "BEQ00003";

    /**
     * 业务数据异常编码
     */
    public static final String BUSINESS_EX_CODE_DATA_DEFAULT = "BED00000";
    public static final String BUSINESS_EX_CODE_DATA_NULL = "BED00001";

    /**
     * 业务逻辑异常编码
     */
    public static final String BUSINESS_EX_CODE_LOGIC_DEFAULT = "BEL00000";

    /**
     * 业务系统异常编码
     */
    public static final String BUSINESS_EX_CODE_SYSTEM_DEFAULT = "BES00000";

}
