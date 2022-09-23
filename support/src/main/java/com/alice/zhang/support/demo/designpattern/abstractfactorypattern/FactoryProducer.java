package com.alice.zhang.support.demo.designpattern.abstractfactorypattern;

import com.alice.zhang.support.demo.designpattern.abstractfactorypattern.color.ColorFactory;
import com.alice.zhang.support.demo.designpattern.abstractfactorypattern.shape.ShapeFactory;

public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("SHAPE")) {
            return new ShapeFactory();
        } else if(choice.equalsIgnoreCase("COLOR")) {
            return new ColorFactory();
        }
        return null;
    }
}
