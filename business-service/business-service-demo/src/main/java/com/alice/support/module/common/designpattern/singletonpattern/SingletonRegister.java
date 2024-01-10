package com.alice.support.module.common.designpattern.singletonpattern;

//登记式/静态内部类
public class SingletonRegister {
    private static class SingletonHolder {
        private static final SingletonRegister INSTANCE = new SingletonRegister();
    }

    private SingletonRegister() {
    }

    public static final SingletonRegister getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
