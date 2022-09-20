package com.alice.zhang.demoapi.demo.module.service;

import com.alice.zhang.demoapi.demo.module.entity.User;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @ClassName UserServer
 * @DateTime 2022/9/20 0020 17:51
 */
public interface UserServer {

    /**
     * insert
     * @author ZhangChenhuang
     * @param user User
     * @return int
     */
    int insert(User user);

    /**
     * select
     * @author ZhangChenhuang
     * @param id Integer
     * @return User
     */
    User select(Integer id);

}
