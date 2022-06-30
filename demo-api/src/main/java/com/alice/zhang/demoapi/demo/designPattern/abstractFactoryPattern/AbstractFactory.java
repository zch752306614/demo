package com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern;

import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.Color.Color;
import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.shape.Shape;

public abstract class AbstractFactory {
    public abstract Color getColor(String colorType);
    public abstract Shape getShape(String shapeType);
}
