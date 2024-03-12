package com.alice.support.module.demo.service.Impl;

import com.alice.support.common.redis.service.RedisService;
import com.alice.support.module.demo.entity.User;
import com.alice.support.module.demo.mapper.UserMapper;
import com.alice.support.module.demo.service.UserServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/8 9:02
 */
@Service
public class UserServerImpl implements UserServer {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User select(Integer id) {
        return userMapper.selectById(id);
    }

    @Async("daemonExecutor")
    @Override
    public void expireKey(String key, long time) {
        while (redisService.hasKey(key)) {
            redisService.expire(key, time);
        }
    }
}
