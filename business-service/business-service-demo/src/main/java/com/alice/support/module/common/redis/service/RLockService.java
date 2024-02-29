package com.alice.support.module.common.redis.service;

import org.springframework.stereotype.Service;

/**
 * @Description redisson分布式锁demo
 * @DateTime 2024/2/23 17:40
 */
@Service
public interface RLockService {

    void getLock(int num);

}
