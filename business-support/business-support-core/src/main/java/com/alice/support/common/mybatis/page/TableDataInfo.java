package com.alice.support.common.mybatis.page;

import com.alice.support.common.framework.converter.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 表格分页数据对象
 * @DateTime 2023/11/28 17:32
 */
@Data
@ApiModel("表格分页对象")
public class TableDataInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @ApiModelProperty(value = "总记录数")
    private long total;

    /**
     * 列表数据
     */
    @ApiModelProperty(value = "列表数据")
    private T rows;

    /**
     * 分页
     */
    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param t     列表数据
     * @param total 总记录数
     */
    public TableDataInfo(T t, int total) {
        this.rows = t;
        this.total = total;
    }

    public static <T extends List<?>> TableDataInfo dateInfo(T data) {
        return new TableDataInfo(data, (int) new PageInfo(data).getTotal());
    }

}