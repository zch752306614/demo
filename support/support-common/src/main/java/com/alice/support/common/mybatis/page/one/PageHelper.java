package com.alice.support.common.mybatis.page.one;

import cn.hutool.core.util.StrUtil;
import com.alice.support.common.exception.BusinessException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 分页工具
 * @DateTime 2023/11/28 10:26
 */
public class PageHelper {

    protected static final ThreadLocal<Page> LOCAL_PAGE = new ThreadLocal();

    public PageHelper() {
    }

    protected static void setLocalPage(Page page) {
        LOCAL_PAGE.set(page);
    }

    public static <T> Page<T> getLocalPage() {
        return (Page) LOCAL_PAGE.get();
    }

    public static <E> void startPage(Object params) {
        Page<E> page = getPageFromObject(params);
        setLocalPage(page);
    }

    public static <E> Page<E> getPageFromObject(Object params) {
        if (params == null) {
            throw new BusinessException("无法获取分页查询参数!");
        } else {
            int pageNum;
            int pageSize;
            PageParam pageParam;
            try {
                pageParam = (PageParam) params;
                pageNum = pageParam.getPageNum();
                pageSize = pageParam.getPageSize();
            } catch (Exception var12) {
                throw new BusinessException("分页参数转换异常！");
            }
            Page page = new Page(pageNum, pageSize);
            List<String> ascs = new ArrayList<>(16);
            List<String> descs = new ArrayList<>(16);

            List<Map<String, PageParam.Sort>> sortBy = pageParam.getSortBy();
            for (Map<String, PageParam.Sort> stringSortMap : sortBy) {
                for (String column : stringSortMap.keySet()) {
                    PageParam.Sort sort = stringSortMap.get(column);
                    int compare = sort.compareTo(PageParam.Sort.ASC);
                    String aCase = StrUtil.toUnderlineCase(column).toUpperCase();;

                    if (compare == 0) {
                        ascs.add(aCase);
                    } else {
                        descs.add(aCase);
                    }
                }
            }
            page.setAscs(ascs);
            page.setDescs(descs);
            return page;
        }
    }
}

