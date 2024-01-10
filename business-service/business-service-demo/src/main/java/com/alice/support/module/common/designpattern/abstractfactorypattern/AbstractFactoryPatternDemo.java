package com.alice.support.module.common.designpattern.abstractfactorypattern;

import com.alice.support.module.common.designpattern.abstractfactorypattern.color.Color;
import com.alice.support.module.common.designpattern.abstractfactorypattern.shape.Shape;

public class AbstractFactoryPatternDemo {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");
        AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape1.draw();
        shape2.draw();
        shape3.draw();
        Color color1 = colorFactory.getColor("RED");
        Color color2 = colorFactory.getColor("GREEN");
        Color color3 = colorFactory.getColor("BLUE");
        color1.fill();
        color2.fill();
        color3.fill();
    }
}
