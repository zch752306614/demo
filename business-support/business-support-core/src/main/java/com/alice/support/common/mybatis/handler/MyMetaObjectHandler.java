package com.alice.support.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @Description Mybatis拦截器
 * @DateTime 2024/1/12 10:47
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.strictInsertFill(metaObject, "createTime", Timestamp.class, now);
        this.strictInsertFill(metaObject, "updateTime", Timestamp.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.strictUpdateFill(metaObject, "updateTime", Timestamp.class, now);
    }

}
