package com.alice.support.common.mybatis.config;

import com.alice.support.common.mybatis.method.UpdateAll;
import com.alice.support.common.mybatis.method.UpdateAllById;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @Description TODO
 * @ClassName MySqlInjector
 * @DateTime 2022/6/30 17:34
 * @Author ZhangChenhuang
 */
public class MySqlInjector extends DefaultSqlInjector {

    public MySqlInjector() {

    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new UpdateAllById());
        methodList.add(new UpdateAll());
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}