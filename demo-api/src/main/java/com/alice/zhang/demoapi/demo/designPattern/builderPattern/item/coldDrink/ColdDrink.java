package com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.coldDrink;

import com.alice.zhang.demoapi.demo.designPattern.builderPattern.item.Item;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.packing.Bottle;
import com.alice.zhang.demoapi.demo.designPattern.builderPattern.packing.Packing;

public abstract class ColdDrink implements Item {
    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
