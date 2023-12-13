package com.alice.support.common.mybatis.method;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Description 全量更新
 * @ClassName UpdateAllById
 * @DateTime 2022/6/30 17:47
 * @Author ZhangChenhuang
 */
public class UpdateAllById extends AbstractMethod {

    private static final long serialVersionUID = 5000001545141858884L;

    public UpdateAllById() {
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        boolean logicDelete = tableInfo.isLogicDelete();
        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        StringBuilder append = (new StringBuilder("<if test=\"et instanceof java.util.Map\">")).append("<if test=\"et.").append("MP_OPTLOCK_VERSION_ORIGINAL").append("!=null\">").append(" AND ${et.").append("MP_OPTLOCK_VERSION_COLUMN").append("}=#{et.").append("MP_OPTLOCK_VERSION_ORIGINAL").append("}").append("</if></if>");
        if (logicDelete) {
            append.append(tableInfo.getLogicDeleteSql(true, false));
        }
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlSet(logicDelete, false, tableInfo, true, "et", "et."), tableInfo.getKeyColumn(), "et." + tableInfo.getKeyProperty(), append);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, "updateAllById", sqlSource);
    }

    @Override
    protected String sqlSet(boolean logic, boolean ew, TableInfo table, boolean judgeAliasNull, String alias, String prefix) {
        String sqlScript = getAllSqlSet(table, logic, prefix);
        if (judgeAliasNull) {
            sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", alias), true);
        }

        if (ew) {
            sqlScript = sqlScript + "\n";
            sqlScript = sqlScript + SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam("ew.sqlSet"), String.format("%s != null and %s != null", "ew", "ew.sqlSet"), false);
        }

        sqlScript = SqlScriptUtils.convertSet(sqlScript);
        return sqlScript;
    }

    private String getAllSqlSet(TableInfo tableInfo, boolean ignoreLogicDelFiled, final String prefix) {
        String newPrefix = prefix == null ? "" : prefix;
        return tableInfo.getFieldList().stream().filter((i) -> {
            if (!ignoreLogicDelFiled) {
                return true;
            } else {
                return !tableInfo.isLogicDelete() || !i.isLogicDelete();
            }
        }).map((i) -> getSqlSet(i, newPrefix)).collect(Collectors.joining("\n"));
    }

    private String getSqlSet(TableFieldInfo tableFieldInfo, final String prefix) {
        String newPrefix = prefix == null ? "" : prefix;
        String sqlSet = tableFieldInfo.getColumn() + "=";
        if (CharSequenceUtil.isNotEmpty(tableFieldInfo.getUpdate())) {
            sqlSet = sqlSet + String.format(tableFieldInfo.getUpdate(), tableFieldInfo.getColumn());
        } else {
            sqlSet = sqlSet + SqlScriptUtils.safeParam(newPrefix + tableFieldInfo.getEl() + getJdbcType(tableFieldInfo.getPropertyType()));
        }
        sqlSet = sqlSet + ",";
        return sqlSet;
    }

    /**
     * jdbc字段类型映射
     *
     * @param property
     */
    private String getJdbcType(Class property) {
        String prefix = ",jdbcType=";
        if (String.class.equals(property)) {
            return prefix + "VARCHAR";
        } else if (Integer.class.equals(property) || Long.class.equals(property)) {
            return prefix + "INTEGER";
        } else if (Double.class.equals(property) || BigDecimal.class.equals(property)) {
            return prefix + "DECIMAL";
        } else if (Timestamp.class.equals(property) || Date.class.equals(property)) {
            return prefix + "TIMESTAMP";
        }
        return null;
    }
}
