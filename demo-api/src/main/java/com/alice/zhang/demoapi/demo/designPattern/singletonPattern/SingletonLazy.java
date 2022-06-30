package com.alice.zhang.demoapi.demo.designPattern.singletonPattern;

//懒汉式，线程不安全
public class SingletonLazy {
    private static SingletonLazy instance;

    private SingletonLazy() {
    }

    public static SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}
