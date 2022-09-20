package com.alice.zhang.demoapi.demo.module.service.Impl;

import com.alice.zhang.demoapi.demo.module.dao.UserMapper;
import com.alice.zhang.demoapi.demo.module.entity.User;
import com.alice.zhang.demoapi.demo.module.service.UserServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/8 9:02
 */
@Service
public class UserServerImpl implements UserServer {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User select(Integer id) {
        return userMapper.selectById(id);
    }

}
