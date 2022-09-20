package com.alice.zhang.demoapi.demo.designpattern.abstractfactorypattern.shape;

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("class.Rectangle,method.draw()");
    }
}
