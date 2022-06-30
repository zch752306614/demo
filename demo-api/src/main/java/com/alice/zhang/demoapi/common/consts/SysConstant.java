package com.alice.zhang.demoapi.common.consts;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * @Description 框架参数
 * @ClassName SysConstant
 * @DateTime 2022/6/30 17:41
 * @Author ZhangChenhuang
 */
public class SysConstant {

    private SysConstant() {
    }

    /**
     * 链路ID
     */
    public static final String TRACE = "trace";
    public static final String TRACE_UUID = "trace_uuid";

    /**
     * 回退参数需覆盖字段
     */
    public static final Set<String> CHANGESTR = ImmutableSet.of("ac08InsertDetailDTOList", "ac95InsertDetailDTOList", "ac96InsertDetailDTOList");

    /**
     * 无需覆盖字段
     */
    public static final Set<String> NOCHANGESTR = ImmutableSet.of("aae859", "aae860", "aaa027", "aab301", "aab359");

    /**
     * 无需覆盖字段-厦门
     */
    public static final Set<String> NOCHANGESTR_XM = ImmutableSet.of("aab034", "aab360");

    /**
     * 厦门用户
     */
    public static final String FLAG_XM = "flag";

    /**
     * 不覆盖ac20的aab034
     */
    public static final String AC20 = "ac20";
    public static final String AAB034 = "aab034";

    /**
     * 操作序号
     */
    public static final String AAZ649 = "aaz649";

    /**
     * 事项编号
     */
    public static final String PATHID = "pathId";

    /**
     * 业务日志ID
     */
    public static final String AAZ002 = "aaz002";

    /**
     * 审核状态
     */
    public static final String KEY = "key";

    /**
     * 用户信息
     */
    public static final String CURRENTUSER = "currentUser";

    /**
     * 自定义忽略字段
     */
    public static final String IGNORE_FIELDS = "ignoreFields";

    /**
     * 功能编号
     */
    public static final String AAA391 = "aaa391";

    /**
     * 部中台服务调用日志表ID
     */
    public static final String BAE000 = "bae000";

    /**
     * 初始ENCODE
     */
    public static final String ENCODE = "00000000000000000000";

    /**
     * replca字符串
     */
    public static final String REP_STR = "1";

    /**
     * SERIALVERSIONUID
     */
    public static final String SERIALVERSIONUID = "serialVersionUID";

    /**
     * k8配置
     */
    public static final String EVN_SELF = "self";
    /**
     * 华为云配置
     */
    public static final String EVN_HWRT = "rt";

    /**
     * 客户端-一体化系统
     */
    public static final String RESOURCE_YTH = "1";

    /**
     * 部中台接口URI
     */
    public static final String BZT_URI = "/api";
    public static final String BZT_OP_URI = "/api/operational";

    /**
     * 省中台接口URI
     */
    public static final String SZT_URI = "/sapi";
    public static final String OUTSIDE_URI = "/outside";
    public static final String QT_SI_URI = "/siapi";

    /**
     * 本地缓存
     */
    public static final String LOCAL_CODE = "localCode";

    /**
     * 浮动费率模板下载单批次数量
     */
    public static final String UNIT_FLOAT_MAX = "unitfloatmax";

    /**
     * 风控开关
     */
    public static final String RISK_SWITCH = "risk";

    /**
     * 税务推送开关
     */
    public static final String UNITTAX_SWITCH = "unittax";
    public static final String PERSONTAX_SWITCH = "persontax";
    public static final String PAYTAX_SWITCH = "paytax";
    public static final String PROJECTTAX_SWITCH = "projecttax";

    /**
     * 防止重复提交key
     */
    public static final String LOCK_PREFIX = "repeat-lock:";

    /**
     * PAYMENT_POOL_SIZE线程池大小   PAYMENT_DATA_SIZE线程数大小
     */
    public static final String PAYMENT_POOL_SIZE = "payment.poolsize";
    public static final String PAYMENT_DATA_SIZE = "payment.datasize";
    public static final String PAYMENT_SEND_TIME = "payment.sendTime";

    public static final String MYBATIS_IGNORE = "SqlSession";
}
