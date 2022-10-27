package com.alice.zhang.support.module.common.designpattern.abstractfactorypattern;

import com.alice.zhang.support.module.common.designpattern.abstractfactorypattern.color.Color;
import com.alice.zhang.support.module.common.designpattern.abstractfactorypattern.shape.Shape;

public abstract class AbstractFactory {
    public abstract Color getColor(String colorType);
    public abstract Shape getShape(String shapeType);
}
