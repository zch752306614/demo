package com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.Color;

import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.AbstractFactory;
import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.shape.Shape;

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
