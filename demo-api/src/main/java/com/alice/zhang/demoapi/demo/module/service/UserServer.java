package com.alice.zhang.demoapi.demo.module.service;

import com.alice.zhang.demoapi.demo.module.entity.User;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/8 9:00
 */
public interface UserServer {

    int insert(User user);

    int insert_sql(String sql);

}
