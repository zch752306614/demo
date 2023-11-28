package com.alice.support.common.mybatis.page;

import cn.hutool.core.text.CharSequenceUtil;
import com.alice.support.common.consts.SysConstants;
import lombok.Data;

/**
 * @Description 分页数据
 * @DateTime 2023/11/28 17:32
 */
@Data
public class PageDomain {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc = "asc";

    public String getOrderBy() {
        if (CharSequenceUtil.isEmpty(orderByColumn)) {
            return SysConstants.EMPTY;
        }
        return orderByColumn + " " + isAsc;
    }

}
