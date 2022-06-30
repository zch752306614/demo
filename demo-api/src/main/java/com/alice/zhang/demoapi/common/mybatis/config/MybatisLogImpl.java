package com.alice.zhang.demoapi.common.mybatis.config;

import com.alice.zhang.demoapi.common.consts.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * @Description TODO
 * @ClassName MybatisLogImpl
 * @DateTime 2022/6/30 17:36
 * @Author ZhangChenhuang
 */
@Slf4j
public class MybatisLogImpl implements Log {

    public MybatisLogImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        if (s.contains(SysConstant.MYBATIS_IGNORE)) {
            log.info(s);
        }
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}
