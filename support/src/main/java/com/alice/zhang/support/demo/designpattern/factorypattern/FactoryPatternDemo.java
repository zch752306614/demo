package com.alice.zhang.support.demo.designpattern.factorypattern;

public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape1.draw();
        shape2.draw();
        shape3.draw();
    }
}
