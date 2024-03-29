package com.alice.support.common.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Description 自定义mapper
 * @DateTime 2023/12/8 13:26
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入 仅适用于mysql
     *
     * @param entityList 实体集合
     * @return int
     */
    int insertBatchSomeColumn(Collection<T> entityList);

    /**
     * 自定义批量插入
     *
     * @param list 插入的数据
     * @return int
     */
    int insertBatch(@Param("list") List<T> list);

    /**
     * 自定义批量更新，条件为主键
     *
     * @param list 更新的数据
     * @return int
     */
    int updateBatch(@Param("list") List<T> list);

    /**
     * 自定义全量更新
     *
     * @param entity 实体数据
     * @return int
     */
    int updateAll(@Param(Constants.ENTITY) T entity);

    /**
     * 自定义全量更新，条件为主键
     *
     * @param entity 实体数据
     * @return int
     */
    int updateAllById(@Param(Constants.ENTITY) T entity);
}

