package com.alice.support.module.demo.service;

import com.alice.support.module.demo.entity.User;

/**
 * @Description TODO
 * @Author ZhangChenhuang
 * @ClassName UserServer
 * @DateTime 2022/9/20 0020 17:51
 */
public interface UserServer {

    /**
     * insert
     *
     * @param user User
     * @return int
     * @author ZhangChenhuang
     */
    int insert(User user);

    /**
     * select
     *
     * @param id Integer
     * @return User
     * @author ZhangChenhuang
     */
    User select(Integer id);

    void expireKey(String key, long time);

}
