package com.alice.support.common.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @Description 自定义mapper
 * @DateTime 2023/12/8 13:26
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入 仅适用于mysql
     *
     * @param entityList 实体集合
     * @return Integer
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}

