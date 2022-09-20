package com.alice.zhang.demoapi.demo.designpattern.abstractfactorypattern;

import com.alice.zhang.demoapi.demo.designpattern.abstractfactorypattern.color.Color;
import com.alice.zhang.demoapi.demo.designpattern.abstractfactorypattern.shape.Shape;

public abstract class AbstractFactory {
    public abstract Color getColor(String colorType);
    public abstract Shape getShape(String shapeType);
}
