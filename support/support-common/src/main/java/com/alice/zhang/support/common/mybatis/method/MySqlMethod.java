package com.alice.zhang.support.common.mybatis.method;

public enum MySqlMethod {
    UPDATE("update", "根据 whereEntity 条件，更新记录", "<script>\nUPDATE %s %s %s\n</script>");

    private final String method;
    private final String desc;
    private final String sql;

    MySqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return this.method;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getSql() {
        return this.sql;
    }
}