package com.alice.support.module.common.designpattern.singletonpattern;

//双检锁/双重校验锁（DCL，即 double-checked locking）
public class SingletonDCL {
    private static SingletonDCL instance;

    private SingletonDCL() {
    }

    public static SingletonDCL getInstance() {
        synchronized (SingletonDCL.class) {
            if (instance == null) {
                instance = new SingletonDCL();
            }
        }
        return instance;
    }
}
