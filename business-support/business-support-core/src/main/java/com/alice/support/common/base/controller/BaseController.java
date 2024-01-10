package com.alice.support.common.base.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alice.support.common.mybatis.page.PageDomain;
import com.alice.support.common.mybatis.page.TableDataInfo;
import com.alice.support.common.mybatis.page.TableSupport;
import com.alice.support.common.util.BusinessExceptionUtil;
import com.alice.support.common.util.SqlUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;

import java.util.List;

/**
 * @Description web层通用数据处理
 * @DateTime 2023/11/28 17:34
 */
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (ObjectUtil.isEmpty(pageNum) || ObjectUtil.isEmpty(pageSize)) {
            BusinessExceptionUtil.dataEx("页码和每页行数不能为空");
        }
        if (ObjectUtil.isNotEmpty(pageNum) && ObjectUtil.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageMethod.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

}
