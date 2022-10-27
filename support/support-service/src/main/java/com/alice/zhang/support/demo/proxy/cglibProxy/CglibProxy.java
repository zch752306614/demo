package com.alice.zhang.support.demo.proxy.cglibProxy;

import com.alice.zhang.support.demo.proxy.util.CommonUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description cglib动态代理
 * @ClassName CglibProxy
 * @DateTime 2022/6/22 16:10
 * @Author ZhangChenhuang
 */
public class CglibProxy implements MethodInterceptor {

    private Object object;

    public CglibProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        CommonUtil.doSomethingBefore();
        Object ret = method.invoke(object, objects);
        CommonUtil.doSomethingAfter();
        return ret;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

}
