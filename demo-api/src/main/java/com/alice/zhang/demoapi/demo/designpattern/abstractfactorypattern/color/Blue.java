package com.alice.zhang.demoapi.demo.designpattern.abstractfactorypattern.color;

public class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("class.Blue,method.fill()");
    }
}
