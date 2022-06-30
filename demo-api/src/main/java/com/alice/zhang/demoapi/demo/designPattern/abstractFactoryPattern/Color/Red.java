package com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.Color;

public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("class.Red,method.fill()");
    }
}
