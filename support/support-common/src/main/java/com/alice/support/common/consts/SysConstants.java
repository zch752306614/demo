package com.alice.support.common.consts;

/**
 * @Description 框架参数
 * @DateTime 2022/6/30 17:41
 * @Author ZhangChenhuang
 */
public class SysConstants {

    private SysConstants() {
    }

    public static final String MYBATIS_IGNORE = "SqlSession";

    public static final int MAX_CHAPTER = 10000;

    public static final int MAX_BATCH = 50;

    public static final int MSG_MAX_LEN = 500;

    public static final String CONTENT_ERROR_FILTER = "内容缺失或章节不存在！请稍后重新尝试！\n";

    public static final String TITLE_ERROR_FILTER = "1/1";

    public static final String IS_YES = "1";

    public static final String IS_NO = "0";

    public static final String EMPTY = "";

    public static final String NUMBER_ONE = "0";

    public static final String NUMBER_TWO = "1";

    public static final String NUMBER_THREE = "2";

    public static final String NUMBER_FOUR = "3";

    public static final String STRING_DT = "dt";

    public static final String STRING_DD = "dd";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
