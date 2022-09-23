package com.alice.zhang.support.demo.proxy.jdkProxy;

import com.alice.zhang.support.demo.proxy.util.CommonUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description jdk动态代理
 * @ClassName JDKProxy
 * @DateTime 2022/6/22 16:11
 * @Author ZhangChenhuang
 */
public class JDKProxy implements InvocationHandler {

    private Object target;

    public JDKProxy(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        CommonUtil.doSomethingBefore();
        Object ret = method.invoke(target, objects);
        CommonUtil.doSomethingAfter();
        return ret;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

}
