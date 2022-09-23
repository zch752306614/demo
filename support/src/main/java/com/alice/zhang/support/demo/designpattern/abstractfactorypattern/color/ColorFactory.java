package com.alice.zhang.support.demo.designpattern.abstractfactorypattern.color;

import com.alice.zhang.support.demo.designpattern.abstractfactorypattern.AbstractFactory;
import com.alice.zhang.support.demo.designpattern.abstractfactorypattern.shape.Shape;

public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String colorType) {
        if (colorType == null) {
            return null;
        }
        if (colorType.equalsIgnoreCase("RED")) {
            return new Red();
        } else if (colorType.equalsIgnoreCase("GREEN")) {
            return new Green();
        } else if (colorType.equalsIgnoreCase("BLUE")) {
            return new Blue();
        }
        return null;
    }

    @Override
    public Shape getShape(String shapeType) {
        return null;
    }
}
