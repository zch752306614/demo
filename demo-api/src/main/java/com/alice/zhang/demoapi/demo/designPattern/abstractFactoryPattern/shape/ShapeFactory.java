package com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.shape;

import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.AbstractFactory;
import com.alice.zhang.demoapi.demo.designPattern.abstractFactoryPattern.Color.Color;

public class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }

    @Override
    public Color getColor(String colorType) {
        return null;
    }
}
