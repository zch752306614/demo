package com.alice.zhang.support.demo.designpattern.abstractfactorypattern.color;

public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("class.Red,method.fill()");
    }
}
