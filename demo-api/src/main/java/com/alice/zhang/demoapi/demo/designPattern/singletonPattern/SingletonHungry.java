package com.alice.zhang.demoapi.demo.designPattern.singletonPattern;

//饿汉式
public class SingletonHungry {
    private static SingletonHungry instance = new SingletonHungry();

    private SingletonHungry() {
    }

    public static SingletonHungry getInstance() {
        return instance;
    }
}
