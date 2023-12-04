package com.alice.support.module.common.designpattern.abstractfactorypattern.shape;

import com.alice.support.module.common.designpattern.abstractfactorypattern.AbstractFactory;
import com.alice.support.module.common.designpattern.abstractfactorypattern.color.Color;

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
