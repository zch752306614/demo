package com.alice.zhang.demoapi.demo.module.dao;

import com.alice.zhang.demoapi.demo.module.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/8 9:06
 */
public interface UserMapper {

    int insert(User user);

    int insert_sql(@Param("sql") String sql);

}
